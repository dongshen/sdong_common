package sdong.common.utils;

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
     * @param bw  bufferwriter
     * @param key object name
     * @throws IOException module exception
     */
    public static void writeObjectStart(BufferedWriter bw, String key) throws IOException {
        StringBuffer sb = new StringBuffer();
        sb.append(JSON_VALUE).append(key).append(JSON_VALUE)
                .append(JSON_VALUE_SPLIT).append(JSON_GROUP_LEFT);
        bw.write(sb.toString());
    }

    /**
     * write object end
     * 
     * @param bw    bufferwriter
     * @param isEnd is the last object
     * @throws IOException module exception
     */
    public static void writeObjectEnd(BufferedWriter bw, boolean isEnd) throws IOException {
        StringBuffer sb = new StringBuffer();
        sb.append(JsonUtil.JSON_GROUP_RIGHT);
        if (!isEnd) {
            sb.append(JsonUtil.JSON_SPLIT);
        }
        bw.write(sb.toString());
    }

    /**
     * write json object value
     * 
     * @param bw    bufferwriter
     * @param key   object name
     * @param value object value
     * @param isEnd is the last object
     * @throws IOException module exception
     */
    public static void writeObjectValue(BufferedWriter bw, String key, Object value, boolean isEnd) throws IOException {
        StringBuffer sb = new StringBuffer();
        sb.append(JSON_VALUE).append(key).append(JSON_VALUE)
                .append(JSON_VALUE_SPLIT);

        if (value instanceof String) {
            sb.append(JSON_VALUE).append(value.toString()).append(JSON_VALUE);
        } else if (value instanceof Integer) {
            sb.append(value.toString());
        } else if (value instanceof Boolean) {
            sb.append(value.toString());
        }
        if (!isEnd) {
            sb.append(JSON_SPLIT);
        }
        bw.write(sb.toString());
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
     * @param bw  bufferwriter
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
}
