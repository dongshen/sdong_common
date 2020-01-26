package sdong.common.bean;

import java.io.Serializable;

public class FileInfo implements Serializable {

	private static final long serialVersionUID = -4954627059067418661L;

	int lineCounts;
    int commentCounts;
    int blankLineCounts;
    String md5;

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

    @Override
    public String toString() {
        return "FileInfo [blankLineCounts=" + blankLineCounts + ", commentCounts=" + commentCounts
                + ", lineCounts=" + lineCounts + ", md5=" + md5 + "]";
    }

    
}
