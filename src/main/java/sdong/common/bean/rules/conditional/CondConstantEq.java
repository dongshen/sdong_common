package sdong.common.bean.rules.conditional;

import com.google.gson.annotations.SerializedName;

import sdong.common.bean.rules.RuleJsonConstants;

public class CondConstantEq extends CondConstant {
    @SerializedName(value = RuleJsonConstants.ARGS_VALUE)
    private String value = "";

    @SerializedName(value = RuleJsonConstants.ARGS_MODE)
    private String mode = "value";

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
