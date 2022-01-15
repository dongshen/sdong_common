package sdong.common.bean.rules;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FunctionParameters {
    @SerializedName(value = RuleJsonConstants.PARAMETER_TYPES)
    private List<FunctionParameterType> parameterTypes;
    
    @SerializedName(value = RuleJsonConstants.PARM_VAR_ARG)
    private boolean varArgs = false;

    public List<FunctionParameterType> getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(List<FunctionParameterType> parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public void addParameterType(FunctionParameterType parameterType) {
        if (parameterTypes == null) {
            parameterTypes = new ArrayList<FunctionParameterType>();
        }
        parameterTypes.add(parameterType);
    }

    public boolean isVarArgs() {
        return varArgs;
    }

    public void setVarArgs(boolean varArgs) {
        this.varArgs = varArgs;
    }   
}
