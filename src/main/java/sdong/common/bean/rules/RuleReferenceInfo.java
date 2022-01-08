package sdong.common.bean.rules;

import com.google.gson.annotations.SerializedName;

public class RuleReferenceInfo {
    @SerializedName(value = RuleJsonConstants.REF_INFO_KEY)
    private String key;

    @SerializedName(value = RuleJsonConstants.REF_INFO_VALUE)
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
