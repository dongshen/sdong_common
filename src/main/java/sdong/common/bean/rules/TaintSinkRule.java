package sdong.common.bean.rules;

import java.util.ArrayList;
import java.util.List;

public class TaintSinkRule extends DataFlowRule{
    private String inArgs ="";
    private Vulnerability vulnerability = new Vulnerability();

    private List<ReportMessage> messages = new ArrayList<ReportMessage>();

    public String getInArgs() {
        return inArgs;
    }

    public void setInArgs(String inArgs) {
        this.inArgs = inArgs;
    }

    public Vulnerability getVulnerability() {
        return vulnerability;
    }

    public void setVulnerability(Vulnerability vulnerability) {
        this.vulnerability = vulnerability;
    }

    public List<ReportMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ReportMessage> messages) {
        this.messages = messages;
    }
}
