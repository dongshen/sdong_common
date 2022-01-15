package sdong.common.bean.rules;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DataFlowRule extends RuleBase {
    @SerializedName(value = RuleJsonConstants.FUNCTIONS)
    private List<FunctionIdentifier> functions;

    public List<FunctionIdentifier> getFunctions() {
        return functions;
    }

    public void setFunctions(List<FunctionIdentifier> functions) {
        this.functions = functions;
    }

    public void addFunction(FunctionIdentifier function) {
        if (getFunctions() == null) {
            setFunctions(new ArrayList<FunctionIdentifier>());
        }
        getFunctions().add(function);
    }
}
