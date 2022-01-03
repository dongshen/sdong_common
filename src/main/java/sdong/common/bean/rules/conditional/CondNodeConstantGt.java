package sdong.common.bean.rules.conditional;

import com.google.gson.annotations.SerializedName;

import sdong.common.bean.rules.RuleJsonConstants;

public class CondNodeConstantGt extends ConditionalNode {
    @SerializedName(value = RuleJsonConstants.CONDITIONAL_CONSTANT_GT)
    CondConstantGt constantGt = new CondConstantGt();

    public CondConstantGt getConstantGt() {
        return constantGt;
    }

    public void setConstantGt(CondConstantGt constantGt) {
        this.constantGt = constantGt;
    }
}
