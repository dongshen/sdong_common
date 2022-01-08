package sdong.common.bean.rules.conditional;

import com.google.gson.annotations.SerializedName;

import sdong.common.bean.rules.RuleJsonConstants;

public class CondConstantMatches extends CondConstant {
    @SerializedName(value = RuleJsonConstants.VALUE_TYPE_PATTERN)
    private String pattern;

    @SerializedName(value = RuleJsonConstants.ARGS_MODE)
    private String mode;

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
