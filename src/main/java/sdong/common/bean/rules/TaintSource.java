package sdong.common.bean.rules;

import com.google.gson.annotations.SerializedName;

/**
 * taint source
 */
public class TaintSource {
    @SerializedName(value = RuleJsonConstants.OUT_ARGS)
    private String outArgs;

    @SerializedName(value = RuleJsonConstants.TAINT_FLAGS)
    private String taintFlags;

    @SerializedName(value = RuleJsonConstants.RULE_MSG)
    private ReportMessage reportMsg;

    public String getOutArgs() {
        return outArgs;
    }

    public void setOutArgs(String outArgs) {
        this.outArgs = outArgs;
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
