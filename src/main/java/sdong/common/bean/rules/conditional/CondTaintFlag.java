package sdong.common.bean.rules.conditional;

import com.google.gson.annotations.SerializedName;

import sdong.common.bean.rules.RuleJsonConstants;

public class CondTaintFlag {
    @SerializedName(value = RuleJsonConstants.TAINT_FLAG)
    String taingFlag = "";

    public String getTaingFlag() {
        return taingFlag;
    }

    public void setTaingFlag(String taingFlag) {
        this.taingFlag = taingFlag;
    }
}
