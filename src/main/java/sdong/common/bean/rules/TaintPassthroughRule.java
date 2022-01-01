package sdong.common.bean.rules;

public class TaintPassthroughRule extends DataFlowRule{
    private TaintPassThrough passthrough = new TaintPassThrough();

    public TaintPassThrough getPassthrough() {
        return passthrough;
    }

    public void setPassthrough(TaintPassThrough passthrough) {
        this.passthrough = passthrough;
    }
}
