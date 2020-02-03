package sdong.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.concurrent.ConcurrentHashMap;

import sdong.common.bean.FileInfo;
import sdong.common.bean.FileType;
import sdong.common.bean.FileTypeComment;
import sdong.common.bean.LineType;
import sdong.common.bean.MultipleLineComment;
import sdong.common.exception.SdongException;

/**
 * Calculate file source of line count
 */
public class LocUtil {
    public static final String REG_STRING_VALUE = "\".+?\"|'.+?'";
    public static final String REG_ONELINE = "\\/\\*.*?\\*\\/|\\/\\/.*";
    public static final String COMMENT_MULTIPL_START = "/*";
    public static final String COMMENT_MULTIPL_END = "*/";
    public static final String COMMENT_ONELINE = "//";

    private static final Logger LOG = LoggerFactory.getLogger(LocUtil.class);

    private static ConcurrentHashMap<FileType, FileTypeComment> fileTypeCommentMap;

    public LocUtil() {
        fileTypeCommentMap = new ConcurrentHashMap<FileType, FileTypeComment>();

        // C
        FileTypeComment fileTypeComment = new FileTypeComment(FileType.C);
        fileTypeComment.setRegOneline(REG_ONELINE);
        fileTypeComment.setRegStringValue(REG_STRING_VALUE);
        fileTypeComment.addOneLineCommentList(COMMENT_ONELINE);
        MultipleLineComment multiLineComment = new MultipleLineComment();
        multiLineComment.setStartComment(COMMENT_MULTIPL_START);
        multiLineComment.setEndComent(COMMENT_MULTIPL_END);
        fileTypeComment.addMultiLineCommentList(multiLineComment);
        fileTypeCommentMap.put(FileType.C, fileTypeComment);

        // Java
        fileTypeComment = new FileTypeComment(FileType.Java);
        fileTypeComment.setRegOneline(REG_ONELINE);
        fileTypeComment.setRegStringValue(REG_STRING_VALUE);
        fileTypeComment.addOneLineCommentList(COMMENT_ONELINE);
        multiLineComment = new MultipleLineComment();
        multiLineComment.setStartComment(COMMENT_MULTIPL_START);
        multiLineComment.setEndComent(COMMENT_MULTIPL_END);
        fileTypeComment.addMultiLineCommentList(multiLineComment);
        fileTypeCommentMap.put(FileType.Java, fileTypeComment);

        // JavaScript
        fileTypeComment = new FileTypeComment(FileType.JavaScript);
        fileTypeComment.setRegOneline(REG_ONELINE);
        fileTypeComment.setRegStringValue(REG_STRING_VALUE);
        fileTypeComment.addOneLineCommentList(COMMENT_ONELINE);
        multiLineComment = new MultipleLineComment();
        multiLineComment.setStartComment(COMMENT_MULTIPL_START);
        multiLineComment.setEndComent(COMMENT_MULTIPL_END);
        fileTypeComment.addMultiLineCommentList(multiLineComment);
        fileTypeCommentMap.put(FileType.JavaScript, fileTypeComment);
    }

    /**
     * Get file type comment
     * 
     * @param fileType input file type
     * @return FileTypeComment
     */
    public FileTypeComment getFileTypeComment(FileType fileType){
        return fileTypeCommentMap.get(fileType);
    }

    /**
     * Get file info
     * 
     * @param fileName file name
     * @return file info bean
     * @throws SdongException module exception
     */
    public FileInfo getFileLocInfo(String fileName) throws SdongException {
        FileInfo fileInfo = new FileInfo();
        try (Reader reader = new InputStreamReader(new FileInputStream(fileName))) {
            // update basic info
            File file = new File(fileName);
            fileInfo.setFileName(file.getCanonicalPath());
            FileType fileType = FileType.getFileType(FileUtil.getFileExtension(fileName));
            if (fileType != null) {
                fileInfo.setFileType(fileType);
                fileInfo.setFileSize(file.length());
                fileInfo.setMd5(Util.generateFileMd5(fileName));

                // get Loc of file
                getFileLocInfo(reader, fileInfo);

                // update LOC
                fileInfo.setLineCounts(
                        fileInfo.getRowLineCounts() - fileInfo.getBlankLineCounts() - fileInfo.getCommentCounts());
            } else {
                LOG.warn("Not support LOC for file:{}", fileName);
            }
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new SdongException(e.getMessage());
        }
        return fileInfo;
    }

    /**
     * Get file info from reader interface
     * 
     * @param reader   String, file reader
     * @param fileInfo file info bean
     * @throws SdongException module exception
     */
    public void getFileLocInfo(Reader reader, FileInfo fileInfo) throws SdongException {

        FileTypeComment fileTypeComment = fileTypeCommentMap.get(fileInfo.getFileType());
        if (fileTypeComment == null) {
            return;
        }

        try (BufferedReader bufReader = new BufferedReader(reader);) {
            String line = null;
            LineType lineType;
            while ((line = bufReader.readLine()) != null) {
                fileInfo.setRowLineCounts(fileInfo.getRowLineCounts() + 1);

                lineType = getLineType(line, fileTypeComment);
                switch (lineType) {
                case BLANK_LINE:
                    fileInfo.setBlankLineCounts(fileInfo.getBlankLineCounts() + 1);
                    break;
                case COMMNET_LINE:
                    fileInfo.setCommentCounts(fileInfo.getCommentCounts() + 1);
                    break;
                case COMMNET_START_LINE:
                    fileInfo.setCommentCounts(fileInfo.getCommentCounts() + 1);
                    multiCommentLine(bufReader, fileInfo, fileTypeComment);
                    break;
                case CODE_COMMNET_START_LINE:
                    fileInfo.setCommentInLineCounts(fileInfo.getCommentInLineCounts() + 1);
                    multiCommentLine(bufReader, fileInfo, fileTypeComment);
                    break;
                case COMMNET_CODE_LINE:
                    fileInfo.setCommentInLineCounts(fileInfo.getCommentInLineCounts() + 1);
                    break;
                default:
                    break;
                }
            }
            bufReader.close();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new SdongException(e.getMessage());
        }
    }

    /**
     * Get line comment status
     * 
     * @param line            input line
     * @param fileTypeComment file type comment bean
     * @return line type
     */
    public  LineType getLineType(String line, FileTypeComment fileTypeComment) {
        String lineTrim = line.trim();
        if (lineTrim.isEmpty()) {
            return LineType.BLANK_LINE;
        }

        String lineWithoutStringValue = lineTrim.replaceAll(fileTypeComment.getRegStringValue(), "").trim();
        String lineWithoutCommentPair = lineWithoutStringValue.replaceAll(fileTypeComment.getRegOneline(), "").trim();
        LineType lineType = LineType.CODE_LINE;
        if (lineWithoutCommentPair.isEmpty()) {
            lineType = LineType.COMMNET_LINE;
        } else if (checkComentStartLine(lineWithoutCommentPair, fileTypeComment)) {
            lineType = LineType.COMMNET_START_LINE;
        } else if (checkComentStartLineWithCode(lineWithoutCommentPair, fileTypeComment)) {
            lineType = LineType.CODE_COMMNET_START_LINE;
        } else if (!lineWithoutStringValue.equals(lineWithoutCommentPair)) {
            lineType = LineType.COMMNET_CODE_LINE;
        }

        return lineType;
    }

    private  boolean checkComentStartLine(String line, FileTypeComment fileTypeComment) {
        boolean isCommentStartLine = false;
        for (MultipleLineComment multiLineComment : fileTypeComment.getMultiLineCommentList()) {
            if (line.startsWith(multiLineComment.getStartComment())) {
                isCommentStartLine = true;
                break;
            }
        }
        return isCommentStartLine;
    }

    private  boolean checkComentStartLineWithCode(String line, FileTypeComment fileTypeComment) {
        boolean isCommentStartLineWithCode = false;
        for (MultipleLineComment multiLineComment : fileTypeComment.getMultiLineCommentList()) {
            if (line.indexOf(multiLineComment.getStartComment()) >= 0) {
                isCommentStartLineWithCode = true;
                break;
            }
        }
        return isCommentStartLineWithCode;
    }

    private  void multiCommentLine(BufferedReader bfr, FileInfo fileInfo, FileTypeComment fileTypeComment)
            throws IOException {
        String line = null;
        LineType lineType;
        while ((line = bfr.readLine()) != null) {
            line = line.trim();
            fileInfo.setRowLineCounts(fileInfo.getRowLineCounts() + 1);

            lineType = getMulCommentsLineType(line, fileTypeComment);
            switch (lineType) {
            case BLANK_LINE:
            case COMMNET_LINE:
            case COMMNET_END_START_LINE:
                fileInfo.setCommentCounts(fileInfo.getCommentCounts() + 1);
                break;
            case COMMNET_END_LINE:
                fileInfo.setCommentCounts(fileInfo.getCommentCounts() + 1);
                return;
            case COMMNET_END_CODE_LINE:
                fileInfo.setCommentInLineCounts(fileInfo.getCommentInLineCounts() + 1);
                return;
            case COMMNET_END_CODE_START_LINE:
                fileInfo.setCommentInLineCounts(fileInfo.getCommentInLineCounts() + 1);
                break;
            default:
                fileInfo.setCommentCounts(fileInfo.getCommentCounts() + 1);
                break;
            }
        }
        return;
    }

    /**
     * Get line type for multiple line comment end.
     * 
     * @param line input line 
     * @param fileTypeComment file type comment
     * @return line type
     */
    public  LineType getMulCommentsLineType(String line, FileTypeComment fileTypeComment) {
        String lineTrim = line.trim();
        if (lineTrim.isEmpty()) {
            return LineType.BLANK_LINE;
        }
        String lineWithoutStringValue = lineTrim.replaceAll(fileTypeComment.getRegStringValue(), "").trim();
        String lineWithoutCommentPair = lineWithoutStringValue.replaceAll(fileTypeComment.getRegOneline(), "").trim();
        LineType lineType = LineType.COMMNET_LINE;
        if (lineWithoutCommentPair.isEmpty()) {
            lineType = LineType.COMMNET_LINE;
        } else {
            int endPos = -1;
            int startPos = -1;
            for (MultipleLineComment multiLineComment : fileTypeComment.getMultiLineCommentList()) {
                if (lineWithoutCommentPair.endsWith(multiLineComment.getEndComent())) {
                    lineType = LineType.COMMNET_END_LINE;
                    break;
                }
                endPos = lineWithoutCommentPair.indexOf(multiLineComment.getEndComent());
                if (endPos >= 0) {
                    startPos = lineWithoutCommentPair.indexOf(multiLineComment.getStartComment(), endPos);
                    if (startPos == -1) {
                        lineType = LineType.COMMNET_END_CODE_LINE;
                    } else {
                        String subLine = lineWithoutCommentPair.substring(endPos + multiLineComment.getEndComent().length(), startPos)
                                .trim();
                        if (subLine.isEmpty()) {
                            lineType = LineType.COMMNET_END_START_LINE;
                        } else {
                            lineType = LineType.COMMNET_END_CODE_START_LINE;
                        }
                    }
                    break;
                }
            }
        }
        return lineType;
    }

    public static boolean matchingStartLine(String str, String regex) {
        String result = str.replaceAll(regex, "").trim();
        return result.startsWith(COMMENT_MULTIPL_START);
    }

    public static boolean matchingStartLineWithCode(String str, String regex) {
        String result = str.replaceAll(regex, "").trim();
        if (result.isEmpty() || result.startsWith(COMMENT_MULTIPL_START)) {
            return false;
        } else {
            return result.matches(".*?\\/\\*.*");
        }
    }

    public static boolean matchingEndLine(String str, String regex) {
        String result = str.replaceAll(regex, "").trim();
        if (result.isEmpty() || result.startsWith(COMMENT_MULTIPL_START)) {
            return false;
        } else if (result.endsWith(COMMENT_MULTIPL_END)) {
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
        if (result.isEmpty() || result.startsWith(COMMENT_MULTIPL_START)) {
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
