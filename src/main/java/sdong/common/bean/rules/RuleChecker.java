package sdong.common.bean.rules;

/**
 * rule checker
 */
public class RuleChecker extends RuleBase {
    Vulnerability vulnerability = new Vulnerability();

    public Vulnerability getVulnerability() {
        return vulnerability;
    }

    public void setVulnerability(Vulnerability vulnerability) {
        this.vulnerability = vulnerability;
    }
}
