package sdong.common.bean.rules;

import com.google.gson.annotations.SerializedName;

public class TaintCleanseRule extends DataFlowRule{
    @SerializedName(value = RuleJsonConstants.CLEANSE)
    private TaintCleanse cleanse = new TaintCleanse();

    public TaintCleanse getCleanse() {
        return cleanse;
    }

    public void setCleanse(TaintCleanse cleanse) {
        this.cleanse = cleanse;
    }   
}
