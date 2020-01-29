package sdong.common.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sdong.common.bean.FileInfo;
import sdong.common.exception.SdongException;

public class LocUtilTest {

    private static final Logger LOG = LoggerFactory.getLogger(LocUtilTest.class);

    @Test
    public void testGetFileLocInfo_case() {
        String fileName = "input/loc/loc_cases.c";
        int commentLineCount = 27;
        int blankLineCounts = 36;
        try {
            FileInfo fileInfo = LocUtil.getFileLocInfo(fileName);

            LOG.info("{}", fileInfo.toString());
            assertEquals(commentLineCount, fileInfo.getCommentCounts());
            assertEquals(blankLineCounts, fileInfo.getBlankLineCounts());

        } catch (SdongException e) {
            LOG.error(e.getMessage());
            fail("should not get exception!");
        }
    }

    @Test
    public void testGetFileLocInfo() {
        String fileName = "input/loc/loc_example.c";
        int commentLineCount = 57;
        int blankLineCounts = 64;
        try {
            FileInfo fileInfo = LocUtil.getFileLocInfo(fileName);

            LOG.info("{}", fileInfo.toString());
            assertEquals(commentLineCount, fileInfo.getCommentCounts());
            assertEquals(blankLineCounts, fileInfo.getBlankLineCounts());

        } catch (SdongException e) {
            LOG.error(e.getMessage());
            fail("should not get exception!");
        }
    }

    @Test
    public void testMathingReg_ONELINE() {
        String fileName = "input/loc/loc_cases.c";
        List<String> lines;
        try {
            lines = FileUtil.readFileToStringList(fileName);

            String regex = LocUtil.REG_ONELINE;
            List<String> matchingCase = Arrays.asList("case 1 ", "case 18 ", "case 20 ", "case 38 ",
                    "case 39 ", "case 40 ", "case 43 ", "case 44 ");
            List<String> result = new ArrayList<String>();
            for (String line : lines) {
                if (!line.trim().isEmpty() && LocUtil.matching(line, regex)) {
                    LOG.info("{}", line);
                    result.add(line);
                }
            }
            verifyResult(matchingCase, result);

        } catch (SdongException e) {
            LOG.error(e.getMessage());
            fail("should not get exception!");
        }
    }

    @Test
    public void testMathingReg_MULTILINE_START() {
        String fileName = "input/loc/loc_cases.c";
        List<String> lines;
        try {
            lines = FileUtil.readFileToStringList(fileName);

            String regex = LocUtil.REG_ONELINE;
            List<String> matchingCase = Arrays.asList("case 2 ", "case 3 ", "case 5 ", "case 9 ",
                    "case 11 ", "case 21 ", "case 23 ", "case 27 ", "case 29 ", "case 42 ");
            List<String> result = new ArrayList<String>();
            for (String line : lines) {
                // LOG.info("{}",line);
                if (!line.trim().isEmpty() && LocUtil.matchingStartLine(line, regex)) {
                    LOG.info("{}", line);
                    result.add(line);
                }
            }
            verifyResult(matchingCase, result);

        } catch (SdongException e) {
            LOG.error(e.getMessage());
            fail("should not get exception!");
        }
    }

    @Test
    public void testMathingReg_MULTILINE_START_WITH_CODE() {
        String fileName = "input/loc/loc_cases.c";
        List<String> lines;
        try {
            lines = FileUtil.readFileToStringList(fileName);

            String regex = LocUtil.REG_ONELINE;
            List<String> matchingCase = Arrays.asList("case 7 ", "case 10 ", "case 11 ", "case 13 ",
                    "case 15 ", "case 16 ", "case 17 ", "case 17 ", "case 25 ", "case 28 ",
                    "case 29", "case 31", "case 33 ", "case 34 ", "case 35", "case 35");
            List<String> result = new ArrayList<String>();
            for (String line : lines) {
                // LOG.info("{}",line);
                if (!line.trim().isEmpty() && LocUtil.matchingStartLineWithCode(line, regex)) {
                    LOG.info("{}", line);
                    result.add(line);
                }
            }
            verifyResult(matchingCase, result);

        } catch (SdongException e) {
            LOG.error(e.getMessage());
            fail("should not get exception!");
        }
    }

    @Test
    public void testMathingReg_MULTILINE_END() {
        String fileName = "input/loc/loc_cases.c";
        List<String> lines;
        try {
            lines = FileUtil.readFileToStringList(fileName);

            String regex = LocUtil.REG_MUTLILINE_END;
            List<String> matchingCase = Arrays.asList("case 2 ", "case 3 ", "case 7 ", "case 10 ",
                    "case 11 ", "case 16 ", "case 17 ", "case 21 ", "case 25 ", "case 28 ",
                    "case 29 ", "case 34 ", "case 35 ");
            List<String> result = new ArrayList<String>();
            for (String line : lines) {
                // LOG.info("{}",line);
                if (!line.trim().isEmpty() && LocUtil.matching(line, regex)) {
                    LOG.info("{}", line);
                    result.add(line);
                }
            }
            verifyResult(matchingCase, result);

        } catch (SdongException e) {
            LOG.error(e.getMessage());
            fail("should not get exception!");
        }
    }

    private void verifyResult(List<String> matchingCase, List<String> result) {
        assertEquals(matchingCase.size(), result.size());
        boolean isMatch = false;
        for (String line : result) {
            isMatch = false;
            LOG.info("verify:{}", line);
            for (String match : matchingCase) {
                if (line.indexOf(match) >= 0) {
                    isMatch = true;
                    break;
                }
            }
            assertEquals(true, isMatch);
        }
    }
}
