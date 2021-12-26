package sdong.common.bean.rules;

public class Parameter {
    private int paramInd = 0;
    private String paramType = "";
    private String paramValue = "";

    public int getParamInd() {
        return paramInd;
    }

    public void setParamInd(int paramInd) {
        this.paramInd = paramInd;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }
}
