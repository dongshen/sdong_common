package sdong.common.bean.rules;

public class TaintSink {
    private String inArgs = "";
    private boolean isMain = false;

    private String conditional = "";
    
    private ReportMessage reportMsg = new ReportMessage();

    public String getInArgs() {
        return inArgs;
    }

    public void setInArgs(String inArgs) {
        this.inArgs = inArgs;
    }

    public boolean isMain() {
        return isMain;
    }

    public void setMain(boolean isMain) {
        this.isMain = isMain;
    }

    public String getConditional() {
        return conditional;
    }

    public void setConditional(String conditional) {
        this.conditional = conditional;
    }

    public ReportMessage getReportMsg() {
        return reportMsg;
    }

    public void setReportMsg(ReportMessage reportMsg) {
        this.reportMsg = reportMsg;
    }
}
