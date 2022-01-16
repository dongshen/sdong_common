package sdong.common.bean.rules.json.validator;

import com.google.gson.annotations.SerializedName;

import sdong.common.bean.rules.RuleJsonConstants;
import sdong.common.utils.JsonUtil;

import java.util.List;

public class JsonValidatErrors {
    private String schemaLocation;
    private String pointerToViolation;
    @SerializedName(value = RuleJsonConstants.VALIDAT_ERRORS)
    private List<JsonValidatErrors> validatErrors;
    private String keyword;
    private String message;

    public String getSchemaLocation() {
        return schemaLocation;
    }

    public void setSchemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
    }

    public String getPointerToViolation() {
        return pointerToViolation;
    }

    public void setPointerToViolation(String pointerToViolation) {
        this.pointerToViolation = pointerToViolation;
    }

    public List<JsonValidatErrors> getValidatErrors() {
        return validatErrors;
    }

    public void setValidatErrors(List<JsonValidatErrors> validatErrors) {
        this.validatErrors = validatErrors;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString(){
        return JsonUtil.objectToJsonString(this, JsonValidatErrors.class);
    }

}
