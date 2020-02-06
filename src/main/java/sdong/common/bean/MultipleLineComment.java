package sdong.common.bean;

import java.io.Serializable;

/**
 * multiple line comments bean.
 *
 */
public class MultipleLineComment implements Serializable {

    private static final long serialVersionUID = 6044143350320638418L;

    private String startComment;
    private String endComment;

    public String getStartComment() {
        return startComment;
    }

    public void setStartComment(String startComment) {
        this.startComment = startComment;
    }

    public String getEndComment() {
        return endComment;
    }

    public void setEndComment(String endComment) {
        this.endComment = endComment;
    }

    public void clear(){
        startComment = null;
        endComment = null;
    }
}