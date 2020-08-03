package sdong.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdong.common.bean.loc.FileInfo;
import sdong.common.bean.loc.FileType;
import sdong.common.bean.loc.FileTypeComment;
import sdong.common.bean.loc.FileTypeComments;
import sdong.common.bean.loc.LineType;
import sdong.common.bean.loc.MultipleLineComment;
import sdong.common.exception.SdongException;

/**
 * Calculate file source of line count
 *
 */
public class LocUtil {
    public static final String COMMENT_SETTING_FILE = "filetype/fileTypeComment.json";

    private static final Logger LOG = LoggerFactory.getLogger(LocUtil.class);

    private static final int NOT_FIND_MARK = -1;

    private static ConcurrentHashMap<FileType, FileTypeComment> fileTypeCommentMap;

    // initial file type comment setting
    static {
        fileTypeCommentMap = new ConcurrentHashMap<FileType, FileTypeComment>();
        try (InputStream stream = LocUtil.class.getClassLoader().getResourceAsStream(LocUtil.COMMENT_SETTING_FILE);
                Reader reader = new InputStreamReader(stream)) {
            setFileTypeCommentMap(parseFileTypeComment(reader));
            LOG.info("Get comment setting: {}", fileTypeCommentMap.size());
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }

    /**
     * Load comment setting from config file
     *
     * @param inputReader input reader
     * @return file type comment map
     * @throws IOException file operation fail
     */
    public static ConcurrentHashMap<FileType, FileTypeComment> parseFileTypeComment(Reader inputReader)
            throws IOException {
        ConcurrentHashMap<FileType, FileTypeComment> fileTypeCommentMap = new ConcurrentHashMap<FileType, FileTypeComment>();
        try (Reader reader = new BufferedReader(inputReader)) {
            Gson gson = new GsonBuilder().create();
            FileTypeComments comments = gson.fromJson(reader, FileTypeComments.class);
            fileTypeCommentMap = comments.getFileTypeCommentMap();
        }

        return fileTypeCommentMap;
    }

    /**
     * Get file type comment
     * 
     * @param fileType input file type
     * @return FileTypeComment
     */
    public static FileTypeComment getFileTypeComment(FileType fileType) {
        return fileTypeCommentMap.get(fileType);
    }

    /**
     * Get file info
     * 
     * @param fileName file name
     * @return file info bean
     * @throws SdongException module exception
     */
    public static FileInfo getFileLocInfo(File file) throws SdongException {
        FileInfo fileInfo = new FileInfo();
        try (Reader reader = new InputStreamReader(new FileInputStream(file))) {
            // update basic info
            fileInfo.setFileName(file.getCanonicalPath());
            fileInfo.setFileSize(file.length());
            fileInfo.setMd5(CommonUtil.generateFileMd5(file));

            FileType fileType = FileType.getFileTypeByExt(FileUtil.getFileExtension(file.getName()));
            fileInfo.setFileType(fileType);
            if (fileType != null) {
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
    public static void getFileLocInfo(Reader reader, FileInfo fileInfo) throws SdongException {
        FileTypeComment fileTypeComment = fileTypeCommentMap.get(fileInfo.getFileType());
        if (fileTypeComment == null) {
            return;
        }

        try (BufferedReader bufReader = new BufferedReader(reader);) {
            String line = null;
            Optional<LineType> lineType;
            MultipleLineComment multiLineCommentStart = new MultipleLineComment();
            while ((line = bufReader.readLine()) != null) {
                fileInfo.setRowLineCounts(fileInfo.getRowLineCounts() + 1);

                lineType = getLineType(line, fileTypeComment, multiLineCommentStart);
                if (lineType.isPresent()) {
                    switch (lineType.get()) {
                        case BLANK_LINE:
                            fileInfo.setBlankLineCounts(fileInfo.getBlankLineCounts() + 1);
                            break;
                        case COMMENT_LINE:
                            fileInfo.setCommentCounts(fileInfo.getCommentCounts() + 1);
                            break;
                        case COMMENT_START_LINE:
                            fileInfo.setCommentCounts(fileInfo.getCommentCounts() + 1);
                            multiCommentLine(bufReader, fileInfo, fileTypeComment, multiLineCommentStart);
                            break;
                        case CODE_COMMENT_START_LINE:
                            fileInfo.setCommentInLineCounts(fileInfo.getCommentInLineCounts() + 1);
                            multiCommentLine(bufReader, fileInfo, fileTypeComment, multiLineCommentStart);
                            break;
                        case COMMENT_CODE_LINE:
                            fileInfo.setCommentInLineCounts(fileInfo.getCommentInLineCounts() + 1);
                            break;
                        default:
                            break;
                    }
                }
            }
            bufReader.close();
        } catch (IOException e) {
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
    public static Optional<LineType> getLineType(String line, FileTypeComment fileTypeComment,
            MultipleLineComment multiLineCommentStart) {
        String lineTrim = line.trim();
        if (lineTrim.isEmpty()) {
            return Optional.of(LineType.BLANK_LINE);
        }

        String lineWithoutStringValue = lineTrim;
        if (!fileTypeComment.getRegStringValue().isEmpty()) {
            lineWithoutStringValue = lineTrim.replaceAll(fileTypeComment.getRegStringValue(), "\"\"").trim();
        }
        String lineWithoutCommentPair = lineWithoutStringValue;
        if (!fileTypeComment.getRegOneLine().isEmpty()) {
            lineWithoutCommentPair = lineWithoutStringValue.replaceAll(fileTypeComment.getRegOneLine(), "").trim();
        }

        if (lineWithoutCommentPair.isEmpty()) {
            return Optional.of(LineType.COMMENT_LINE);
        }

        for (MultipleLineComment multiLineComment : fileTypeComment.getMultiLineCommentList()) {
            if (lineWithoutCommentPair.startsWith(multiLineComment.getStartComment())) {
                multiLineCommentStart.setStartComment(multiLineComment.getStartComment());
                multiLineCommentStart.setEndComment(multiLineComment.getEndComment());
                return Optional.of(LineType.COMMENT_START_LINE);
            } else if (lineWithoutCommentPair.indexOf(multiLineComment.getStartComment()) >= 0) {
                multiLineCommentStart.setStartComment(multiLineComment.getStartComment());
                multiLineCommentStart.setEndComment(multiLineComment.getEndComment());
                return Optional.of(LineType.CODE_COMMENT_START_LINE);
            }
        }

        if (!lineWithoutStringValue.equals(lineWithoutCommentPair)) {
            return Optional.of(LineType.COMMENT_CODE_LINE);
        } else {
            return Optional.of(LineType.CODE_LINE);
        }
    }

    private static void multiCommentLine(BufferedReader bfr, FileInfo fileInfo, FileTypeComment fileTypeComment,
            MultipleLineComment multiLineCommentStart) throws IOException {
        String line = null;
        Optional<LineType> lineType;
        while ((line = bfr.readLine()) != null) {
            fileInfo.setRowLineCounts(fileInfo.getRowLineCounts() + 1);

            lineType = getMulCommentsLineType(line, fileTypeComment, multiLineCommentStart);
            if (lineType.isPresent()) {
                switch (lineType.get()) {
                    case BLANK_LINE:
                    case COMMENT_LINE:
                    case COMMENT_END_START_LINE:
                        fileInfo.setCommentCounts(fileInfo.getCommentCounts() + 1);
                        break;
                    case COMMENT_END_LINE:
                        fileInfo.setCommentCounts(fileInfo.getCommentCounts() + 1);
                        return;
                    case COMMENT_END_CODE_LINE:
                        fileInfo.setCommentInLineCounts(fileInfo.getCommentInLineCounts() + 1);
                        return;
                    case COMMENT_END_CODE_START_LINE:
                        fileInfo.setCommentInLineCounts(fileInfo.getCommentInLineCounts() + 1);
                        break;
                    default:
                        fileInfo.setCommentCounts(fileInfo.getCommentCounts() + 1);
                        break;
                }
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
    public static Optional<LineType> getMulCommentsLineType(String line, FileTypeComment fileTypeComment,
            MultipleLineComment multiLineCommentStart) {
        String lineTrim = line.trim();
        if (lineTrim.isEmpty()) {
            return Optional.of(LineType.BLANK_LINE);
        }

        // check first end comment in line
        int endPos = lineTrim.indexOf(multiLineCommentStart.getEndComment());
        if (endPos == NOT_FIND_MARK) {
            return Optional.of(LineType.COMMENT_LINE);
        }

        if (lineTrim.length() == endPos + multiLineCommentStart.getEndComment().length() - 1) {
            return Optional.of(LineType.COMMENT_END_LINE);
        }

        // remove first end comment from line and check again;
        lineTrim = lineTrim.substring(endPos + multiLineCommentStart.getEndComment().length());

        Optional<LineType> lineType = getLineType(lineTrim, fileTypeComment, multiLineCommentStart);
        if (!lineType.isPresent()) {
            return lineType;
        }

        switch (lineType.get()) {
            case BLANK_LINE:
                lineType = Optional.of(LineType.COMMENT_END_LINE);
                break;
            case COMMENT_LINE:
                lineType = Optional.of(LineType.COMMENT_END_LINE);
                break;
            case CODE_LINE:
                lineType = Optional.of(LineType.COMMENT_END_CODE_LINE);
                break;
            case COMMENT_START_LINE:
                lineType = Optional.of(LineType.COMMENT_END_START_LINE);
                break;
            case CODE_COMMENT_START_LINE:
                lineType = Optional.of(LineType.COMMENT_END_CODE_START_LINE);
                break;
            case COMMENT_CODE_LINE:
                lineType = Optional.of(LineType.COMMENT_END_CODE_LINE);
                break;
            default:
                break;
        }
        return lineType;
    }

    public static ConcurrentHashMap<FileType, FileTypeComment> getFileTypeCommentMap() {
        return fileTypeCommentMap;
    }

    public static void setFileTypeCommentMap(ConcurrentHashMap<FileType, FileTypeComment> fileTypeCommentMap) {
        LocUtil.fileTypeCommentMap = fileTypeCommentMap;
    }

}
