package sdong.common.bean.rules;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Taint sink rule
 */
public class TaintSinkRule extends DataFlowRule {
    @SerializedName(value = RuleJsonConstants.SINKS)
    private List<TaintSink> taintSinks;

    @SerializedName(value = RuleJsonConstants.SOURCE)
    private List<TaintSource> taintSources;
    
    @SerializedName(value = RuleJsonConstants.VULNERABILITY)
    private Vulnerability vulnerability;

    public Vulnerability getVulnerability() {
        return vulnerability;
    }

    public void setVulnerability(Vulnerability vulnerability) {
        this.vulnerability = vulnerability;
    }

    public List<TaintSink> getTaintSinks() {
        return taintSinks;
    }

    public void setTaintSinks(List<TaintSink> taintSinks) {
        this.taintSinks = taintSinks;
    }

    public void addTaintSink(TaintSink taintSink) {
        if (getTaintSinks() == null) {
            setTaintSinks(new ArrayList<TaintSink>());
        }
        getTaintSinks().add(taintSink);
    }

    public List<TaintSource> getTaintSources() {
        return taintSources;
    }

    public void setTaintSources(List<TaintSource> taintSources) {
        this.taintSources = taintSources;
    }

    public void addTaintSource(TaintSource taintSource) {
        if (getTaintSources() == null) {
            setTaintSources(new ArrayList<TaintSource>());
        }
        getTaintSources().add(taintSource);
    }
}
