package sdong.common.bean.rules;

import com.google.gson.annotations.SerializedName;

/**
 * entry point
 */
public class EntryPoint {
    @SerializedName(value = RuleJsonConstants.IN_ARGS)
    private String inArgs;

    @SerializedName(value = RuleJsonConstants.TAINT_FLAGS)
    private String taintFlags;

    @SerializedName(value = RuleJsonConstants.RULE_MSG)
    private ReportMessage reportMsg;

    public String getInArgs() {
        return inArgs;
    }

    public void setInArgs(String inArgs) {
        this.inArgs = inArgs;
    }

    public String getTaintFlags() {
        return taintFlags;
    }

    public void setTaintFlags(String taintFlags) {
        this.taintFlags = taintFlags;
    }

    public ReportMessage getReportMsg() {
        return reportMsg;
    }

    public void setReportMsg(ReportMessage reportMsg) {
        this.reportMsg = reportMsg;
    }
}
