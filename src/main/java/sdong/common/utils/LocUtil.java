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
 *
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

        // Go
        fileTypeComment = new FileTypeComment(FileType.Go);
        fileTypeComment.setRegOneline(REG_ONELINE);
        fileTypeComment.setRegStringValue(REG_STRING_VALUE);
        fileTypeComment.addOneLineCommentList(COMMENT_ONELINE);
        multiLineComment = new MultipleLineComment();
        multiLineComment.setStartComment(COMMENT_MULTIPL_START);
        multiLineComment.setEndComent(COMMENT_MULTIPL_END);
        fileTypeComment.addMultiLineCommentList(multiLineComment);
        fileTypeCommentMap.put(FileType.Go, fileTypeComment);

        // Kotlin
        fileTypeComment = new FileTypeComment(FileType.Kotlin);
        fileTypeComment.setRegOneline(REG_ONELINE);
        fileTypeComment.setRegStringValue(REG_STRING_VALUE);
        fileTypeComment.addOneLineCommentList(COMMENT_ONELINE);
        multiLineComment = new MultipleLineComment();
        multiLineComment.setStartComment(COMMENT_MULTIPL_START);
        multiLineComment.setEndComent(COMMENT_MULTIPL_END);
        fileTypeComment.addMultiLineCommentList(multiLineComment);
        fileTypeCommentMap.put(FileType.Kotlin, fileTypeComment);

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

        // Python
        fileTypeComment = new FileTypeComment(FileType.Python);
        fileTypeComment.setRegOneline("'''.*?'''|\"\"\".*?\"\"\"|^#.*");
        fileTypeComment.setRegStringValue("(?!\"\"\")\"[^\"]+(?!\"\"\")\"|(?!''')'[^']+(?!''')'");
        fileTypeComment.addOneLineCommentList("#");
        multiLineComment = new MultipleLineComment();
        multiLineComment.setStartComment("\"\"\"");
        multiLineComment.setEndComent("\"\"\"");
        fileTypeComment.addMultiLineCommentList(multiLineComment);
        multiLineComment = new MultipleLineComment();
        multiLineComment.setStartComment("'''");
        multiLineComment.setEndComent("'''");
        fileTypeComment.addMultiLineCommentList(multiLineComment);
        fileTypeCommentMap.put(FileType.Python, fileTypeComment);
    }

    /**
     * Get file type comment
     * 
     * @param fileType input file type
     * @return FileTypeComment
     */
    public FileTypeComment getFileTypeComment(FileType fileType) {
        return fileTypeCommentMap.get(fileType);
    }

    /**
     * Get file info
     * 
     * @param fileName file name
     * @return file info bean
     * @throws SdongException module exception
     */
    public FileInfo getFileLocInfo(File file) throws SdongException {
        FileInfo fileInfo = new FileInfo();
        try (Reader reader = new InputStreamReader(new FileInputStream(file))) {
            // update basic info
            fileInfo.setFileName(file.getCanonicalPath());
            fileInfo.setFileSize(file.length());
            fileInfo.setMd5(Util.generateFileMd5(file));

            FileType fileType = FileType.getFileType(FileUtil.getFileExtension(file.getName()));
            if (fileType != null) {
                fileInfo.setFileType(fileType);

                // get Loc of file
                getFileLocInfo(reader, fileInfo);

                // update LOC
                fileInfo.setLineCounts(
                        fileInfo.getRowLineCounts() - fileInfo.getBlankLineCounts() - fileInfo.getCommentCounts());
            } else {
                LOG.warn("Not support LOC for file:{}", file.getName());
                fileInfo.setRowLineCounts(FileUtil.getFileLineNum(file));
                fileInfo.setLineCounts(fileInfo.getRowLineCounts());
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
            MultipleLineComment multiLineCommentStart = new MultipleLineComment();
            while ((line = bufReader.readLine()) != null) {
                fileInfo.setRowLineCounts(fileInfo.getRowLineCounts() + 1);

                lineType = getLineType(line, fileTypeComment, multiLineCommentStart);
                switch (lineType) {
                case BLANK_LINE:
                    fileInfo.setBlankLineCounts(fileInfo.getBlankLineCounts() + 1);
                    break;
                case COMMNET_LINE:
                    fileInfo.setCommentCounts(fileInfo.getCommentCounts() + 1);
                    break;
                case COMMNET_START_LINE:
                    fileInfo.setCommentCounts(fileInfo.getCommentCounts() + 1);
                    multiCommentLine(bufReader, fileInfo, fileTypeComment, multiLineCommentStart);
                    break;
                case CODE_COMMNET_START_LINE:
                    fileInfo.setCommentInLineCounts(fileInfo.getCommentInLineCounts() + 1);
                    multiCommentLine(bufReader, fileInfo, fileTypeComment, multiLineCommentStart);
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
    public LineType getLineType(String line, FileTypeComment fileTypeComment,
            MultipleLineComment multiLineCommentStart) {
        String lineTrim = line.trim();
        if (lineTrim.isEmpty()) {
            return LineType.BLANK_LINE;
        }

        String lineWithoutStringValue = lineTrim.replaceAll(fileTypeComment.getRegStringValue(), "").trim();
        String lineWithoutCommentPair = lineWithoutStringValue.replaceAll(fileTypeComment.getRegOneline(), "").trim();
        LineType lineType = LineType.CODE_LINE;
        if (lineWithoutCommentPair.isEmpty()) {
            lineType = LineType.COMMNET_LINE;
        } else {
            for (MultipleLineComment multiLineComment : fileTypeComment.getMultiLineCommentList()) {
                if (lineWithoutCommentPair.startsWith(multiLineComment.getStartComment())) {
                    lineType = LineType.COMMNET_START_LINE;
                    multiLineCommentStart.setStartComment(multiLineComment.getStartComment());
                    multiLineCommentStart.setEndComent(multiLineComment.getEndComent());
                    return lineType;
                } else if (lineWithoutCommentPair.indexOf(multiLineComment.getStartComment()) >= 0) {
                    lineType = LineType.CODE_COMMNET_START_LINE;
                    multiLineCommentStart.setStartComment(multiLineComment.getStartComment());
                    multiLineCommentStart.setEndComent(multiLineComment.getEndComent());
                    return lineType;
                }
            }
            if (!lineWithoutStringValue.equals(lineWithoutCommentPair)) {
                lineType = LineType.COMMNET_CODE_LINE;
            }
        }

        return lineType;
    }

    private void multiCommentLine(BufferedReader bfr, FileInfo fileInfo, FileTypeComment fileTypeComment,
            MultipleLineComment multiLineCommentStart) throws IOException {
        String line = null;
        LineType lineType;
        while ((line = bfr.readLine()) != null) {
            fileInfo.setRowLineCounts(fileInfo.getRowLineCounts() + 1);

            lineType = getMulCommentsLineType(line, fileTypeComment, multiLineCommentStart);
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
     * @param line            input line
     * @param fileTypeComment file type comment
     * @return line type
     */
    public LineType getMulCommentsLineType(String line, FileTypeComment fileTypeComment,
            MultipleLineComment multiLineCommentStart) {
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
            lineType = checkMulCommentsLineType(lineWithoutCommentPair, fileTypeComment, multiLineCommentStart);
        }
        return lineType;
    }

    private LineType checkMulCommentsLineType(String lineWithoutCommentPair, FileTypeComment fileTypeComment,
            MultipleLineComment multiLineCommentStart) {
        int endPos = -1;
        int startPos = -1;
        LineType lineType = LineType.COMMNET_LINE;

        if (lineWithoutCommentPair.endsWith(multiLineCommentStart.getEndComent())) {
            lineType = LineType.COMMNET_END_LINE;
        } else {
            endPos = lineWithoutCommentPair.indexOf(multiLineCommentStart.getEndComent());
            if (endPos >= 0) {
                startPos = lineWithoutCommentPair.indexOf(multiLineCommentStart.getStartComment(),
                        endPos + multiLineCommentStart.getEndComent().length());
                if (startPos == -1) {
                    lineType = LineType.COMMNET_END_CODE_LINE;
                    for (MultipleLineComment multiLineComment : fileTypeComment.getMultiLineCommentList()) {
                        if (!multiLineComment.getStartComment().equals(multiLineCommentStart.getStartComment())) {
                            startPos = lineWithoutCommentPair.indexOf(multiLineComment.getStartComment(),
                                    endPos + multiLineCommentStart.getEndComent().length());
                            if (startPos >= 0) {
                                String subLine = lineWithoutCommentPair
                                        .substring(endPos + multiLineCommentStart.getEndComent().length(), startPos)
                                        .trim();
                                if (subLine.isEmpty()) {
                                    lineType = LineType.COMMNET_END_START_LINE;
                                } else {
                                    lineType = LineType.COMMNET_END_CODE_START_LINE;
                                }
                                multiLineCommentStart.setStartComment(multiLineComment.getStartComment());
                                multiLineCommentStart.setEndComent(multiLineComment.getEndComent());
                                break;
                            }
                        }
                    }
                } else {
                    String subLine = lineWithoutCommentPair
                            .substring(endPos + multiLineCommentStart.getEndComent().length(), startPos).trim();
                    if (subLine.isEmpty()) {
                        lineType = LineType.COMMNET_END_START_LINE;
                    } else {
                        lineType = LineType.COMMNET_END_CODE_START_LINE;
                    }
                }
            }
        }
        return lineType;
    }
}
