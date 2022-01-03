package sdong.common.bean.rules;

import com.google.gson.annotations.SerializedName;

public class TaintPassThroughRule extends DataFlowRule{
    @SerializedName(value = RuleJsonConstants.PASSTHROUGH)
    private TaintPassThrough passthrough = new TaintPassThrough();

    public TaintPassThrough getPassthrough() {
        return passthrough;
    }

    public void setPassthrough(TaintPassThrough passthrough) {
        this.passthrough = passthrough;
    }
}
