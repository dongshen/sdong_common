package sdong.common.bean.rules;

public class TaintPassThrough {
    private String inArgs = "";
    private String outArgs = "";

    public String getInArgs() {
        return inArgs;
    }

    public void setInArgs(String inArgs) {
        this.inArgs = inArgs;
    }

    public String getOutArgs() {
        return outArgs;
    }

    public void setOutArgs(String outArgs) {
        this.outArgs = outArgs;
    }
}
