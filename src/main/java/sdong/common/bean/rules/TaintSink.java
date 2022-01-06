package sdong.common.bean.rules;

import com.google.gson.annotations.SerializedName;

import sdong.common.bean.rules.conditional.Conditional;

public class TaintSink {
    @SerializedName(value = RuleJsonConstants.IN_ARGS)
    private String inArgs = "";
    
    private boolean isMain = false;

    @SerializedName(value = RuleJsonConstants.CONDITIONAL)
    private Conditional conditional = new Conditional();

    private String conditionalStr = "";
    
    @SerializedName(value = RuleJsonConstants.RULE_MSG)
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

    public String getConditionalStr() {
        return conditionalStr;
    }

    public void setConditionalStr(String conditional) {
        this.conditionalStr = conditional;
    }

    public ReportMessage getReportMsg() {
        return reportMsg;
    }

    public void setReportMsg(ReportMessage reportMsg) {
        this.reportMsg = reportMsg;
    }

    public Conditional getConditional() {
        return conditional;
    }

    public void setConditional(Conditional conditional) {
        this.conditional = conditional;
    }
}
