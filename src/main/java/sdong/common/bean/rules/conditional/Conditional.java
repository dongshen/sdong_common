package sdong.common.bean.rules.conditional;

import com.google.gson.annotations.SerializedName;

import sdong.common.bean.rules.RuleJsonConstants;

public class Conditional {
    @SerializedName(value = RuleJsonConstants.CONDITIONAL)
    private ConditionalNode cond;

    public ConditionalNode getCond() {
        return cond;
    }

    public void setCond(ConditionalNode cond) {
        this.cond = cond;
    }
}
