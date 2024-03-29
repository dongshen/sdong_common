package sdong.common.utils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sdong.common.exception.SdongException;

public class SqliteUtil {
    public static final int BATCH_SIZE = 1000;
    private static final Logger LOG = LogManager.getLogger(SqliteUtil.class);
    private static final String JDBC_LINK = "jdbc:sqlite:";

    /**
     * create database
     * 
     * @param dbFileName database file name
     * @throws SdongException module exception
     */
    public static void createNewDatabase(String dbFileName) throws SdongException {
        String url = JDBC_LINK + dbFileName;
        FileUtil.createFileWithFolder(dbFileName);
        FileUtil.deleteFile(dbFileName);

        try (Connection conn = DriverManager.getConnection(url);) {
            if (conn == null) {
                throw new SdongException("Create database fail!");
            }
        } catch (SQLException e) {
            throw new SdongException(e);
        }
        LOG.info("Create database:{} done.", FileUtil.getFileName(dbFileName));
    }

    /**
     * create database and table
     * 
     * @param dbFileName database file name
     * @param sqlFile    table file
     * @return the number of tables
     * @throws SdongException module exception
     */
    public static int createNewDatabaseAndTable(String dbFileName, String sqlFile) throws SdongException {
        createNewDatabase(dbFileName);
        return createTables(dbFileName, sqlFile);
    }

    /**
     * create table
     * 
     * @param dbFileName database file name
     * @param sqlFile    sql file
     * @return the number of tables
     * @throws SdongException module exception
     */
    public static int createTables(String dbFileName, String sqlFile) throws SdongException {
        List<String> sqlList = getSqlStmtFromFile(sqlFile);
        for (String sqlStmt : sqlList) {
            exeSql(dbFileName, sqlStmt);
        }
        int tables = getDatabaseTables(dbFileName).size();
        LOG.info("Create table:{}", tables);
        return tables;
    }

    /**
     * get connection
     * 
     * @param dbFileName database file name
     * @return Connection connection
     * @throws SQLException   sql exception
     * @throws SdongException module exception
     */
    public static Connection getConnection(String dbFileName) throws SQLException, SdongException {
        if (!new File(dbFileName).exists()) {
            throw new SdongException("Database not exist:" + new File(dbFileName).getAbsolutePath());
        }

        return DriverManager.getConnection(JDBC_LINK + dbFileName);
    }

    /**
     * execute sql
     * 
     * @param dbFileName database file name
     * @param sqlStmt    sql stmt
     * @throws SdongException module exception
     */
    public static void exeSql(String dbFileName, String sqlStmt) throws SdongException {
        try (Connection conn = getConnection(dbFileName); Statement stmt = conn.createStatement();) {
            LOG.info("Sqlstmt:{}", sqlStmt);
            stmt.execute(sqlStmt);
        } catch (SQLException e) {
            throw new SdongException(e);
        }
    }

    /**
     * delete all record from table
     * 
     * @param dbFileName database file name
     * @param tableName  table name
     * @throws SdongException module exception
     */
    public static void clearTable(String dbFileName, String tableName) throws SdongException {
        String sql = "Delete from " + tableName;
        exeSql(dbFileName, sql);
    }

    /**
     * delete records from table list
     * 
     * @param dbFileName database file name
     * @param tableList  table list
     * @throws SdongException module exception
     */
    public static void clearTableList(String dbFileName, List<String> tableList) throws SdongException {
        if (!FileUtil.fileExist(dbFileName)) {
            return;
        }

        if (tableList == null || tableList.isEmpty()) {
            return;
        }

        for (String table : tableList) {
            LOG.info("Clear table:{}", table);
            SqliteUtil.clearTable(dbFileName, table);
        }
    }

    /**
     * get record count from table
     * 
     * @param dbFileName database file name
     * @param tableName  table name
     * @return record count
     * @throws SdongException module exception
     */
    public static long getTableCount(String dbFileName, String tableName) throws SdongException {
        long records = 0L;
        String sql = "Select count(*) from " + tableName;
        try (Connection conn = getConnection(dbFileName);
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            rs.next();
            records = rs.getLong(1);
        } catch (SQLException e) {
            throw new SdongException(e);
        }
        return records;
    }

    /**
     * get all sql statement from sql file
     *
     * @param fileName filename
     * @return sql list
     * @throws SdongException module exception
     */
    public static List<String> getSqlStmtFromFile(String fileName) throws SdongException {
        List<String> sqlList = new ArrayList<String>();
        List<String> lines = FileUtil.readFileToStringList(fileName);
        StringBuilder sb = new StringBuilder();
        String val = "";
        for (String line : lines) {
            val = line.trim();
            if (val.isEmpty() || val.startsWith("--")) {
                continue;
            }
            sb.append(" ").append(val);
            if (line.endsWith(";")) {
                sqlList.add(sb.toString());
                sb.setLength(0);
            }
        }
        return sqlList;
    }

    /**
     * get database table list
     * 
     * @param dbFileName database name
     * @return table list
     * @throws SdongException moudle exception
     */
    public static List<String> getDatabaseTables(String dbFileName) throws SdongException {
        List<String> tables = new ArrayList<String>();
        String sql = "select name from sqlite_master where type = 'table'";
        try (Connection conn = getConnection(dbFileName);
                PreparedStatement pStatement = conn.prepareStatement(sql);
                ResultSet rs = pStatement.executeQuery();) {
            while (rs.next()) {
                tables.add(rs.getString(1));
            }
        } catch (SQLException e) {
            LOG.debug(e.getMessage());
            throw new SdongException(e);
        }

        return tables;
    }

    /**
     * batch sql insert result
     * 
     * @param updateCounts updateCounts
     * @return update Counts
     */
    public static int errorsCount(int[] updateCounts) {
        int result = 0;
        for (int count : updateCounts) {
            result = result + count;
        }
        return result;
    }

    /**
     * batch commit
     * 
     * @param conn  connection
     * @param pstmt Prepared Statement
     * @return updated records
     * @throws SQLException SQL exception
     */
    public static int commitBatch(Connection conn, PreparedStatement pstmt) throws SQLException {
        int[] updateCounts = pstmt.executeBatch();
        conn.commit();
        return errorsCount(updateCounts);
    }

    /**
     * import data to database
     * 
     * @param dbFileName database name
     * @param dataFile   data file
     * @return records
     * @throws SdongException module exception
     */
    public static int importData(String dbFileName, String dataFile) throws SdongException {
        List<String> dataList = SqliteUtil.getSqlStmtFromFile(dataFile);
        int records = 0;
        try (Connection conn = SqliteUtil.getConnection(dbFileName); Statement stmt = conn.createStatement();) {
            conn.setAutoCommit(true);
            for (String sql : dataList) {
                try (PreparedStatement pstmt = conn.prepareStatement(sql);) {
                    records = records + pstmt.executeUpdate();
                }
            }
            // conn.commit();
        } catch (SQLException e) {
            throw new SdongException(e);
        }

        LOG.info("import records:{}", records);
        return records;
    }
}