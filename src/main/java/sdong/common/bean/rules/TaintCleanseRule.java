package sdong.common.bean.rules;

public class TaintCleanseRule extends DataFlowRule{
    private TaintCleanse cleanse = new TaintCleanse();

    public TaintCleanse getCleanse() {
        return cleanse;
    }

    public void setCleanse(TaintCleanse cleanse) {
        this.cleanse = cleanse;
    }   
}
