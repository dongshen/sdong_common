package sdong.common.bean.rules;

import com.google.gson.annotations.SerializedName;

import sdong.common.bean.rules.conditional.ConditionalNode;

public class TaintSink {
    @SerializedName(value = RuleJsonConstants.IN_ARGS)
    private String inArgs;
    
    private boolean isMain = false;

    @SerializedName(value = RuleJsonConstants.CONDITIONAL)
    private ConditionalNode conditional;

    private String conditionalStr;
    
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
        if(conditionalStr == null || conditionalStr.isEmpty()){
            
        }
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

    public ConditionalNode getConditional() {
        return conditional;
    }

    public void setConditional(ConditionalNode conditional) {
        this.conditional = conditional;
    }
}
