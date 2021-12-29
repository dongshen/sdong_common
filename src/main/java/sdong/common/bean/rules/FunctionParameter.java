package sdong.common.bean.rules;

public class FunctionParameter {
    private int paramInd = 0;
    private String paramValue = "";
    private String paramPattern = "";

    public int getParamInd() {
        return paramInd;
    }

    public void setParamInd(int paramInd) {
        this.paramInd = paramInd;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getParamPattern() {
        return paramPattern;
    }

    public void setParamPattern(String paramPattern) {
        this.paramPattern = paramPattern;
    }
}
