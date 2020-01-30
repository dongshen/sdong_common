package sdong.common.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sdong.common.bean.FileInfo;
import sdong.common.exception.SdongException;

/**
 * Calculate file source of line count
 */
public class LocUtil {
    private static final Logger LOG = LoggerFactory.getLogger(LocUtil.class);
    public static final String REG_ONELINE = "\\/\\*.*?\\*\\/|\\/\\/.*";

    public static FileInfo getFileLocInfo(String fileName) throws SdongException {
        FileInfo fileInfo = new FileInfo();
        try (Reader reader = new InputStreamReader(new FileInputStream(fileName))) {
            fileInfo = getFileLocInfo(reader);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new SdongException(e.getMessage());
        }
        return fileInfo;
    }


    public static FileInfo getFileLocInfo(Reader reader) throws SdongException {
        FileInfo fileInfo = new FileInfo();

        try (BufferedReader bfr = new BufferedReader(reader);) {
            String line = null;
            String result = null;
            while ((line = bfr.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    fileInfo.setBlankLineCounts(fileInfo.getBlankLineCounts() + 1);
                } else {
                    result = line.replaceAll(REG_ONELINE, "").trim();
                    if (result.isBlank()) {
                        fileInfo.setCommentCounts(fileInfo.getCommentCounts() + 1);
                    } else if (result.startsWith("/*")) { // /* comments
                        fileInfo.setCommentCounts(fileInfo.getCommentCounts() + 1);
                        multiCommentLine(bfr, fileInfo);
                    } else if (result.matches(".*?\\/\\*.*")) { // code /* comments
                        fileInfo.setCommentInLineCounts(fileInfo.getCommentInLineCounts() + 1);
                        multiCommentLine(bfr, fileInfo);
                    } else if (!line.equals(result)) {
                        fileInfo.setCommentInLineCounts(fileInfo.getCommentInLineCounts() + 1);
                    }
                }
            }
            bfr.close();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new SdongException(e.getMessage());
        }
        return fileInfo;
    }

    private static void multiCommentLine(BufferedReader bfr, FileInfo fileInfo) throws IOException {
        String line = null;
        String result = null;
        while ((line = bfr.readLine()) != null) {
            line = line.trim();

            // blank line in comments
            if (line.isEmpty()) {
                fileInfo.setCommentCounts(fileInfo.getCommentCounts() + 1);
                continue;
            }

            // after remove comments is blank
            result = line.replaceAll(REG_ONELINE, "").trim();
            if (result.isBlank()) {
                fileInfo.setCommentCounts(fileInfo.getCommentCounts() + 1);
                continue;
            }

            // comments end
            if (result.endsWith("*/")) {
                fileInfo.setCommentCounts(fileInfo.getCommentCounts() + 1);
                break;
            }

            // comments end with comment start
            if (result.matches(".*?\\*/\\s*/\\*.*")) { // *//*
                fileInfo.setCommentCounts(fileInfo.getCommentCounts() + 1);
                continue;
            }

            // comments end with code
            if (result.matches(".*?\\*\\/.*")) { // */ code
                fileInfo.setCommentInLineCounts(fileInfo.getCommentInLineCounts() + 1);
                if (result.matches(".*?\\/\\*.*")) { // */ code /*
                    continue;
                }
                break;
            }
            // still in multiple lines comments
            fileInfo.setCommentCounts(fileInfo.getCommentCounts() + 1);
        }
    }

    public static boolean matching(String str, String regex) {
        String result = str.replaceAll(regex, "");
        if (result.trim().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean matchingStartLine(String str, String regex) {
        String result = str.replaceAll(regex, "").trim();
        return result.startsWith("/*");
    }

    public static boolean matchingStartLineWithCode(String str, String regex) {
        // return str.matches(regex);
        String result = str.replaceAll(regex, "").trim();
        if (result.isEmpty() || result.startsWith("/*")) {
            return false;
        } else {
            return result.matches(".*?\\/\\*.*");
        }
    }

    public static boolean matchingEndLine(String str, String regex) {
        String result = str.replaceAll(regex, "").trim();
        if (result.isEmpty() || result.startsWith("/*")) {
            return false;
        } else if (result.endsWith("*/")) {
            return true;
        }
        return false;
    }

    public static boolean matchingEndLineWithCode(String str, String regex) {
        String result = str.replaceAll(regex, "").trim();
        if (result.isEmpty() || result.startsWith("/*")) {
            return false;
        } else if (result.endsWith("*/")) {
            return false;
        } else {
            if (result.matches(".*?\\*\\/.*")) {
                if (result.matches(".*?\\/\\*.*")) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        }
    }

    public static boolean matchingEndLineWithCodeAndStarAgain(String str, String regex) {
        String result = str.replaceAll(regex, "").trim();
        if (result.isEmpty() || result.startsWith("/*")) {
            return false;
        } else if (result.endsWith("*/")) {
            return false;
        } else {
            if (result.matches(".*?\\*\\/.*")) { // */ code
                if (result.matches(".*?\\/\\*.*")) { // */ code /*
                    if (result.matches(".*?\\*/\\s*/\\*.*")) { // *//*
                        return false;
                    } else {
                        return true;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }
}
