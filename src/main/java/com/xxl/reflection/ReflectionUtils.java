package com.xxl.reflection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author lixin
 * @date 2018-12-24 15:25
 **/
public class ReflectionUtils {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);

    public static <T> T invokMethod(Object cls, String methodName, Object[] fields) {
        //step 1. 获取类
        Class clazz = cls.getClass();

        //step 2. 获取参数
        Class[] fieldTypes = {};
        if (fields != null) {
            fieldTypes = new Class[fields.length];
            for (int i = 0; i < fields.length; i++) {
                if (fields[i] != null) {
                    fieldTypes[i] = fields[i].getClass();
                } else {
                    fieldTypes[i] = null;
                }
            }
        }

        //step 3. 根据类、方法名、参数获取参数
        Method method = getMethod(clazz, methodName, fieldTypes);
        if (method == null) {
            logger.error("未找到目标方法 {}，参数类型：{}", methodName, fieldTypes);
            return null;
        }

        //step 4. 通过反射调用方法
        T result = null;
        try {
            result = (T) method.invoke(cls, fields);
        } catch (IllegalArgumentException e) {
            logger.error("调用{}方法异常，参数无效", method.getName(), e);
            throw new ReflectionException(e.getCause());
        } catch (IllegalAccessException e) {
            logger.error("调用{}方法异常，非法调用", method.getName(), e);
            throw new ReflectionException(e.getCause());
        } catch (InvocationTargetException e) {
            logger.error("调用{}方法异常", method.getName(), e);
            throw new ReflectionException(e.getCause());
        }

        return result;
    }


    /**
     * 通过该方式获得方法有以下局限，需谨慎使用
     * <p>1. 该方法支持参数数组中有null类型参数，但是当有null类型时，该方法如果有重载方法，且参数数量一致，参数排序除了null参数位置为其他
     * 参数位置一致，那么采用此方法不能精确的获得目标方法。如果想避免此类情况发生，则定义重载方法时避免上述定义情景，或者不使用该方法。
     * <p>2. 该方法目前不支持获取实现接口中的方法，当调用方向传入interface的实现类并向获得接口中的default类型方法时，无效。
     *
     * @param clazz
     * @param methodName
     * @param fieldTypes
     * @return
     */
    private static Method getMethod(Class clazz, String methodName, Class[] fieldTypes) {
        Method method = null;

        //step 1. 根据方法名和参数类型找方法
        try {
            method = clazz.getDeclaredMethod(methodName, fieldTypes);
        } catch (NoSuchMethodException e) {
            // 如果参数类型数组中有null类型，会导致这个异常，这里通过遍历的方式再获取一遍
            // 需要注意的是： 这里就是根据方法名一致，参数数组中数量一致来做判断了，因此，类中定义重载函数时要避免参数数量一致且参数排序基本一致，只有一个参数类型不同的情况
            // 如果存在上述类型，则不能通过该反射工具类来进行调用
            Method[] mds = clazz.getDeclaredMethods();
            for (Method md : mds) {
                if (methodName.equals(md.getName()) && md.getParameterTypes().length == fieldTypes.length) {
                    method = md;
                    break;
                }
            }
        } catch (SecurityException e) {
            logger.error("反射获取方法异常");
        }

        //step 2. 若第一种方式找不到，则找父类中的同类型方法。不支持反射调用父类方法时有null类型参数
        if (method == null) {
            // 从父类开始，临界为Object类，每次都取当前类的父类
            for (Class<?> cls = clazz.getSuperclass(); cls != Object.class; cls = cls.getSuperclass()) {
                try {
                    method = clazz.getDeclaredMethod(methodName, fieldTypes);
                    break;
                } catch (NoSuchMethodException e) {
                    continue;
                }
            }
        }

        //step 3. 判断方法是否是可访问的，如果不是，则设置accessAble 为true
        if(method != null){
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
        }

        return method;
    }
}
