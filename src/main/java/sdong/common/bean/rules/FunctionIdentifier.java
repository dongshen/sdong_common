package sdong.common.bean.rules;

import java.util.ArrayList;
import java.util.List;

public class FunctionIdentifier {
    private String funcionType = "";
    private String NamespaceNameValue = "";
    private String NamespaceNamePattern = "";
    private String ClassNameValue = "";
    private String ClassNamePattern = "";
    private String FunctionNameValue = "";
    private String FunctionNamePattern = "";

    private String modifier = "";
    private String returnType = "";

    private List<FunctionParameter> parameters = new ArrayList<FunctionParameter>();
    private boolean varArgs = false;
    
    private boolean isApplyTo = true;

    private List<FunctionIdentifier> excepts = new ArrayList<FunctionIdentifier>();

    public String getFuncionType() {
        return funcionType;
    }

    public void setFuncionType(String funcionType) {
        this.funcionType = funcionType;
    }

    public String getNamespaceNameValue() {
        return NamespaceNameValue;
    }

    public void setNamespaceNameValue(String namespaceNameValue) {
        NamespaceNameValue = namespaceNameValue;
    }

    public String getNamespaceNamePattern() {
        return NamespaceNamePattern;
    }

    public void setNamespaceNamePattern(String namespaceNamePattern) {
        NamespaceNamePattern = namespaceNamePattern;
    }

    public String getClassNameValue() {
        return ClassNameValue;
    }

    public void setClassNameValue(String classNameValue) {
        ClassNameValue = classNameValue;
    }

    public String getClassNamePattern() {
        return ClassNamePattern;
    }

    public void setClassNamePattern(String classNamePattern) {
        ClassNamePattern = classNamePattern;
    }

    public String getFunctionNameValue() {
        return FunctionNameValue;
    }

    public void setFunctionNameValue(String functionNameValue) {
        FunctionNameValue = functionNameValue;
    }

    public String getFunctionNamePattern() {
        return FunctionNamePattern;
    }

    public void setFunctionNamePattern(String functionNamePattern) {
        FunctionNamePattern = functionNamePattern;
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
