package sdong.common.bean.rules;

import com.google.gson.annotations.SerializedName;

public class EntryPointRule extends DataFlowRule {
    @SerializedName(value = RuleJsonConstants.RULE_ENTRY_POINT)
    private EntryPoint entryPoint;

    public EntryPoint getEntryPoint() {
        return entryPoint;
    }

    public void setEntryPoint(EntryPoint entryPoint) {
        this.entryPoint = entryPoint;
    }
}
