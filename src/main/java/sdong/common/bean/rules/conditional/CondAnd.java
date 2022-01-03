package sdong.common.bean.rules.conditional;

import com.google.gson.annotations.SerializedName;

import sdong.common.bean.rules.RuleJsonConstants;

import java.util.ArrayList;
import java.util.List;

public class CondAnd extends ConditionalNode {
    @SerializedName(value = RuleJsonConstants.CONDITIONAL_AND)
    List<ConditionalNode> opAnd = new ArrayList<ConditionalNode>();

    public List<ConditionalNode> getOpAnd() {
        return opAnd;
    }

    public void setOpAnd(List<ConditionalNode> opAnd) {
        this.opAnd = opAnd;
    }
}
