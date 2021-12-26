package sdong.common.bean.rules;

import java.util.ArrayList;
import java.util.List;

public class RulePackage {
    private String packageId = "";
    private String name = "";
    private String version = "";
    private String language = "";
    private String description = "";
    private String locale = "";
    List<Irule> rules = new ArrayList<Irule>();
    List<ReportMessage> messages = new ArrayList<ReportMessage>();

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public List<Irule> getRules() {
        return rules;
    }

    public void setRules(List<Irule> rules) {
        this.rules = rules;
    }

    public List<ReportMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ReportMessage> messages) {
        this.messages = messages;
    }

}
