package sdong.common.bean.rules.conditional;

import com.google.gson.annotations.SerializedName;

import sdong.common.bean.rules.RuleJsonConstants;

public class CondNot extends ConditionalNode {
    @SerializedName(value = RuleJsonConstants.CONDITIONAL_NOT)
    ConditionalNode opNot = new ConditionalNode();

    public ConditionalNode getOpNot() {
        return opNot;
    }

    public void setOpNot(ConditionalNode opNot) {
        this.opNot = opNot;
    }   
}
