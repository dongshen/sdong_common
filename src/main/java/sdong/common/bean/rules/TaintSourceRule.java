package sdong.common.bean.rules;

import java.util.ArrayList;
import java.util.List;

public class TaintSourceRule extends DataFlowRule{
    private List<TaintSource> sourceList = new ArrayList<TaintSource>();

    public List<TaintSource> getSourceList() {
        return sourceList;
    }

    public void setSourceList(List<TaintSource> sourceList) {
        this.sourceList = sourceList;
    }
}
