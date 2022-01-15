package sdong.common.bean.rules.conditional;

import com.google.gson.annotations.SerializedName;

import sdong.common.bean.rules.RuleJsonConstants;
import sdong.common.bean.rules.RuleValueType;

public class CondIsType {
    @SerializedName(value = RuleJsonConstants.ARGUMENT)
    String arg;

    @SerializedName(value = RuleJsonConstants.FUNC_NAMESPACE)
    private RuleValueType namespaceName;

    @SerializedName(value = RuleJsonConstants.FUNC_CLASS)
    private RuleValueType className;

    public String getArg() {
        return arg;
    }

    public void setArg(String arg) {
        this.arg = arg;
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
}
