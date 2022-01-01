package sdong.common.bean.rules;

import java.util.ArrayList;
import java.util.List;

/**
 * Taint sink rule
 */
public class TaintSinkRule extends DataFlowRule {
    private List<TaintSink> taintSinks = new ArrayList<TaintSink>();
    private List<TaintSource> taintSources = new ArrayList<TaintSource>();
    private Vulnerability vulnerability = new Vulnerability();

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

    public List<TaintSource> getTaintSources() {
        return taintSources;
    }

    public void setTaintSources(List<TaintSource> taintSources) {
        this.taintSources = taintSources;
    }
}
