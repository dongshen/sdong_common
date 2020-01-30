package sdong.common.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
    public static final String REG_MUTLILINE_START = "^/\\*.*(?!\\*/)";
    public static final String REG_MUTLILINE_END = "[^\\*/]*\\*/(\\s*/\\*[^\\*/]*\\*/)*\\s*(//.*)*";
    public static final String REG_MUTLILINE_START_WITH_CODE =
            "(/\\*.[^\\*/]*\\*/)*.+(/\\*.[^\\*/]*\\*/)*/\\*.[^\\*/]*";

    public static FileInfo getFileLocInfo(String fileName) throws SdongException {
        FileInfo fileInfo = new FileInfo();

        String regMultiLinesStart = "";
        String regMultiLinesEnd = "";

        try (BufferedReader bfr =
                new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));) {
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
                    } else if (result.startsWith("/*")) {
                        fileInfo.setCommentCounts(fileInfo.getCommentCounts() + 1);
                        while ((line = bfr.readLine()) != null) {
                            line = line.trim();
                            fileInfo.setCommentCounts(fileInfo.getCommentCounts() + 1);
                            if (matching(line, REG_MUTLILINE_END)) {
                                break;
                            }
                        }
                    } else if (result.matches(".*?/\\*.*")) {

                    }
                }
            }
            bfr.close();
        } catch (

        IOException e) {
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
            if (line.isEmpty()) {
                fileInfo.setBlankLineCounts(fileInfo.getBlankLineCounts() + 1);
                continue;
            }
            result = line.replaceAll(REG_ONELINE, "").trim();
            if (result.isBlank()) {
                fileInfo.setCommentCounts(fileInfo.getCommentCounts() + 1);
                continue;
            }

        }
    }

    public static boolean matching(String str, String regex) {
        // return str.matches(regex);
        String result = str.replaceAll(regex, "");
        if (result.trim().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean matchingStartLine(String str, String regex) {
        // return str.matches(regex);
        String result = str.replaceAll(regex, "").trim();
        return result.startsWith("/*");
    }

    public static boolean matchingStartLineWithCode(String str, String regex) {
        // return str.matches(regex);
        String result = str.replaceAll(regex, "").trim();
        if (result.isEmpty() || result.startsWith("/*")) {
            return false;
        } else {
            return result.matches(".*?/\\*.*");
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
}
