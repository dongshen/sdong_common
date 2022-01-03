package sdong.common.bean.rules.conditional;

import com.google.gson.annotations.SerializedName;

import sdong.common.bean.rules.RuleJsonConstants;

public class CondNodeConstantLt extends ConditionalNode {
    @SerializedName(value = RuleJsonConstants.CONDITIONAL_CONSTANT_LT)
    CondConstantLt constantLt = new CondConstantLt();

    public CondConstantLt getConstantLt() {
        return constantLt;
    }

    public void setConstantLt(CondConstantLt constantLt) {
        this.constantLt = constantLt;
    }
}
