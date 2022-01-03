package sdong.common.bean.rules.conditional;

import com.google.gson.annotations.SerializedName;

import sdong.common.bean.rules.RuleJsonConstants;

public class CondConstant {
    @SerializedName(value = RuleJsonConstants.ARGS)
    String arg = "";

    public String getArg() {
        return arg;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }
}
