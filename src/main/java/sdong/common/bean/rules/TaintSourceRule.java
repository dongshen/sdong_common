package sdong.common.bean.rules;

import com.google.gson.annotations.SerializedName;

public class TaintSourceRule extends DataFlowRule{
    @SerializedName(value = RuleJsonConstants.SOURCE)
    private TaintSource source;

    public TaintSource getSource() {
        return source;
    }

    public void setSource(TaintSource source) {
        this.source = source;
    }
}
