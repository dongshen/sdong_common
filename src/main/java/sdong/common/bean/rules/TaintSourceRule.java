package sdong.common.bean.rules;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TaintSourceRule extends DataFlowRule{
    @SerializedName(value = RuleJsonConstants.SOURCE)
    private List<TaintSource> sourceList = new ArrayList<TaintSource>();

    public List<TaintSource> getSourceList() {
        return sourceList;
    }

    public void setSourceList(List<TaintSource> sourceList) {
        this.sourceList = sourceList;
    }
}
