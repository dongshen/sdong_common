package sdong.common.utils;

import sdong.common.bean.rules.RuleJsonConstants;

import java.io.BufferedWriter;
import java.io.IOException;

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
     * write json object start
     * 
     * @param bw bufferwriter
     * @param hasPreValue has previous value
     * @param key object name
     * @return false
     * @throws IOException module exception
     */
    public static boolean writeObjectStart(BufferedWriter bw,boolean hasPreValue, String key) throws IOException {
        StringBuffer sb = new StringBuffer();
        if(hasPreValue){
            sb.append(JSON_SPLIT);
        }
        sb.append(JSON_VALUE).append(key).append(JSON_VALUE)
                .append(JSON_VALUE_SPLIT).append(JSON_GROUP_LEFT);
        bw.write(sb.toString());
        return false;
    }

    /**
     * write json object value
     * 
     * @param bw    bufferwriter
     * @param hasPreValue has previous value
     * @param key   object name
     * @param value object value
     * @return has value return true else false
     * @throws IOException module exception
     */
    public static boolean writeObjectValue(BufferedWriter bw, boolean hasPreValue, String key, Object value) throws IOException {
        if(value == null || value.toString().isEmpty()){
            return hasPreValue;
        }
        
        StringBuffer sb = new StringBuffer();
        if(hasPreValue){
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
        bw.write(sb.toString());
        return true;
    }

    /**
     * write json array start
     * 
     * @param bw  bufferwriter
     * @param key array name
     * @throws IOException module exception
     */
    public static void writeArrayStart(BufferedWriter bw, String key) throws IOException {
        StringBuffer sb = new StringBuffer();
        if (key == null || key.isEmpty()) {
            sb.append(JSON_ARRAY_LEFT);
        } else {
            sb.append(JSON_VALUE).append(key).append(JSON_VALUE)
                    .append(JSON_VALUE_SPLIT).append(JSON_ARRAY_LEFT);
        }
        bw.write(sb.toString());
    }

    /**
     * write json array end
     * 
     * @param bw    bufferwriter
     * @param isEnd is end
     * @throws IOException module exception
     */
    public static void writeArrayEnd(BufferedWriter bw, boolean isEnd) throws IOException {
        StringBuffer sb = new StringBuffer();
        sb.append(JSON_ARRAY_RIGHT);
        if (!isEnd) {
            sb.append(JSON_SPLIT);
        }
        bw.write(sb.toString());
    }

    /**
     * write valueType 
     * 
     * @param bw bufferwriter
     * @param hasPreValue has Previous Value
     * @param key key
     * @param value value
     * @param pattern pattern
     * @return has value return true else false
     * @throws IOException module exception
     */
    public static boolean writeValueType(BufferedWriter bw,boolean hasPreValue, String key, String value, String pattern)
            throws IOException {
        if ((value == null || value.isEmpty()) && (pattern == null || pattern.isEmpty())) {
            return hasPreValue;
        }
        StringBuffer sb = new StringBuffer();
        if(hasPreValue){
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
        bw.write(sb.toString());
        return true;
    }
}
