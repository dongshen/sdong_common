package sdong.common.bean.rules;

/**
 * taint source
 */
public class TaintSource {
    private String outArgs = "";
    private String taintFlags = "";
    private ReportMessage reportMsg = new ReportMessage();

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
