package sdong.common.bean.rules;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FunctionIdentifier {
    private transient String funcionType;

    @SerializedName(value = RuleJsonConstants.FUNC_NAMESPACE)
    private RuleValueType namespaceName;

    @SerializedName(value = RuleJsonConstants.FUNC_CLASS)
    private RuleValueType className;

    @SerializedName(value = RuleJsonConstants.FUNC_FUNC)
    private RuleValueType functionName;

    @SerializedName(value = RuleJsonConstants.FUNC_MODIFIER)
    private String modifier;

    @SerializedName(value = RuleJsonConstants.FUNC_RETURN_TYPE)
    private String returnType;

    @SerializedName(value = RuleJsonConstants.PARAMETERS)
    private List<FunctionParameter> parameters;

    @SerializedName(value = RuleJsonConstants.PARM_VAR_ARG)
    private boolean varArgs = false;

    @SerializedName(value = RuleJsonConstants.FUNC_APPLYTO)
    private boolean isApplyTo = true;

    @SerializedName(value = RuleJsonConstants.FUNC_EXCLUDE)
    private List<FunctionIdentifier> exclude;

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

    public void addParameter(FunctionParameter parameter) {
        if (parameters == null) {
            parameters = new ArrayList<FunctionParameter>();
        }
        parameters.add(parameter);
    }

    public boolean isApplyTo() {
        return isApplyTo;
    }

    public void setApplyTo(boolean isApplyTo) {
        this.isApplyTo = isApplyTo;
    }

    public List<FunctionIdentifier> getExclude() {
        return exclude;
    }

    public void setExclude(List<FunctionIdentifier> exclude) {
        this.exclude = exclude;
    }

    public void addExclude(FunctionIdentifier function) {
        if (exclude == null) {
            exclude = new ArrayList<FunctionIdentifier>();
        }
        exclude.add(function);
    }

    public boolean isVarArgs() {
        return varArgs;
    }

    public void setVarArgs(boolean varArgs) {
        this.varArgs = varArgs;
    }
}
