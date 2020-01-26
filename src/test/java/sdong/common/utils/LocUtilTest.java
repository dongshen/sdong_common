package sdong.common.utils;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sdong.common.bean.FileInfo;
import sdong.common.exception.SdongException;

public class LocUtilTest {

    private static final Logger log = LoggerFactory.getLogger(LocUtilTest.class);

    @Test
    public void testGetFileLocInfo() {
        String fileName = "input/loc/loc_example.c";
        int commentLineCount = 57;
        int blankLineCounts = 64;
        try {
            FileInfo fileInfo = LocUtil.getFileLocInfo(fileName);

            log.info("{}", fileInfo.toString());
            assertEquals(commentLineCount, fileInfo.getCommentCounts());
            assertEquals(blankLineCounts, fileInfo.getBlankLineCounts());

        } catch (SdongException e) {
            log.error(e.getMessage());
            fail("should not get exception!");
        }
    }
}
