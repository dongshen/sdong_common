package sdong.common.bean.rules;

public class TaintSourceRule extends DataFlowRule{
    private String outArgs ="";

    public String getOutArgs() {
        return outArgs;
    }

    public void setOutArgs(String outArgs) {
        this.outArgs = outArgs;
    }   
}
