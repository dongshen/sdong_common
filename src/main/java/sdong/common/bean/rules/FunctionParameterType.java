package sdong.common.bean.rules;

import com.google.gson.annotations.SerializedName;

public class FunctionParameterType {
    @SerializedName(value = RuleJsonConstants.PARM_INDEX)
    private int paramInd = 0;

    @SerializedName(value = RuleJsonConstants.PARM_TYPES)
    private RuleValueType paramType;

    public int getParamInd() {
        return paramInd;
    }

    public void setParamInd(int paramInd) {
        this.paramInd = paramInd;
    }

    public RuleValueType getParamType() {
        return paramType;
    }

    public void setParamType(RuleValueType paramType) {
        this.paramType = paramType;
    }
}
