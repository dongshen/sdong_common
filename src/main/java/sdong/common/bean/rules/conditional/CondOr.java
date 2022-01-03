package sdong.common.bean.rules.conditional;

import com.google.gson.annotations.SerializedName;

import sdong.common.bean.rules.RuleJsonConstants;

import java.util.ArrayList;
import java.util.List;

public class CondOr extends ConditionalNode {
    @SerializedName(value = RuleJsonConstants.CONDITIONAL_OR)
    List<ConditionalNode> opOr = new ArrayList<ConditionalNode>();

    public List<ConditionalNode> getOpOr() {
        return opOr;
    }

    public void setOpOr(List<ConditionalNode> opOr) {
        this.opOr = opOr;
    }
}
