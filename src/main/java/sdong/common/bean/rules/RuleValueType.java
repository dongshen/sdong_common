package sdong.common.bean.rules;

import com.google.gson.annotations.SerializedName;

public class RuleValueType {
    @SerializedName(value = RuleJsonConstants.VALUE_TYPE_VALUE)
    String value;

    @SerializedName(value = RuleJsonConstants.VALUE_TYPE_PATTERN)
    String pattern;

    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getPattern() {
        return pattern;
    }
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
