package sdong.common.bean.rules;

public class TaintCleanseRule extends DataFlowRule{
    private String outArgs ="";

    public String getOutArgs() {
        return outArgs;
    }

    public void setOutArgs(String outArgs) {
        this.outArgs = outArgs;
    }
}
