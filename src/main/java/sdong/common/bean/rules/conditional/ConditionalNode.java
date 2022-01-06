package sdong.common.bean.rules.conditional;

import com.google.gson.annotations.SerializedName;

import sdong.common.bean.rules.RuleJsonConstants;

import java.util.List;

public class ConditionalNode implements IconditionalNode {
    @SerializedName(value = RuleJsonConstants.CONDITIONAL_AND)
    private List<ConditionalNode> condAnd;

    @SerializedName(value = RuleJsonConstants.CONDITIONAL_OR)
    private List<ConditionalNode> condOr;

    @SerializedName(value = RuleJsonConstants.CONDITIONAL_NOT)
    private ConditionalNode condNot;

    @SerializedName(value = RuleJsonConstants.TAINT_FLAG)
    private String taintFlag;

    @SerializedName(value = RuleJsonConstants.CONDITIONAL_CON_IS_TYPE)
    private CondIsType isType;

    @SerializedName(value = RuleJsonConstants.CONDITIONAL_CON_IS_CONSTANT)
    private CondConstant isConstant;

    @SerializedName(value = RuleJsonConstants.CONDITIONAL_CONSTANT_EQ)
    private CondConstantEq constantEq;

    @SerializedName(value = RuleJsonConstants.CONDITIONAL_CONSTANT_GT)
    private CondConstantGt constantGt;

    @SerializedName(value = RuleJsonConstants.CONDITIONAL_CONSTANT_LT)
    private CondConstantLt constantLt;

    @SerializedName(value = RuleJsonConstants.CONDITIONAL_CONSTANT_MATCHES)
    CondConstantMatches constantMatches;

    public String getTaintFlag() {
        return taintFlag;
    }

    public void setTaintFlag(String taintFlag) {
        this.taintFlag = taintFlag;
    }

    public List<ConditionalNode> getCondAnd() {
        return condAnd;
    }

    public void setCondAnd(List<ConditionalNode> condAnd) {
        this.condAnd = condAnd;
    }

    public ConditionalNode getCondNot() {
        return condNot;
    }

    public void setCondNot(ConditionalNode condNot) {
        this.condNot = condNot;
    }

    public List<ConditionalNode> getCondOr() {
        return condOr;
    }

    public void setCondOr(List<ConditionalNode> condOr) {
        this.condOr = condOr;
    }

    public CondIsType getIsType() {
        return isType;
    }

    public void setIsType(CondIsType isType) {
        this.isType = isType;
    }

    public CondConstant getIsConstant() {
        return isConstant;
    }

    public void setIsConstant(CondConstant isConstant) {
        this.isConstant = isConstant;
    }

    public CondConstantEq getConstantEq() {
        return constantEq;
    }

    public void setConstantEq(CondConstantEq constantEq) {
        this.constantEq = constantEq;
    }

    public CondConstantGt getConstantGt() {
        return constantGt;
    }

    public void setConstantGt(CondConstantGt constantGt) {
        this.constantGt = constantGt;
    }

    public CondConstantLt getConstantLt() {
        return constantLt;
    }

    public void setConstantLt(CondConstantLt constantLt) {
        this.constantLt = constantLt;
    }

    public CondConstantMatches getConstantMatches() {
        return constantMatches;
    }

    public void setConstantMatches(CondConstantMatches constantMatches) {
        this.constantMatches = constantMatches;
    }
}
