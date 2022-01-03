package sdong.common.bean.rules.conditional;

import com.google.gson.annotations.SerializedName;

import sdong.common.bean.rules.RuleJsonConstants;

public class CondNodeTaintFlag extends ConditionalNode {
    @SerializedName(value = RuleJsonConstants.TAINT_FLAG)
    String taintFlag = "";

    public String getTaintFlag() {
        return taintFlag;
    }

    public void setTaintFlag(String taintFlag) {
        this.taintFlag = taintFlag;
    }
}
