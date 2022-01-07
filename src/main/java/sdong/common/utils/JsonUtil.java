package sdong.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import sdong.common.bean.rules.RuleJsonConstants;
import sdong.common.exception.SdongException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;

public class JsonUtil {
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
        if (value == null || value.toString().isEmpty()) {
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
        return new Gson().toJson(obj, classType);
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
            T obj = new Gson().fromJson(jsonStr, classType);

            return obj;
        } catch (JsonSyntaxException e) {
            throw new SdongException(e.getMessage(), e);
        }
    }

    /**
     * parse json file to object
     * 
     * @param <T>       object class type
     * @param jsonFile   json file
     * @param classType class type
     * @return object
     * @throws SdongException Module exception
     */
    public static <T> T jsonFileToObject(String jsonFile, Class<T> classType ) throws SdongException{
        try (Reader reader = new BufferedReader(new FileReader(jsonFile))) {
            Gson gson = new GsonBuilder().create();
            T object = gson.fromJson(reader, classType);                        
            return object;
        }catch (IOException e){
            throw new SdongException(e.getMessage(), e);
        }
    }
}
