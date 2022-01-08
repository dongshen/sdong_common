package sdong.common.bean.rules;

import com.google.gson.annotations.SerializedName;

public class TaintCleanse {
    @SerializedName(value = RuleJsonConstants.OUT_ARGS)
    private String outArgs;

    public String getOutArgs() {
        return outArgs;
    }

    public void setOutArgs(String outArgs) {
        this.outArgs = outArgs;
    }
}
