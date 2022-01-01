package sdong.common.bean.rules;

public class RuleJsonConstants {
    public static final String RULE_PACKAGE = "RulePackage";
    public static final String RULES = "Rules";
    public static final String RULE_MESSAGE = "RuleMessages";

    /**
     * pakage info
     */
    public static final String RULE_PACKAGE_NAME = "Name";
    public static final String RULE_PACKAGE_DES = "Description";
    public static final String RULE_PACKAGE_VER = "Version";
    public static final String RULE_PACKAGE_LANGUAGE = "Language";
    public static final String RULE_PACKAGE_LOCALE = "Locale";

    /**
     * Rule info
     */
    public static final String RULE_TAINT_SOURCE = "TaintSourceRule";
    public static final String RULE_TAINT_PASSTHROUGH = "TaintPassthroughRule";
    public static final String RULE_TAINT_SINK = "TaintSinkRule";
    public static final String RULE_TAINT_CLEAN = "TaintCleanseRule";

    /**
     * Rule
     */
    public static final String RULE = "Rule";
    public static final String RULE_ID = "RuleId";
    public static final String RULE_NOTE = "Note";

    /**
     * RefInfos
     */
    public static final String REF_INFOS = "RefInfos";
    public static final String REF_INFO = "RefInfo";
    public static final String REF_INFO_KEY = "Key";
    public static final String REF_INFO_VALUE = "Value";

    /**
     * Function
     */
    public static final String FUNCTION = "Function";
    public static final String FUNC_NAMESPACE = "Namespace";
    public static final String FUNC_CLASS = "Class";
    public static final String FUNC_FUNC = "Function";
    public static final String FUNC_RETURN_TYPE = "ReturnType";
    public static final String FUNC_MODIFIER = "Modifier";
    public static final String FUNC_EXCLUDE = "Exclude";
    public static final String FUNC_APPLYTO = "ApplyTo";

    /**
     * Parameters
     */
    public static final String PARAMETERS = "Parameters";
    public static final String PARM_INDEX = "ParamInd";
    public static final String PARM_TYPES = "ParamType";
    public static final String PARM_VAR_ARG = "varArg";

    /**
     * Source
     */
    public static final String SOURCE = "Source";

    /**
     * Passthrough
     */
    public static final String PASSTHROUGH = "Passthrough";

    /**
     * Sink
     */
    public static final String SINK = "Sink";

    /**
     * Args
     */
    public static final String OUT_ARGS = "OutArgs";
    public static final String IN_ARGS = "InArgs";

    /**
     * taint flag
     */
    public static final String TAINT_FLAGS = "TaintFlags";
    public static final String TAINT_FLAG = "TaintFlag";

    /**
     * ValueType
     */
    public static final String VALUE_TYPE_VALUE = "Value";
    public static final String VALUE_TYPE_PATTERN = "Pattern";

    /**
     * Conditional
     */
    public static final String CONDITIONAL = "Conditioanl";

    /**
     * Vulnerability
     */
    public static final String VULNERABILITY = "Vulnerability";
    public static final String VULN_CATEGORY = "Category";
    public static final String VULN_SUB_CATEGORY = "Subcategory";
    public static final String VULN_ISSUE_TYPE = "IssueType";
    public static final String VULN_CWE = "CWE";
    public static final String VULN_SEVERITY = "Severity";

    /**
     * ReportMsg
     */
    public static final String MSG_ID = "MessageId";
    public static final String MSG_MSG = "Message";
}