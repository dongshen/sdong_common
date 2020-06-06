package sdong.common.utils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdong.common.exception.SdongException;

public class SqliteUtil {
    private static final Logger LOG = LoggerFactory.getLogger(SqliteUtil.class);
    private static final String JDBC_LINK = "jdbc:sqlite:";

    /**
     * create database
     * 
     * @param dbFileName database file name
     * @throws SdongException module exception
     */
    public static void createNewDatabase(String dbFileName) throws SdongException {

        String url = JDBC_LINK + dbFileName;
        FileUtil.createFile(dbFileName);

        try (Connection conn = DriverManager.getConnection(url);) {
            if (conn == null) {
                throw new SdongException("Create database fail!");
            }
        } catch (SQLException e) {
            throw new SdongException(e.getMessage());
        }
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
            throw new SdongException("Database not exist!");
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
            stmt.execute(sqlStmt);
        } catch (SQLException e) {
            throw new SdongException(e.getMessage());
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
            throw new SdongException(e.getMessage());
        }
        return records;
    }
}