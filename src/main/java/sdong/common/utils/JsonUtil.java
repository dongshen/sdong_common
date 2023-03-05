package sdong.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import sdong.common.bean.rules.RuleConstants;
import sdong.common.bean.rules.RuleJsonConstants;
import sdong.common.bean.rules.json.validator.JsonValidatErrors;
import sdong.common.exception.SdongException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Optional;

public class JsonUtil {
    private static final Logger LOG = LogManager.getLogger(JsonUtil.class);

    /**
     * JSON_GROUP_LEFT
     */
    public static final String JSON_GROUP_LEFT = "{";

    /**
     * JSON_GROUP_RIGHT
     */
    public static final String JSON_GROUP_RIGHT = "}";

    /**
     * JSON_ARRAY_LEFT
     */
    public static final String JSON_ARRAY_LEFT = "[";

    /**
     * JSON_ARRAY_RIGHT
     */
    public static final String JSON_ARRAY_RIGHT = "]";

    /**
     * JSON_VALUE_SPLIT
     */
    public static final String JSON_VALUE_SPLIT = ":";

    /**
     * JSON_SPLIT
     */
    public static final String JSON_SPLIT = ",";

    /**
     * JSON_VALUE
     */
    public static final String JSON_VALUE = "\"";

    /**
     * taint schema
     */
    private static final Schema TAINT_SCHEMA = null;

    /**
     * write json object start: "key":{ or {
     * 
     * @param sb          StringBuilder
     * @param hasPreValue has previous value, then print ","
     * @param key         object name
     * @return false
     */
    public static boolean writeObjectStart(StringBuilder sb, boolean hasPreValue, String key) {
        if (hasPreValue) {
            sb.append(JSON_SPLIT);
        }
        if (key == null || key.isEmpty()) {
            sb.append(JSON_GROUP_LEFT);
        } else {
            sb.append(JSON_VALUE).append(key).append(JSON_VALUE)
                    .append(JSON_VALUE_SPLIT).append(JSON_GROUP_LEFT);
        }
        return false;
    }

    /**
     * write json object end: }
     * 
     * @param sb StringBuilder
     * @return true
     */
    public static boolean writeObjectEnd(StringBuilder sb) {
        sb.append(JSON_GROUP_RIGHT);
        return true;
    }

    /**
     * write json object value: "object":"value"
     * 
     * @param sb          StringBuilder
     * @param hasPreValue has previous value, then print ","
     * @param key         object name
     * @param value       object value
     * @return has value return true else false
     */
    public static boolean writeObjectValue(StringBuilder sb, boolean hasPreValue, String key, Object value) {
        if (value == null) {
            return hasPreValue;
        }

        if (hasPreValue) {
            sb.append(JSON_SPLIT);
        }
        sb.append(JSON_VALUE).append(key).append(JSON_VALUE)
                .append(JSON_VALUE_SPLIT);

        if (value instanceof String) {
            sb.append(JSON_VALUE).append(value.toString()).append(JSON_VALUE);
        } else if (value instanceof Integer) {
            sb.append(value.toString());
        } else if (value instanceof Boolean) {
            sb.append(value.toString());
        }
        return true;
    }

    /**
     * write json array start: [ or "key":[
     * 
     * @param sb          StringBuilder
     * @param hasPreValue has previous value, then print ","
     * @param key         array name
     * @return has value return true else false
     */
    public static boolean writeArrayStart(StringBuilder sb, boolean hasPreValue, String key) {
        if (hasPreValue) {
            sb.append(JSON_SPLIT);
        }

        if (key == null || key.isEmpty()) {
            sb.append(JSON_ARRAY_LEFT);
        } else {
            sb.append(JSON_VALUE).append(key).append(JSON_VALUE)
                    .append(JSON_VALUE_SPLIT).append(JSON_ARRAY_LEFT);
        }
        return false;
    }

    /**
     * write json array end: ]
     * 
     * @param sb StringBuilder
     * @return has value return true else false
     */
    public static boolean writeArrayEnd(StringBuilder sb) {
        sb.append(JSON_ARRAY_RIGHT);
        return true;
    }

    /**
     * write valueType: "Value":"xxx" or "Pattern":"yyy"
     * 
     * @param sb          StringBuilder
     * @param hasPreValue has previous value, then print ","
     * @param key         key
     * @param value       value
     * @param pattern     pattern
     * @return has value return true else false
     */
    public static boolean writeValueType(StringBuilder sb, boolean hasPreValue, String key, String value,
            String pattern) {
        if ((value == null || value.isEmpty()) && (pattern == null || pattern.isEmpty())) {
            return hasPreValue;
        }
        if (hasPreValue) {
            sb.append(JSON_SPLIT);
        }
        sb.append(JSON_VALUE).append(key).append(JSON_VALUE)
                .append(JSON_VALUE_SPLIT).append(JSON_GROUP_LEFT);
        if (value != null && !value.isEmpty()) {
            sb.append(JSON_VALUE).append(RuleJsonConstants.VALUE_TYPE_VALUE).append(JSON_VALUE).append(JSON_VALUE_SPLIT)
                    .append(JSON_VALUE).append(value).append(JSON_VALUE);
        } else {
            sb.append(JSON_VALUE).append(RuleJsonConstants.VALUE_TYPE_PATTERN).append(JSON_VALUE)
                    .append(JSON_VALUE_SPLIT)
                    .append(JSON_VALUE).append(pattern).append(JSON_VALUE);
        }
        sb.append(JSON_GROUP_RIGHT);
        return true;
    }

    /**
     * convert object to json string
     * 
     * @param obj       object
     * @param classType object class
     * @return json string
     */
    public static String objectToJsonString(Object obj, Type classType) {
        return new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT).create().toJson(obj, classType);
    }

    /**
     * convert json string to object
     * 
     * @param <T>       object class type
     * @param jsonStr   json string
     * @param classType class type
     * @return object
     * @throws SdongException Module exception
     */
    public static <T> T jsonStringToObject(String jsonStr, Class<T> classType) throws SdongException {
        try {
            return new Gson().fromJson(jsonStr, classType);
        } catch (JsonSyntaxException e) {
            throw new SdongException(e.getMessage(), e);
        }
    }

    /**
     * parse json file to object
     * 
     * @param <T>       object class type
     * @param jsonFile  json file
     * @param classType class type
     * @return object
     * @throws SdongException Module exception
     */
    public static <T> T jsonFileToObject(String jsonFile, Class<T> classType) throws SdongException {
        try (Reader reader = new BufferedReader(new FileReader(jsonFile))) {
            Gson gson = new GsonBuilder().create();
            return gson.fromJson(reader, classType);
        } catch (IOException e) {
            throw new SdongException(e.getMessage(), e);
        }
    }

    /**
     * parse json file to object
     * 
     * @param <T>       object class type
     * @param jsonFile  json file
     * @param classType class type
     * @return object
     * @throws SdongException Module exception
     */
    public static void writeJsonObjectToFile(String jsonFile, Object object, Type classType) throws SdongException {
        String jsonString = objectToJsonString(object, classType);
        FileUtil.createFileWithFolder(jsonFile);
        try (FileWriter fileWriter = new FileWriter(jsonFile)) {
            fileWriter.write(jsonString);
        } catch (IOException e) {
            throw new SdongException(e.getMessage(), e);
        }
    }

    /**
     * get data flow rule schema
     * 
     * @return schema data flow rule schema
     * @throws SdongException module exception
     */
    public static Schema getDataFlowRulesSchema() throws SdongException {
        if (TAINT_SCHEMA != null) {
            return TAINT_SCHEMA;
        }

        try (InputStream inputStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(RuleConstants.TAINT_RULES_SCHEMA)) {
            JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
            return SchemaLoader.load(rawSchema);
        } catch (IOException | JSONException e) {
            throw new SdongException("Taint schema setting get error:" + e.getMessage(), e);
        }
    }

    public static Optional<JsonValidatErrors> validatTaintRule(String ruleFile) throws SdongException {
        try (InputStream inputStream = new FileInputStream(ruleFile)) {
            Schema schema = getDataFlowRulesSchema();
            JSONObject jsonSubject = new JSONObject(new JSONTokener(inputStream));
            schema.validate(jsonSubject);
            return Optional.empty();
        } catch (ValidationException ex) {
            StringBuffer result = new StringBuffer("Validation against Json schema failed: \n");
            ex.getAllMessages().stream().peek(e -> result.append("\n")).forEach(result::append);
            LOG.error("Get validate erorrs:{}", ex.getAllMessages().size());
            LOG.error("Validate erorrs:{}", result);
            return Optional.of(JsonUtil.jsonStringToObject(ex.toJSON().toString(), JsonValidatErrors.class));
        } catch (JSONException | IOException e) {
            throw new SdongException("Varify taint rule " + ruleFile + "fail on:" + e.getMessage(), e);
        }
    }
}
