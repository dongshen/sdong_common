package sdong.common.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * file type related comment bean list
 *
 */
public class FileTypeComments implements Serializable {

    private static final long serialVersionUID = -3952331934176464798L;

    @SerializedName("fileTypeCommentList")
    private List<FileTypeComment> fileTypeCommentList = new ArrayList<FileTypeComment>();

    public List<FileTypeComment> getFileTypeCommentList() {
        return fileTypeCommentList;
    }

    public void setFileTypeCommentList(List<FileTypeComment> commentList) {
        this.fileTypeCommentList = commentList;
    }

    public ConcurrentHashMap<FileType, FileTypeComment> getFileTypeCommentMap() {
        ConcurrentHashMap<FileType, FileTypeComment> fileTypeCommentMap = new ConcurrentHashMap<FileType, FileTypeComment>();
        for (FileTypeComment comment : fileTypeCommentList) {
            fileTypeCommentMap.put(comment.getFileType(), comment);
        }
        return fileTypeCommentMap;
    }
}
