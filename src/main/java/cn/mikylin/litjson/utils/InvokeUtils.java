package cn.mikylin.litjson.utils;

import cn.mikylin.litjson.exception.JSONObjectInvokeException;
import java.beans.*;
import java.lang.reflect.*;
import java.util.*;

/**
 * helper for java invoke api
 * @author mikylin
 */
public final class InvokeUtils {

    private static final String CLASS = "class";

    /**
     * invoke to create the object
     */
    public static Object create(Class clz,Object... params) {
        try {
            return clz.getConstructor().newInstance(params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new JSONObjectInvokeException(e.getMessage());
        }
    }

    /**
     * java bean to map.
     */
    public static Map<String,Object> toMap(Object bean) {

        BeanInfo info = info(bean.getClass());
        PropertyDescriptor[] pds = info.getPropertyDescriptors();

        Map<String,Object> map = new HashMap<>(pds.length);

        for (PropertyDescriptor descriptor : pds) {
            String key = descriptor.getName();
            Method getter = descriptor.getReadMethod();
            if (StringUtils.isNotBlank(key) && !key.equals(CLASS)) {
                try {
                    Object value = getter.invoke(bean);
                    map.put(key,value);
                } catch (Exception e) {
                    throw new JSONObjectInvokeException("Bean method ["
                            + getter.getName() + "] invoke exception");
                }
            }
        }
        return map;
    }

    /**
     * setter methods.
     *
     * @param clz  object's class
     * @return setters
     */
    public static Map<String,Method> sets(Class clz) {
        try {
            BeanInfo info = info(clz);
            PropertyDescriptor[] pds = info.getPropertyDescriptors();
            Map<String,Method> map = new HashMap<>(pds.length);

            for (PropertyDescriptor des : pds) {
                String key = des.getName();
                if (StringUtils.isNotBlank(key) && !key.equals(CLASS)) {
                    Method setterMethod = des.getWriteMethod();
                    map.put(key,setterMethod);
                }
            }
            return map;
        } catch (RuntimeException e) {
            throw new JSONObjectInvokeException(e.getMessage());
        }
    }

    /**
     * set method
     */
    public static void invokeSet(Method setter,Object bean,Object value) {
        try {
            setter.invoke(bean,value);
        } catch (Exception e) {
            throw new JSONObjectInvokeException("Value [" + value + "] set method [" + setter.getName() + "] exception");
        }
    }

    public static Class genericityClass(Class clazz,String fieldName) {
        try {
            Field f = clazz.getDeclaredField(fieldName);
            ParameterizedType parameterizedType = (ParameterizedType) f.getGenericType();
            return (Class) parameterizedType.getActualTypeArguments()[0];
        } catch (NoSuchFieldException e) {
            throw new JSONObjectInvokeException("Object param [" + fieldName + "] genericity get exception");
        }
    }

    public static Class<?> methodParam(Method setter) {
        Class<?>[] pt = setter.getParameterTypes();
        return pt == null || pt.length == 0 ? null : pt[0];
    }


    /**
     * get BeanInfo.
     *
     * @param clz  class
     * @return class's BeanInfo
     */
    private static BeanInfo info(Class clz) {
        try {
            return Introspector.getBeanInfo(clz);
        } catch (IntrospectionException e) {
            throw new RuntimeException("Object [" + clz.getName() + "] BeanInfo create exception");
        }
    }
}
