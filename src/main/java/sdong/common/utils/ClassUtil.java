package sdong.common.utils;

import sdong.common.exception.SdongException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClassUtil {
    /**
     * set object value
     * 
     * @param <T>        object type
     * @param object     object
     * @param methodName method
     * @param setValue   value
     * @throws SdongException module exception
     */
    public static <T> void setClassValueWithOutTrim(T object, String methodName, String setValue)
            throws SdongException {
        try {
            String value = StringUtil.removeStarAndEndBlankLine(setValue);
            Method method = object.getClass().getMethod(methodName, String.class);
            method.invoke(object, value);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            throw new SdongException(e);
        }
    }

    /**
     * set object value
     * 
     * @param <T>        object type
     * @param object     object
     * @param methodName method
     * @param setValue   value
     * @throws SdongException module exception
     */
    public static <T> void setObjectValue(T object, String methodName, String setValue) throws SdongException {
        try {
            Method method = object.getClass().getMethod(methodName, String.class);
            method.invoke(object, setValue);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            throw new SdongException(e);
        }
    }
}
