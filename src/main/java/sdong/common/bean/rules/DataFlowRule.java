package sdong.common.bean.rules;

import java.util.ArrayList;
import java.util.List;

public class DataFlowRule extends RuleBase {
    private List<FunctionIdentifier> functions = new ArrayList<FunctionIdentifier>();

    public List<FunctionIdentifier> getFunctions() {
        return functions;
    }

    public void setFunctions(List<FunctionIdentifier> functions) {
        this.functions = functions;
    }
}
