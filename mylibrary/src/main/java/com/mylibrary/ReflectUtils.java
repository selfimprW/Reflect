package com.mylibrary;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Description:
 * Created by Jiacheng on 2017/7/15.
 */
public class ReflectUtils {
    /**
     * 反射机制获取类有三种方法：
     * 第一种方式：Class cls1 = Class.forName("ConfigUtils");
     * 第二种方式：Class cls2 = ConfigUtils.class;
     * 第三种方式：
     * java语言中任何一个java对象都有getClass 方法
     * ConfigUtils config= new ConfigUtils();
     * Class cls3 = config.getClass(); //c3是运行时类 (e的运行时类是Employee)
     *
     * @param className 类名（包名+类名）
     * @return Class
     * @throws ClassNotFoundException
     */
    public static Class getReflectClass(String className) throws ClassNotFoundException {
        return Class.forName(className);
    }

    /**
     * 通过反射得到对应的属性值
     *
     * @param cls       类
     * @param fieldName 变量名
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
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
