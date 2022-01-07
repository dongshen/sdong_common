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

    ConditionalType type;

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
        setType(ConditionalType.AND);
        this.condAnd = condAnd;
    }

    public ConditionalNode getCondNot() {
        return condNot;
    }

    public void setCondNot(ConditionalNode condNot) {
        setType(ConditionalType.NOT);
        this.condNot = condNot;
    }

    public List<ConditionalNode> getCondOr() {
        return condOr;
    }

    public void setCondOr(List<ConditionalNode> condOr) {
        setType(ConditionalType.OR);
        this.condOr = condOr;
    }

    public CondIsType getIsType() {
        return isType;
    }

    public void setIsType(CondIsType isType) {
        setType(ConditionalType.IS_TYPE);
        this.isType = isType;
    }

    public CondConstant getIsConstant() {
        return isConstant;
    }

    public void setIsConstant(CondConstant isConstant) {
        setType(ConditionalType.IS_CONSTANT);
        this.isConstant = isConstant;
    }

    public CondConstantEq getConstantEq() {
        return constantEq;
    }

    public void setConstantEq(CondConstantEq constantEq) {
        setType(ConditionalType.CONSTANT_EQ);
        this.constantEq = constantEq;
    }

    public CondConstantGt getConstantGt() {
        return constantGt;
    }

    public void setConstantGt(CondConstantGt constantGt) {
        setType(ConditionalType.CONSTANT_GT);
        this.constantGt = constantGt;
    }

    public CondConstantLt getConstantLt() {
        return constantLt;
    }

    public void setConstantLt(CondConstantLt constantLt) {
        setType(ConditionalType.CONSTANT_LT);
        this.constantLt = constantLt;
    }

    public CondConstantMatches getConstantMatches() {
        return constantMatches;
    }

    public void setConstantMatches(CondConstantMatches constantMatches) {
        setType(ConditionalType.CONSTANT_MATCHES);
        this.constantMatches = constantMatches;
    }

    public ConditionalType getType() {
        if(type != null){
            return type;
        }

        if(condAnd != null){
            setType(ConditionalType.AND);
        }else if(condOr != null){
            setType(ConditionalType.OR);
        }else if(condNot != null){
            setType(ConditionalType.NOT);
        }else if(taintFlag != null){
            setType(ConditionalType.OR);
        }else if(isType != null){
            setType(ConditionalType.IS_TYPE);
        }else if(isConstant != null){
            setType(ConditionalType.IS_CONSTANT);
        }else if(constantEq != null){
            setType(ConditionalType.CONSTANT_EQ);
        }else if(constantGt != null){
            setType(ConditionalType.CONSTANT_GT);
        }else if(constantLt != null){
            setType(ConditionalType.CONSTANT_LT);            
        }else if(constantMatches != null){
            setType(ConditionalType.CONSTANT_MATCHES);            
        }else{
            setType(ConditionalType.OTHTERS); 
        }
        return type;
    }

    public void setType(ConditionalType type) {
        this.type = type;
    }
}
