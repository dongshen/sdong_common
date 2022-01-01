package sdong.common.utils;

import sdong.common.bean.rules.RuleJsonConstants;

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
     * @param sb          StringBuilder
     * @param hasPreValue has previous value
     * @param key         object name
     * @return false
     */
    public static boolean writeObjectStart(StringBuilder sb, boolean hasPreValue, String key)  {
        if (hasPreValue) {
            sb.append(JSON_SPLIT);
        }
        sb.append(JSON_VALUE).append(key).append(JSON_VALUE)
                .append(JSON_VALUE_SPLIT).append(JSON_GROUP_LEFT);
        return false;
    }

    /**
     * write json object value
     * 
     * @param sb          StringBuilder
     * @param hasPreValue has previous value
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
     * write json array start
     * 
     * @param sb  StringBuilder
     * @param key array name
     */
    public static void writeArrayStart(StringBuilder sb, String key) {
        if (key == null || key.isEmpty()) {
            sb.append(JSON_ARRAY_LEFT);
        } else {
            sb.append(JSON_VALUE).append(key).append(JSON_VALUE)
                    .append(JSON_VALUE_SPLIT).append(JSON_ARRAY_LEFT);
        }
    }

    /**
     * write json array end
     * 
     * @param sb    StringBuilder
     * @param isEnd is end
     */
    public static void writeArrayEnd(StringBuilder sb, boolean isEnd)  {
        sb.append(JSON_ARRAY_RIGHT);
        if (!isEnd) {
            sb.append(JSON_SPLIT);
        }
    }

    /**
     * write valueType
     * 
     * @param sb          StringBuilder
     * @param hasPreValue has Previous Value
     * @param key         key
     * @param value       value
     * @param pattern     pattern
     * @return has value return true else false
     */
    public static boolean writeValueType(StringBuilder sb, boolean hasPreValue, String key, String value,
            String pattern)
            {
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
}
