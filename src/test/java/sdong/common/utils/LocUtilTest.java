package sdong.common.utils;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import sdong.common.bean.loc.FileInfo;
import sdong.common.bean.loc.FileType;
import sdong.common.bean.loc.FileTypeComment;
import sdong.common.bean.loc.LineType;
import sdong.common.bean.loc.MultipleLineComment;
import sdong.common.exception.SdongException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class LocUtilTest {
    private static final Logger LOG = LogManager.getLogger(LocUtilTest.class);

    private static final int FILE_TYPE_NUM = 8;

    public static final String C_REG_STRING_VALUE = "\"(.+?)\"|'.+?'";
    public static final String C_REG_ONELINE = "\\/\\*.*?\\*\\/|\\/\\/.*";
    public static final String C_COMMENT_MULTIPL_START = "/*";
    public static final String C_COMMENT_MULTIPL_END = "*/";
    public static final String C_COMMENT_ONELINE = "//";

    // test case file name
    private static final String caseFileName = "input/loc/example/loc_cases.c";

    // test csae expact result array index: 0:blank line; 1:comment line; 2:comment
    // in line;
    private static final int CASE_RESULT_ARRAY_IND_BLANK_LINE = 0;
    private static final int CASE_RESULT_ARRAY_IND_COMMENT_LINE = 1;
    private static final int CASE_RESULT_ARRAY_IND_COMMENT_IN_LINE = 2;

    // test case expact result array
    private static final int[][] result = { { 0, 1, 0 }, /* case 1 */{ 0, 1, 0 }, { 0, 3, 0 }, { 0, 3, 0 }, { 0, 0, 1 },
            { 0, 1, 1 }, /* case 6 */ { 0, 0, 1 }, { 0, 2, 1 }, { 0, 0, 1 }, { 0, 1, 1 }, { 0, 1, 1 },
            /* case 11 */ { 0, 2, 1 }, { 0, 0, 1 }, { 0, 0, 2 }, { 0, 0, 1 }, { 0, 0, 2 }, /* case 16 */ { 0, 1, 1 },
            { 0, 1, 2 }, { 0, 1, 0 }, { 0, 0, 1 }, { 0, 1, 0 }, /* case 21 */{ 0, 2, 0 }, { 0, 0, 1 }, { 0, 1, 1 },
            { 0, 0, 1 }, { 0, 2, 1 }, /* case 26 */ { 0, 0, 1 }, { 0, 1, 1 }, { 0, 1, 1 }, { 0, 2, 1 }, { 0, 0, 1 },
            /* case 31 */ { 0, 0, 2 }, { 0, 0, 1 }, { 0, 0, 2 }, { 0, 1, 1 }, { 0, 1, 2 }, /* csae 36 */ { 0, 0, 0 },
            { 0, 0, 0 }, { 0, 1, 0 }, { 0, 1, 0 }, { 0, 1, 0 }, /* case 41 */ { 0, 0, 1 }, { 0, 3, 0 }, { 0, 1, 0 },
            { 0, 1, 0 }, { 0, 3, 0 }, /* case 46 */{ 0, 0, 0 }, { 0, 3, 0 }, { 0, 2, 0 }, { 0, 1, 1 } };

    @Test
    void testGetFileLocInfo_case() {
        int blankLineCounts = result.length;
        int commentLineCount = 0;
        int commentInLineCounts = 0;
        int rowLineCounts = 143;
        for (int[] value : result) {
            commentLineCount = commentLineCount + value[1];
            commentInLineCounts = commentInLineCounts + value[2];
        }
        LOG.info("blankLineCounts:{}, commentLineCount:{}, commentInLineCounts:{}", blankLineCounts, commentLineCount,
                commentInLineCounts);
        try {
            FileInfo fileInfo = LocUtil.getFileLocInfo(new File(caseFileName));

            LOG.info("{}", fileInfo.toString());
            assertEquals(commentLineCount, fileInfo.getCommentCounts());
            assertEquals(blankLineCounts, fileInfo.getBlankLineCounts());
            assertEquals(commentInLineCounts, fileInfo.getCommentInLineCounts());
            assertEquals(rowLineCounts, fileInfo.getRowLineCounts());
            assertEquals("cdbeae51a04ffffdbbde623b9f66913f", fileInfo.getMd5());
        } catch (SdongException e) {
            LOG.error("{}:{}", e.getErrorPosition(), e.getMessage());
            fail("should not get exception!");
        }
    }

    @Test
    void testGetFileLocInfoCaseByCase() {
        try {
            List<String> caseList = getTestCase();
            FileInfo fileInfo;
            for (int id = 0; id < caseList.size(); id++) {
                fileInfo = new FileInfo();
                fileInfo.setFileType(FileType.C);
                LocUtil.getFileLocInfo(new StringReader(caseList.get(id)), fileInfo);
                LOG.info("case id:{},{}", id, fileInfo.toString());
                assertEquals(result[id][CASE_RESULT_ARRAY_IND_BLANK_LINE], fileInfo.getBlankLineCounts());
                assertEquals(result[id][CASE_RESULT_ARRAY_IND_COMMENT_LINE], fileInfo.getCommentCounts());
                assertEquals(result[id][CASE_RESULT_ARRAY_IND_COMMENT_IN_LINE], fileInfo.getCommentInLineCounts());
            }
        } catch (SdongException e) {
            LOG.error("{}:{}", e.getErrorPosition(), e.getMessage());
            fail("should not get exception!");
        }
    }

    private List<String> getTestCase() throws SdongException {
        List<String> list = FileUtil.readFileToStringList(caseFileName);
        List<String> caseList = new ArrayList<String>();
        String testCase = "";
        for (String line : list) {
            if (line.isEmpty()) {
                caseList.add(testCase);
                testCase = "";
            } else {
                testCase = testCase + line + System.lineSeparator();
            }
        }
        if (!testCase.isEmpty()) {
            caseList.add(testCase);
        }
        return caseList;
    }

    @Test
    void testGetFileLocInfo_C() {
        String fileName = "input/loc/example/loc_example.c";
        int commentLineCount = 62;
        int blankLineCounts = 57;
        int commentInLineCounts = 14;
        int rowLineCounts = 277;
        int lineCounts = 158;
        try {
            FileInfo fileInfo = LocUtil.getFileLocInfo(new File(fileName));

            LOG.info("{}", fileInfo.toString());
            assertEquals(commentLineCount, fileInfo.getCommentCounts());
            assertEquals(blankLineCounts, fileInfo.getBlankLineCounts());
            assertEquals(commentInLineCounts, fileInfo.getCommentInLineCounts());
            assertEquals(lineCounts, fileInfo.getLineCounts());
            assertEquals(rowLineCounts, fileInfo.getRowLineCounts());
            assertEquals("a699c02176bab00a5a9f0c193c019f0a", fileInfo.getMd5());
        } catch (SdongException e) {
            LOG.error("{}:{}", e.getErrorPosition(), e.getMessage());
            fail("should not get exception!");
        }
    }

    @Test
    void testGetFileLocInfo_Java() {
        String fileName = "input/loc/example/loc_example.java";
        int commentLineCount = 9;
        int blankLineCounts = 5;
        int commentInLineCounts = 0;
        int rowLineCounts = 21;
        int lineCounts = 7;

        try {
            FileInfo fileInfo = LocUtil.getFileLocInfo(new File(fileName));

            LOG.info("{}", fileInfo.toString());
            assertEquals(commentLineCount, fileInfo.getCommentCounts());
            assertEquals(blankLineCounts, fileInfo.getBlankLineCounts());
            assertEquals(commentInLineCounts, fileInfo.getCommentInLineCounts());
            assertEquals(lineCounts, fileInfo.getLineCounts());
            assertEquals(rowLineCounts, fileInfo.getRowLineCounts());
            assertEquals("54a0b23afe764a9203e13fec72c7ad04", fileInfo.getMd5());
        } catch (SdongException e) {
            LOG.error("{}:{}", e.getErrorPosition(), e.getMessage());
            fail("should not get exception!");
        }
    }

    @Test
    void testGetFileLocInfo_Python() {
        String fileName = "input/loc/example/loc_example.py";
        int commentLineCount = 7;
        int blankLineCounts = 2;
        int commentInLineCounts = 0;
        int rowLineCounts = 14;
        int lineCounts = 5;
        try {
            FileInfo fileInfo = LocUtil.getFileLocInfo(new File(fileName));

            LOG.info("{}", fileInfo.toString());
            assertEquals(commentLineCount, fileInfo.getCommentCounts());
            assertEquals(blankLineCounts, fileInfo.getBlankLineCounts());
            assertEquals(commentInLineCounts, fileInfo.getCommentInLineCounts());
            assertEquals(lineCounts, fileInfo.getLineCounts());
            assertEquals(rowLineCounts, fileInfo.getRowLineCounts());
            assertEquals("e44019a4e0e7fb50d0bd6acd26592312", fileInfo.getMd5());
        } catch (SdongException e) {
            LOG.error("{}:{}", e.getErrorPosition(), e.getMessage());
            fail("should not get exception!");
        }
    }

    @Test
    void testGetFileLocInfo_Xml() {
        String fileName = "input/loc/example/loc_example.xml";
        int commentLineCount = 2;
        int blankLineCounts = 0;
        int commentInLineCounts = 1;
        int rowLineCounts = 14;
        int lineCounts = 12;
        try {
            FileInfo fileInfo = LocUtil.getFileLocInfo(new File(fileName));

            LOG.info("{}", fileInfo.toString());
            assertEquals(commentLineCount, fileInfo.getCommentCounts());
            assertEquals(blankLineCounts, fileInfo.getBlankLineCounts());
            assertEquals(commentInLineCounts, fileInfo.getCommentInLineCounts());
            assertEquals(lineCounts, fileInfo.getLineCounts());
            assertEquals(rowLineCounts, fileInfo.getRowLineCounts());
            assertEquals("7fd9d6551bed2f83b2b741655a432be1", fileInfo.getMd5());
        } catch (SdongException e) {
            LOG.error("{}:{}", e.getErrorPosition(), e.getMessage());
            fail("should not get exception!");
        }
    }
    
    @Test
    void testGetFileLocInfo_Properties() {
        // fileType=Properties, blankLineCounts=3, commentCounts=14, commentInLineCounts=0, fileSize=866, lineCounts=3,rowLineCounts=20
        String fileName = "input/loc/example/loc_example.properties";

        int commentLineCount = 14;
        int blankLineCounts = 3;
        int commentInLineCounts = 0;
        int rowLineCounts = 20;
        int lineCounts = 3;
        try {
            FileInfo fileInfo = LocUtil.getFileLocInfo(new File(fileName));

            LOG.info("{}", fileInfo.toString());
            assertEquals(commentLineCount, fileInfo.getCommentCounts());
            assertEquals(blankLineCounts, fileInfo.getBlankLineCounts());
            assertEquals(commentInLineCounts, fileInfo.getCommentInLineCounts());
            assertEquals(lineCounts, fileInfo.getLineCounts());
            assertEquals(rowLineCounts, fileInfo.getRowLineCounts());
            assertEquals("2fb102498981b7f9326113e565248762", fileInfo.getMd5());
        } catch (SdongException e) {
            LOG.error("{}:{}", e.getErrorPosition(), e.getMessage());
            fail("should not get exception!");
        }
    }

    @Test
    void testCaseFor_COMMENT_LINE() {
        List<String> lines;
        try {
            lines = FileUtil.readFileToStringList(caseFileName);
            FileType fileType = FileType.getFileTypeByExt(FileUtil.getFileExtension(caseFileName));

            FileTypeComment fileTypeComment = LocUtil.getFileTypeComment(fileType);

            List<String> matchingCase = Arrays.asList("case 0 ", "case 1 ", "case 18 ", "case 20 ", "case 38 ",
                    "case 39 ", "case 40 ", "case 43 ", "case 44 ", "case 47 ", "case 48 ", "case 49 ");
            verifyGetLineType(lines, fileTypeComment, matchingCase, LineType.COMMENT_LINE);
        } catch (SdongException e) {
            LOG.error("{}:{}", e.getErrorPosition(), e.getMessage());
            fail("should not get exception!");
        }
    }

    @Test
    void testCaseFor_COMMENT_START_LINE() {
        List<String> lines;
        try {
            lines = FileUtil.readFileToStringList(caseFileName);
            FileType fileType = FileType.getFileTypeByExt(FileUtil.getFileExtension(caseFileName));

            FileTypeComment fileTypeComment = LocUtil.getFileTypeComment(fileType);

            List<String> matchingCase = Arrays.asList("case 2 ", "case 3 ", "case 5 ", "case 9 ", "case 11 ",
                    "case 21 ", "case 23 ", "case 27 ", "case 29 ", "case 42 ", "case 45 ", "case 47 ", "case 48 ",
                    "case 49 ");
            verifyGetLineType(lines, fileTypeComment, matchingCase, LineType.COMMENT_START_LINE);

        } catch (SdongException e) {
            LOG.error("{}:{}", e.getErrorPosition(), e.getMessage());
            fail("should not get exception!");
        }
    }

    @Test
    void testCaseFor_CODE_COMMENT_START_LINE() {
        List<String> lines;
        try {
            lines = FileUtil.readFileToStringList(caseFileName);
            FileType fileType = FileType.getFileTypeByExt(FileUtil.getFileExtension(caseFileName));

            FileTypeComment fileTypeComment = LocUtil.getFileTypeComment(fileType);

            List<String> matchingCase = Arrays.asList("case 7 ", "case 10 ", "case 11 ", "case 13 ", "case 15 ",
                    "case 16 ", "case 17 ", "case 17 ", "case 25 ", "case 28 ", "case 29", "case 31", "case 33 ",
                    "case 34 ", "case 35", "case 35", "case 45");
            verifyGetLineType(lines, fileTypeComment, matchingCase, LineType.CODE_COMMENT_START_LINE);
        } catch (SdongException e) {
            LOG.error("{}:{}", e.getErrorPosition(), e.getMessage());
            fail("should not get exception!");
        }
    }

    private void verifyGetLineType(List<String> lines, FileTypeComment fileTypeComment, List<String> matchingCase,
            LineType checkLineType) {
        MultipleLineComment multiLineCommentStart = new MultipleLineComment();
        List<String> result = new ArrayList<String>();
        for (String line : lines) {
            LineType lineType = LocUtil.getLineType(line, fileTypeComment, multiLineCommentStart);
            if (lineType == checkLineType) {
                LOG.info("{}", line);
                result.add(line);
            }
        }
        verifyResult(matchingCase, result);
    }

    @Test
    void testCaseFor_COMMENT_END_LINE() {
        List<String> lines;
        try {
            lines = FileUtil.readFileToStringList(caseFileName);
            FileType fileType = FileType.getFileTypeByExt(FileUtil.getFileExtension(caseFileName));

            FileTypeComment fileTypeComment = LocUtil.getFileTypeComment(fileType);

            List<String> matchingCase = Arrays.asList("case 1 ", "case 2 ", "case 3 ", "case 6 ", "case 7 ", "case 10 ",
                    "case 11 ", "case 16 ", "case 17 ", "case 20 ", "case 21 ", "case 24 ", "case 25 ", "case 28 ",
                    "case 29 ", "case 34 ", "case 35 ", "case 37 ", "case 39 ", "case 40 ", "case 42 ", "case 43 ",
                    "case 44 ", "case 45 ", "case 47 ", "case 48 ");
            verifyGetMulCommentsLineType(lines, fileTypeComment, matchingCase, LineType.COMMENT_END_LINE);
        } catch (SdongException e) {
            LOG.error("{}:{}", e.getErrorPosition(), e.getMessage());
            fail("should not get exception!");
        }
    }

    @Test
    void testCaseFor_COMMENT_END_CODE_LINE() {
        List<String> lines;
        try {
            lines = FileUtil.readFileToStringList(caseFileName);
            FileType fileType = FileType.getFileTypeByExt(FileUtil.getFileExtension(caseFileName));

            FileTypeComment fileTypeComment = LocUtil.getFileTypeComment(fileType);

            List<String> matchingCase = Arrays.asList("case 4 ", "case 5 ", "case 8 ", "case 9 ", "case 12 ",
                    "case 13 ", "case 14 ", "case 15 ", "case 22 ", "case 23 ", "case 26 ", "case 27 ", "case 30 ",
                    "case 31 ", "case 32 ", "case 33 ", "case 36 ", "case 41 ", "case 46 ", "case 49 ");
            verifyGetMulCommentsLineType(lines, fileTypeComment, matchingCase, LineType.COMMENT_END_CODE_LINE);

        } catch (SdongException e) {
            LOG.error("{}:{}", e.getErrorPosition(), e.getMessage());
            fail("should not get exception!");
        }
    }

    @Test
    void testCaseFor_COMMENT_END_START_LINE() {
        List<String> lines;
        try {
            lines = FileUtil.readFileToStringList(caseFileName);
            FileType fileType = FileType.getFileTypeByExt(FileUtil.getFileExtension(caseFileName));

            FileTypeComment fileTypeComment = LocUtil.getFileTypeComment(fileType);

            List<String> matchingCase = Arrays.asList("case 45 ", "case 45 ");
            verifyGetMulCommentsLineType(lines, fileTypeComment, matchingCase, LineType.COMMENT_END_START_LINE);

        } catch (SdongException e) {
            LOG.error("{}:{}", e.getErrorPosition(), e.getMessage());
            fail("should not get exception!");
        }
    }

    @Test
    void testCaseFor_COMMENT_END_CODE_START_LINE() {
        List<String> lines;
        try {
            lines = FileUtil.readFileToStringList(caseFileName);
            FileType fileType = FileType.getFileTypeByExt(FileUtil.getFileExtension(caseFileName));

            FileTypeComment fileTypeComment = LocUtil.getFileTypeComment(fileType);

            List<String> matchingCase = Arrays.asList("case 10 ", "case 11 ", "case 16 ", "case 17 ", "case 28 ",
                    "case 29 ", "case 34 ", "case 35 ");
            verifyGetMulCommentsLineType(lines, fileTypeComment, matchingCase, LineType.COMMENT_END_CODE_START_LINE);
        } catch (SdongException e) {
            LOG.error("{}:{}", e.getErrorPosition(), e.getMessage());
            fail("should not get exception!");
        }
    }

    private void verifyGetMulCommentsLineType(List<String> lines, FileTypeComment fileTypeComment,
            List<String> matchingCase, LineType checkLineType) {
        MultipleLineComment multiLineCommentStart = fileTypeComment.getMultiLineCommentList().get(0);
        List<String> result = new ArrayList<String>();
        for (String line : lines) {
            LineType lineType = LocUtil.getMulCommentsLineType(line, fileTypeComment, multiLineCommentStart);
            if (lineType == checkLineType) {
                LOG.info("{}", line);
                result.add(line);
            }
        }
        verifyResult(matchingCase, result);
    }

    @Test
    void testGetFileLocInfoQuestionMark() {
        String input = "char *p = \"/* case 37 */ // case 37\";";
        String result = input.replaceAll(C_REG_STRING_VALUE, "\"\"");

        LOG.info("result:{}", result);
        assertEquals("char *p = \"\";", result);
    }

    @Test
    void testParseFileTypeComment() {
        ConcurrentMap<FileType, FileTypeComment> fileTypeCommentMap = LocUtil.getFileTypeCommentMap();
        LOG.info("Comment map size:{}", fileTypeCommentMap.size());
        assertEquals(FILE_TYPE_NUM, fileTypeCommentMap.size());

        FileTypeComment comment = fileTypeCommentMap.get(FileType.C);
        assertEquals(C_REG_ONELINE, comment.getRegOneLine());
        assertEquals(C_REG_STRING_VALUE, comment.getRegStringValue());
        assertEquals(1, comment.getOneLineCommentList().size());
        assertEquals(C_COMMENT_ONELINE, comment.getOneLineCommentList().get(0));
        assertEquals(1, comment.getMultiLineCommentList().size());
        assertEquals(C_COMMENT_MULTIPL_START, comment.getMultiLineCommentList().get(0).getStartComment());
        assertEquals(C_COMMENT_MULTIPL_END, comment.getMultiLineCommentList().get(0).getEndComment());

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
