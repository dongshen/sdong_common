package sdong.common.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import sdong.common.bean.loc.FileInfo;
import sdong.common.bean.loc.FileType;
import sdong.common.bean.loc.FileTypeComment;
import sdong.common.bean.loc.LineType;
import sdong.common.bean.loc.MultipleLineComment;
import sdong.common.exception.SdongException;

public class LocUtilTest {

    private static final Logger LOG = LoggerFactory.getLogger(LocUtilTest.class);

    // test case file name
    String caseFileName = "input/loc/example/loc_cases.c";

    // test csae result. 0:blank line; 1:comment line; 2:comment in line;
    private static final int[][] result = { { 0, 1, 0 }, /* case 1 */{ 0, 1, 0 }, { 0, 3, 0 }, { 0, 3, 0 }, { 0, 0, 1 },
            { 0, 1, 1 }, /* case 6 */ { 0, 0, 1 }, { 0, 2, 1 }, { 0, 0, 1 }, { 0, 1, 1 }, { 0, 1, 1 },
            /* case 11 */ { 0, 2, 1 }, { 0, 0, 1 }, { 0, 0, 2 }, { 0, 0, 1 }, { 0, 0, 2 }, /* case 16 */ { 0, 1, 1 },
            { 0, 1, 2 }, { 0, 1, 0 }, { 0, 0, 1 }, { 0, 1, 0 }, /* case 21 */{ 0, 2, 0 }, { 0, 0, 1 }, { 0, 1, 1 },
            { 0, 0, 1 }, { 0, 2, 1 }, /* case 26 */ { 0, 0, 1 }, { 0, 1, 1 }, { 0, 1, 1 }, { 0, 2, 1 }, { 0, 0, 1 },
            /* case 31 */ { 0, 0, 2 }, { 0, 0, 1 }, { 0, 0, 2 }, { 0, 1, 1 }, { 0, 1, 2 }, /* csae 36 */ { 0, 0, 0 },
            { 0, 0, 0 }, { 0, 1, 0 }, { 0, 1, 0 }, { 0, 1, 0 }, /* case 41 */ { 0, 0, 1 }, { 0, 3, 0 }, { 0, 1, 0 },
            { 0, 1, 0 }, { 0, 3, 0 }, /* case 46 */{ 0, 0, 0 }, { 0, 3, 0 }, { 0, 2, 0 }, { 0, 1, 1 } };

    @Test
    public void testGetFileLocInfo_case() {
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
            LocUtil loc = new LocUtil();
            FileInfo fileInfo = loc.getFileLocInfo(new File(caseFileName));

            LOG.info("{}", fileInfo.toString());
            assertEquals(commentLineCount, fileInfo.getCommentCounts());
            assertEquals(blankLineCounts, fileInfo.getBlankLineCounts());
            assertEquals(commentInLineCounts, fileInfo.getCommentInLineCounts());
            assertEquals(rowLineCounts, fileInfo.getRowLineCounts());
            assertEquals("cdbeae51a04ffffdbbde623b9f66913f", fileInfo.getMd5());
        } catch (SdongException e) {
            LOG.error(e.getMessage());
            fail("should not get exception!");
        }
    }

    @Test
    public void testGetFileLocInfoCaseByCase() {
        try {
            List<String> caseList = getTestCase();
            FileInfo fileInfo;
            LocUtil loc = new LocUtil();
            for (int id = 0; id < caseList.size(); id++) {
                fileInfo = new FileInfo();
                fileInfo.setFileType(FileType.C);
                loc.getFileLocInfo(new StringReader(caseList.get(id)), fileInfo);
                LOG.info("case id:{},{}", id, fileInfo.toString());
                assertEquals(result[id][0], fileInfo.getBlankLineCounts());
                assertEquals(result[id][1], fileInfo.getCommentCounts());
                assertEquals(result[id][2], fileInfo.getCommentInLineCounts());
            }
        } catch (SdongException e) {
            LOG.error(e.getMessage());
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
    public void testGetFileLocInfo_C() {
        String fileName = "input/loc/example/loc_example.c";
        int commentLineCount = 62;
        int blankLineCounts = 57;
        int commentInLineCounts = 14;
        int rowLineCounts = 277;
        int lineCounts = 158;
        try {
            LocUtil loc = new LocUtil();
            FileInfo fileInfo = loc.getFileLocInfo(new File(fileName));

            LOG.info("{}", fileInfo.toString());
            assertEquals(commentLineCount, fileInfo.getCommentCounts());
            assertEquals(blankLineCounts, fileInfo.getBlankLineCounts());
            assertEquals(commentInLineCounts, fileInfo.getCommentInLineCounts());
            assertEquals(lineCounts, fileInfo.getLineCounts());
            assertEquals(rowLineCounts, fileInfo.getRowLineCounts());
            assertEquals("a699c02176bab00a5a9f0c193c019f0a", fileInfo.getMd5());
        } catch (SdongException e) {
            LOG.error(e.getMessage());
            fail("should not get exception!");
        }
    }

    @Test
    public void testGetFileLocInfo_Java() {
        String fileName = "input/loc/example/loc_example.java";
        int commentLineCount = 9;
        int blankLineCounts = 5;
        int commentInLineCounts = 0;
        int rowLineCounts = 21;
        int lineCounts = 7;

        try {
            LocUtil loc = new LocUtil();
            FileInfo fileInfo = loc.getFileLocInfo(new File(fileName));

            LOG.info("{}", fileInfo.toString());
            assertEquals(commentLineCount, fileInfo.getCommentCounts());
            assertEquals(blankLineCounts, fileInfo.getBlankLineCounts());
            assertEquals(commentInLineCounts, fileInfo.getCommentInLineCounts());
            assertEquals(lineCounts, fileInfo.getLineCounts());
            assertEquals(rowLineCounts, fileInfo.getRowLineCounts());
            assertEquals("54a0b23afe764a9203e13fec72c7ad04", fileInfo.getMd5());
        } catch (SdongException e) {
            LOG.error(e.getMessage());
            fail("should not get exception!");
        }
    }

    @Test
    public void testGetFileLocInfo_Python() {
        String fileName = "input/loc/example/loc_example.py";
        int commentLineCount = 7;
        int blankLineCounts = 2;
        int commentInLineCounts = 0;
        int rowLineCounts = 14;
        int lineCounts = 5;
        try {
            LocUtil loc = new LocUtil();
            FileInfo fileInfo = loc.getFileLocInfo(new File(fileName));

            LOG.info("{}", fileInfo.toString());
            assertEquals(commentLineCount, fileInfo.getCommentCounts());
            assertEquals(blankLineCounts, fileInfo.getBlankLineCounts());
            assertEquals(commentInLineCounts, fileInfo.getCommentInLineCounts());
            assertEquals(lineCounts, fileInfo.getLineCounts());
            assertEquals(rowLineCounts, fileInfo.getRowLineCounts());
            assertEquals("e44019a4e0e7fb50d0bd6acd26592312", fileInfo.getMd5());
        } catch (SdongException e) {
            LOG.error(e.getMessage());
            fail("should not get exception!");
        }
    }

    @Test
    public void testGetFileLocInfo_Xml() {
        String fileName = "input/loc/example/loc_example.xml";
        int commentLineCount = 2;
        int blankLineCounts = 0;
        int commentInLineCounts = 1;
        int rowLineCounts = 14;
        int lineCounts = 12;
        try {
            LocUtil loc = new LocUtil();
            FileInfo fileInfo = loc.getFileLocInfo(new File(fileName));

            LOG.info("{}", fileInfo.toString());
            assertEquals(commentLineCount, fileInfo.getCommentCounts());
            assertEquals(blankLineCounts, fileInfo.getBlankLineCounts());
            assertEquals(commentInLineCounts, fileInfo.getCommentInLineCounts());
            assertEquals(lineCounts, fileInfo.getLineCounts());
            assertEquals(rowLineCounts, fileInfo.getRowLineCounts());
            assertEquals("7fd9d6551bed2f83b2b741655a432be1", fileInfo.getMd5());
        } catch (SdongException e) {
            LOG.error(e.getMessage());
            fail("should not get exception!");
        }
    }

    @Test
    public void testCaseFor_COMMNET_LINE() {
        List<String> lines;
        try {
            lines = FileUtil.readFileToStringList(caseFileName);
            FileType fileType = FileType.getFileTypeByExt(FileUtil.getFileExtension(caseFileName));
            LocUtil loc = new LocUtil();
            FileTypeComment fileTypeComment = loc.getFileTypeComment(fileType);
            MultipleLineComment multiLineCommentStart = new MultipleLineComment();

            List<String> matchingCase = Arrays.asList("case 0 ", "case 1 ", "case 18 ", "case 20 ", "case 38 ",
                    "case 39 ", "case 40 ", "case 43 ", "case 44 ", "case 47 ", "case 48 ", "case 49 ");
            List<String> result = new ArrayList<String>();

            for (String line : lines) {
                if (loc.getLineType(line, fileTypeComment, multiLineCommentStart) == LineType.COMMNET_LINE) {
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
    public void testCaseFor_COMMNET_START_LINE() {
        List<String> lines;
        try {
            lines = FileUtil.readFileToStringList(caseFileName);
            FileType fileType = FileType.getFileTypeByExt(FileUtil.getFileExtension(caseFileName));
            LocUtil loc = new LocUtil();
            FileTypeComment fileTypeComment = loc.getFileTypeComment(fileType);
            MultipleLineComment multiLineCommentStart = new MultipleLineComment();

            List<String> matchingCase = Arrays.asList("case 2 ", "case 3 ", "case 5 ", "case 9 ", "case 11 ",
                    "case 21 ", "case 23 ", "case 27 ", "case 29 ", "case 42 ", "case 45 ", "case 47 ", "case 48 ",
                    "case 49 ");
            List<String> result = new ArrayList<String>();
            for (String line : lines) {
                if (loc.getLineType(line, fileTypeComment, multiLineCommentStart) == LineType.COMMNET_START_LINE) {
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
    public void testCaseFor_CODE_COMMNET_START_LINE() {
        List<String> lines;
        try {
            lines = FileUtil.readFileToStringList(caseFileName);
            FileType fileType = FileType.getFileTypeByExt(FileUtil.getFileExtension(caseFileName));
            LocUtil loc = new LocUtil();
            FileTypeComment fileTypeComment = loc.getFileTypeComment(fileType);
            MultipleLineComment multiLineCommentStart = new MultipleLineComment();

            List<String> matchingCase = Arrays.asList("case 7 ", "case 10 ", "case 11 ", "case 13 ", "case 15 ",
                    "case 16 ", "case 17 ", "case 17 ", "case 25 ", "case 28 ", "case 29", "case 31", "case 33 ",
                    "case 34 ", "case 35", "case 35", "case 45");
            List<String> result = new ArrayList<String>();
            for (String line : lines) {
                if (loc.getLineType(line, fileTypeComment, multiLineCommentStart) == LineType.CODE_COMMNET_START_LINE) {
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
    public void testCaseFor_COMMNET_END_LINE() {
        List<String> lines;
        try {
            lines = FileUtil.readFileToStringList(caseFileName);
            FileType fileType = FileType.getFileTypeByExt(FileUtil.getFileExtension(caseFileName));
            LocUtil loc = new LocUtil();
            FileTypeComment fileTypeComment = loc.getFileTypeComment(fileType);
            MultipleLineComment multiLineCommentStart = fileTypeComment.getMultiLineCommentList().get(0);

            List<String> matchingCase = Arrays.asList("case 1 ", "case 2 ", "case 3 ", "case 6 ", "case 7 ", "case 10 ",
                    "case 11 ", "case 16 ", "case 17 ", "case 20 ", "case 21 ", "case 24 ", "case 25 ", "case 28 ",
                    "case 29 ", "case 34 ", "case 35 ", "case 37 ", "case 39 ", "case 40 ", "case 42 ", "case 43 ",
                    "case 44 ", "case 45 ", "case 47 ", "case 48 ");
            List<String> result = new ArrayList<String>();
            for (String line : lines) {
                if (loc.getMulCommentsLineType(line, fileTypeComment,
                        multiLineCommentStart) == LineType.COMMNET_END_LINE) {
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
    public void testCaseFor_COMMNET_END_CODE_LINE() {
        List<String> lines;
        try {
            lines = FileUtil.readFileToStringList(caseFileName);
            FileType fileType = FileType.getFileTypeByExt(FileUtil.getFileExtension(caseFileName));
            LocUtil loc = new LocUtil();
            FileTypeComment fileTypeComment = loc.getFileTypeComment(fileType);
            MultipleLineComment multiLineCommentStart = fileTypeComment.getMultiLineCommentList().get(0);

            List<String> matchingCase = Arrays.asList("case 4 ", "case 5 ", "case 8 ", "case 9 ", "case 12 ",
                    "case 13 ", "case 14 ", "case 15 ", "case 22 ", "case 23 ", "case 26 ", "case 27 ", "case 30 ",
                    "case 31 ", "case 32 ", "case 33 ", "case 36 ", "case 41 ", "case 46 ", "case 49 ");
            List<String> result = new ArrayList<String>();
            for (String line : lines) {
                if (loc.getMulCommentsLineType(line, fileTypeComment,
                        multiLineCommentStart) == LineType.COMMNET_END_CODE_LINE) {
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
    public void testCaseFor_COMMNET_END_START_LINE() {
        List<String> lines;
        try {
            lines = FileUtil.readFileToStringList(caseFileName);
            FileType fileType = FileType.getFileTypeByExt(FileUtil.getFileExtension(caseFileName));
            LocUtil loc = new LocUtil();
            FileTypeComment fileTypeComment = loc.getFileTypeComment(fileType);
            MultipleLineComment multiLineCommentStart = fileTypeComment.getMultiLineCommentList().get(0);

            List<String> matchingCase = Arrays.asList("case 45 ", "case 45 ");
            List<String> result = new ArrayList<String>();
            for (String line : lines) {
                if (loc.getMulCommentsLineType(line, fileTypeComment,
                        multiLineCommentStart) == LineType.COMMNET_END_START_LINE) {
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
    public void testCaseFor_COMMNET_END_CODE_START_LINE() {
        List<String> lines;
        try {
            lines = FileUtil.readFileToStringList(caseFileName);
            FileType fileType = FileType.getFileTypeByExt(FileUtil.getFileExtension(caseFileName));
            LocUtil loc = new LocUtil();
            FileTypeComment fileTypeComment = loc.getFileTypeComment(fileType);
            MultipleLineComment multiLineCommentStart = fileTypeComment.getMultiLineCommentList().get(0);

            List<String> matchingCase = Arrays.asList("case 10 ", "case 11 ", "case 16 ", "case 17 ", "case 28 ",
                    "case 29 ", "case 34 ", "case 35 ");
            List<String> result = new ArrayList<String>();
            for (String line : lines) {
                if (loc.getMulCommentsLineType(line, fileTypeComment,
                        multiLineCommentStart) == LineType.COMMNET_END_CODE_START_LINE) {
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
    public void testGetFileLocInfoQuestionMark() {
        String input = "char *p = \"/* case 37 */ // case 37\";";
        String result = input.replaceAll(LocUtil.REG_STRING_VALUE, "\"\"");

        LOG.info("result:{}", result);
        assertEquals("char *p = \"\";", result);
    }

    @Test
    public void testParseFileTypeComment() {
        LocUtil loc = new LocUtil();
        ConcurrentHashMap<FileType, FileTypeComment> fileTypeCommentMap = loc.getFileTypeCommentMap();
        LOG.info("Comment map size:{}", fileTypeCommentMap.size());
        assertEquals(5, fileTypeCommentMap.size());

        FileTypeComment comment = fileTypeCommentMap.get(FileType.C);
        assertEquals(LocUtil.REG_ONELINE, comment.getRegOneLine());
        assertEquals(LocUtil.REG_STRING_VALUE, comment.getRegStringValue());
        assertEquals(1, comment.getOneLineCommentList().size());
        assertEquals(LocUtil.COMMENT_ONELINE, comment.getOneLineCommentList().get(0));
        assertEquals(1, comment.getMultiLineCommentList().size());
        assertEquals(LocUtil.COMMENT_MULTIPL_START, comment.getMultiLineCommentList().get(0).getStartComment());
        assertEquals(LocUtil.COMMENT_MULTIPL_END, comment.getMultiLineCommentList().get(0).getEndComment());

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
