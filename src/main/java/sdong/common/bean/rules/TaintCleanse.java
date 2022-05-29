package sdong.common.bean.rules;

import com.google.gson.annotations.SerializedName;

public class TaintCleanse {
    @SerializedName(value = RuleJsonConstants.OUT_ARGS)
    private String outArgs;

    @SerializedName(value = RuleJsonConstants.TAINT_FLAGS)
    private String taintFlags;

    public String getOutArgs() {
        return outArgs;
    }

    public void setOutArgs(String outArgs) {
        this.outArgs = outArgs;
    }

    public String getTaintFlags() {
        return taintFlags;
    }

    public void setTaintFlags(String taintFlags) {
        this.taintFlags = taintFlags;
    }
}
