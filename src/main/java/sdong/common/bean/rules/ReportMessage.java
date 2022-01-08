package sdong.common.bean.rules;

import com.google.gson.annotations.SerializedName;

public class ReportMessage {
    @SerializedName(value = RuleJsonConstants.MSG_ID)
    private String msgssageId;

    @SerializedName(value = RuleJsonConstants.MSG_MSG)
    private String message;

    public String getMsgssageId() {
        return msgssageId;
    }

    public void setMsgssageId(String msgssageId) {
        this.msgssageId = msgssageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
