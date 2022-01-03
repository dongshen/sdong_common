package sdong.common.bean.rules.conditional;

import com.google.gson.annotations.SerializedName;

import sdong.common.bean.rules.RuleJsonConstants;

public class CondNodeIsContant extends ConditionalNode {
    @SerializedName(value = RuleJsonConstants.CONDITIONAL_CON_IS_CONSTANT)
    CondConstant isConstant = new CondConstant();

    public CondConstant getIsConstant() {
        return isConstant;
    }

    public void setIsConstant(CondConstant isConstant) {
        this.isConstant = isConstant;
    }   
}
