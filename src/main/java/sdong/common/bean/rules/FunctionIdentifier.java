package sdong.common.bean.rules;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FunctionIdentifier {    
    private String funcionType = "";

    @SerializedName(value = RuleJsonConstants.FUNC_NAMESPACE)
    private RuleValueType namespaceName = new RuleValueType();

    @SerializedName(value = RuleJsonConstants.FUNC_CLASS)
    private RuleValueType className = new RuleValueType();

    @SerializedName(value = RuleJsonConstants.FUNC_FUNC)
    private RuleValueType functionName = new RuleValueType();

    @SerializedName(value = RuleJsonConstants.FUNC_MODIFIER)
    private String modifier = "";

    @SerializedName(value = RuleJsonConstants.FUNC_RETURN_TYPE)
    private String returnType = "";

    @SerializedName(value = RuleJsonConstants.PARAMETERS)
    private List<FunctionParameter> parameters = new ArrayList<FunctionParameter>();

    @SerializedName(value = RuleJsonConstants.PARM_VAR_ARG)
    private boolean varArgs = false;
    
    private boolean isApplyTo = true;

    private List<FunctionIdentifier> excepts = new ArrayList<FunctionIdentifier>();

    public String getFuncionType() {
        return funcionType;
    }

    public void setFuncionType(String funcionType) {
        this.funcionType = funcionType;
    }

    public RuleValueType getNamespaceName() {
        return namespaceName;
    }

    public void setNamespaceName(RuleValueType namespaceName) {
        this.namespaceName = namespaceName;
    }

    public RuleValueType getClassName() {
        return className;
    }

    public void setClassName(RuleValueType className) {
        this.className = className;
    }

    public RuleValueType getFunctionName() {
        return functionName;
    }

    public void setFunctionName(RuleValueType functionName) {
        this.functionName = functionName;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public List<FunctionParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<FunctionParameter> parameters) {
        this.parameters = parameters;
    }

    public boolean isApplyTo() {
        return isApplyTo;
    }

    public void setApplyTo(boolean isApplyTo) {
        this.isApplyTo = isApplyTo;
    }

    public List<FunctionIdentifier> getExcepts() {
        return excepts;
    }

    public void setExcepts(List<FunctionIdentifier> excepts) {
        this.excepts = excepts;
    }

    public boolean isVarArgs() {
        return varArgs;
    }

    public void setVarArgs(boolean varArgs) {
        this.varArgs = varArgs;
    }
}
