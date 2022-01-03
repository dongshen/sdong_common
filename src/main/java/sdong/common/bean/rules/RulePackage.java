package sdong.common.bean.rules;

import com.google.gson.annotations.SerializedName;

public class RulePackage {
    private String packageId = "";

    @SerializedName(value = RuleJsonConstants.RULE_PACKAGE_NAME)
    private String name = "";

    @SerializedName(value = RuleJsonConstants.RULE_PACKAGE_VER)
    private String version = "";
    
    @SerializedName(value = RuleJsonConstants.RULE_PACKAGE_LANGUAGE)
    private String language = "";

    @SerializedName(value = RuleJsonConstants.RULE_PACKAGE_DES)
    private String description = "";

    @SerializedName(value = RuleJsonConstants.RULE_PACKAGE_LOCALE)
    private String locale = "";
   

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
}
