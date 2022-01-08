package sdong.common.bean.rules;

import com.google.gson.annotations.SerializedName;

public class TaintPassThrough {
    @SerializedName(value = RuleJsonConstants.IN_ARGS)
    private String inArgs;

    @SerializedName(value = RuleJsonConstants.OUT_ARGS)
    private String outArgs;

    public String getInArgs() {
        return inArgs;
    }

    public void setInArgs(String inArgs) {
        this.inArgs = inArgs;
    }

    public String getOutArgs() {
        return outArgs;
    }

    public void setOutArgs(String outArgs) {
        this.outArgs = outArgs;
    }
}
