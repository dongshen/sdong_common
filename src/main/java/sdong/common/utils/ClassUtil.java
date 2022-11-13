package sdong.common.utils;

import sdong.common.exception.SdongException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClassUtil {
    /**
     * set class value base on class method
     * 
     * @param object     updated rule class
     * @param mothodName updated rule field method name
     * @param setValue   updated field value
     * @throws SdongException module exception
     */
    public static <T> void setRuleValue(T object, String mothodName, String setValue)
            throws SdongException {
        try {
            Method method = object.getClass().getMethod("set" + mothodName, String.class);
            method.invoke(object, setValue);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            throw new SdongException(e.getMessage(), e);
        }
    }

    /**
     * get class value base on class method
     * 
     * @param <T> Rule
     * @param object rule object
     * @param mothodName get rule field method
     * @return rule field value
     * @throws SdongException module exception
     */
    public static <T> String getRuleValue(T object, String mothodName)
            throws SdongException {
        try {
            Method method = object.getClass().getMethod("get" + mothodName);
            return (String) method.invoke(object);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            throw new SdongException(e.getMessage(), e);
        }
    }
}