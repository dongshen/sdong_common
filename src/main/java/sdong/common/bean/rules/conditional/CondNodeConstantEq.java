package sdong.common.bean.rules.conditional;

import com.google.gson.annotations.SerializedName;

import sdong.common.bean.rules.RuleJsonConstants;

public class CondNodeConstantEq extends ConditionalNode {
    @SerializedName(value = RuleJsonConstants.CONDITIONAL_CONSTANT_EQ)
    CondConstantEq constantEq = new CondConstantEq();

    public CondConstantEq getConstantEq() {
        return constantEq;
    }

    public void setConstantEq(CondConstantEq constantEq) {
        this.constantEq = constantEq;
    }
}
