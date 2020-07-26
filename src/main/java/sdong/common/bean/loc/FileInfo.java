package sdong.common.bean.loc;

import java.io.Serializable;

/**
 * Source file info for SLOC and md5
 *
 */
public class FileInfo implements Serializable {

    private static final long serialVersionUID = -4954627059067418661L;

    String fileName = "";
    FileType fileType;
    int rowLineCounts = 0;
    int lineCounts = 0;
    int commentCounts = 0;
    int blankLineCounts = 0;
    int commentInLineCounts = 0;
    long fileSize = 0l;
    String encoding = "";
    String md5 = "";
    String timestamp = "";

    public int getLineCounts() {
        return lineCounts;
    }

    public void setLineCounts(int lineCounts) {
        this.lineCounts = lineCounts;
    }

    public int getCommentCounts() {
        return commentCounts;
    }

    public void setCommentCounts(int commentCounts) {
        this.commentCounts = commentCounts;
    }

    public int getBlankLineCounts() {
        return blankLineCounts;
    }

    public void setBlankLineCounts(int blankLineCounts) {
        this.blankLineCounts = blankLineCounts;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public int getCommentInLineCounts() {
        return commentInLineCounts;
    }

    public void setCommentInLineCounts(int commentInLineCounts) {
        this.commentInLineCounts = commentInLineCounts;
    }

    public int getRowLineCounts() {
        return rowLineCounts;
    }

    public void setRowLineCounts(int rowLineCounts) {
        this.rowLineCounts = rowLineCounts;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "FileInfo [fileName=" + fileName + ", fileType=" + fileType + ", blankLineCounts=" + blankLineCounts
                + ", commentCounts=" + commentCounts + ", commentInLineCounts=" + commentInLineCounts + ", fileSize="
                + fileSize + ", lineCounts=" + lineCounts + ", rowLineCounts=" + rowLineCounts + ", timestamp="
                + timestamp + ", encoding=" + encoding + ", md5=" + md5 + "]";
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
}
