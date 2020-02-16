package sdong.common.bean;

import java.io.Serializable;

/**
 * Source file info Summary
 *
 */
public class FileInfoSum implements Serializable {
    private static final long serialVersionUID = -8661761088227801742L;    
    FileType fileType;
    int filesCounts;
    int rowLineCounts;
    int lineCounts;
    int commentCounts;
    int blankLineCounts;
    int commentInLineCounts;
    long fileSize;


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

    public int getFilesCounts() {
        return filesCounts;
    }

    public void setFilesCounts(int filesCounts) {
        this.filesCounts = filesCounts;
    }
    
    @Override
    public String toString() {
        return "FileInfoSum [fileType=" + fileType+", filesCounts=" + filesCounts + ", blankLineCounts=" + blankLineCounts
                + ", commentCounts=" + commentCounts + ", commentInLineCounts=" + commentInLineCounts + ", fileSize="
                + fileSize + ", lineCounts=" + lineCounts + ", rowLineCounts=" + rowLineCounts + "]";
    }
}
