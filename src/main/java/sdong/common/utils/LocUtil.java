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
    public static final String REG_ONELINE = "(^/\\*[^\\*/]*\\*/(\\s*/\\*[^\\*/]*\\*/)*\\s*(//.*)*)|(^//.*)";

    public static FileInfo getFileLocInfo(String fileName) throws SdongException {
        FileInfo fileInfo = new FileInfo();

        String regMultiLinesStart = "";
        String regMultiLinesEnd = "";

        try (BufferedReader bfr =
                new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));) {
            String line = null;
            while ((line = bfr.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    fileInfo.setBlankLineCounts(fileInfo.getBlankLineCounts() + 1);
                } else if (line.matches(REG_ONELINE)) {
                    fileInfo.setCommentCounts(fileInfo.getCommentCounts() + 1);
                } else if (line.startsWith("/*") && !line.endsWith("*/")) {
                    fileInfo.setCommentCounts(fileInfo.getCommentCounts() + 1);
                    while ((line = bfr.readLine()) != null) {
                        line = line.trim();
                        fileInfo.setCommentCounts(fileInfo.getCommentCounts() + 1);
                        if (line.endsWith("*/")) {
                            break;
                        }
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

    public static boolean matching(String str, String regex){
        return str.matches(regex);
    }
}
