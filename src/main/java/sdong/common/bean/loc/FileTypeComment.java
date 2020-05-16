package sdong.common.bean.loc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * file type related comment bean.
 *
 */
public class FileTypeComment implements Serializable {

    private static final long serialVersionUID = 8412407244894915596L;

    public FileTypeComment(FileType fileType) {
        this.fileType = fileType;
    }

    private FileType fileType;
    private String regStringValue = "";
    private String regOneLine = "";

    private List<String> stringMarkList = new ArrayList<String>();
    private List<String> oneLineCommentList = new ArrayList<String>();
    private List<MultipleLineComment> multiLineCommentList = new ArrayList<MultipleLineComment>();

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public List<String> getStringMarkList() {
        return stringMarkList;
    }

    public void setStringMarkList(List<String> stringMarkList) {
        this.stringMarkList = stringMarkList;
    }

    public void addStringMarkList(String stringMark) {
        this.stringMarkList.add(stringMark);
    }

    public List<String> getOneLineCommentList() {
        return oneLineCommentList;
    }

    public void setOneLineCommentList(List<String> oneLineCommentList) {
        this.oneLineCommentList = oneLineCommentList;
    }

    public void addOneLineCommentList(String oneLineComment) {
        this.oneLineCommentList.add(oneLineComment);
    }

    public List<MultipleLineComment> getMultiLineCommentList() {
        return multiLineCommentList;
    }

    public void setMultiLineCommentList(List<MultipleLineComment> multiLineCommentList) {
        this.multiLineCommentList = multiLineCommentList;
    }

    public void addMultiLineCommentList(MultipleLineComment multiLineComment) {
        this.multiLineCommentList.add(multiLineComment);
    }

    public void addAllMultiLineCommentList(List<MultipleLineComment> multiLineCommentList) {
        this.multiLineCommentList.addAll(multiLineCommentList);
    }

    public String getRegStringValue() {
        return regStringValue;
    }

    public void setRegStringValue(String regStringValue) {
        this.regStringValue = regStringValue;
    }

    public String getRegOneLine() {
        return regOneLine;
    }

    public void setRegOneLine(String regOneline) {
        this.regOneLine = regOneline;
    }

}
