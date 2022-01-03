package sdong.common.bean.rules.conditional;

import com.google.gson.annotations.SerializedName;

import sdong.common.bean.rules.RuleJsonConstants;

public class CondNodeIsType extends ConditionalNode {
    @SerializedName(value = RuleJsonConstants.CONDITIONAL_CON_IS_TYPE)
    CondIsType isType = new CondIsType();

    public CondIsType getIsType() {
        return isType;
    }

    public void setIsType(CondIsType isType) {
        this.isType = isType;
    }
}
