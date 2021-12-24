package sdong.common.bean.rules;

import java.util.ArrayList;
import java.util.List;

public class DataFlowRule extends RuleBase {
    private String taintFlags = "";

    private List<FunctionIdentifier> functions = new ArrayList<FunctionIdentifier>();

    private String conditional = "";

    public String getTaintFlags() {
        return taintFlags;
    }

    public void setTaintFlags(String taintFlags) {
        this.taintFlags = taintFlags;
    }

    public List<FunctionIdentifier> getFunctions() {
        return functions;
    }

    public void setFunctions(List<FunctionIdentifier> functions) {
        this.functions = functions;
    }

    public String getConditional() {
        return conditional;
    }

    public void setConditional(String conditional) {
        this.conditional = conditional;
    }
}
