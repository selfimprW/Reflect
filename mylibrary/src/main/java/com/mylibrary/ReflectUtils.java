package com.mylibrary;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Description:
 * Created by Jiacheng on 2017/7/15.
 */

public class ReflectUtils {
    public static Class getReflectClass(String className) throws ClassNotFoundException {
        return Class.forName(className);
    }

    public static Object getReflectValue(Class cls, String fieldName) throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        Field field = cls.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(cls.newInstance());
    }


    public static Method getMethod(Class clx, String methodName) {
        if (clx != null) {
            Method[] methods = clx.getDeclaredMethods();
            for (Method m : methods) {
                if (m.getName().equals(methodName)) {
                    return m;
                }
            }
        }
        return null;
    }
}
