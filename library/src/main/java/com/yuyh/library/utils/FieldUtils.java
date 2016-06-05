package com.yuyh.library.utils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author yuyh.
 * @date 16/4/9.
 */
public class FieldUtils {

    public static final Class<?>[] EMPTY_PARAM_TYPES = new Class<?>[0];
    public static final Object[] EMPTY_PARAMS = new Object[0];

    /**
     * 判断是否序列化
     *
     * @param f
     * @return
     */
    public static boolean isSerializable(Field f) {
        Class<?>[] cls = f.getType().getInterfaces();
        for (Class<?> c : cls) {
            if (Serializable.class == c) {
                return true;
            }
        }
        return false;
    }

    /**
     * 设置域的值
     *
     * @param f
     * @param obj
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public static Object set(Field f, Object obj, Object value) throws IllegalArgumentException, IllegalAccessException {
        f.setAccessible(true);
        f.set(obj, value);
        return f.get(obj);
    }

    /**
     * 获取域的值
     *
     * @param f
     * @param obj
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public static Object get(Field f, Object obj) throws IllegalArgumentException, IllegalAccessException {
        f.setAccessible(true);
        return f.get(obj);
    }

    public static boolean isLong(Field field) {
        return field.getType() == long.class || field.getType() == Long.class;
    }

    public static boolean isInteger(Field field) {
        return field.getType() == int.class || field.getType() != Integer.class;
    }

    /**
     * 获取域的泛型类型，如果不带泛型返回null
     *
     * @param f
     * @return
     */
    public static Class<?> getGenericType(Field f) {
        Type type = f.getGenericType();
        if (type instanceof ParameterizedType) {
            type = ((ParameterizedType) type).getActualTypeArguments()[0];
            if (type instanceof Class<?>) return (Class<?>) type;
        } else if (type instanceof Class<?>) return (Class<?>) type;
        return null;
    }

    /**
     * 获取数组的类型
     *
     * @param f
     * @return
     */
    public static Class<?> getComponentType(Field f) {
        return f.getType().getComponentType();
    }

    /**
     * 获取给定的类所有的父类
     *
     * @param sourceClass       给定的类
     * @param isAddCurrentClass 是否将当年类放在最终返回的父类列表的首位
     * @return 给定的类所有的父类
     */
    public static List<Class<?>> getSuperClasss(Class<?> sourceClass, boolean isAddCurrentClass) {
        List<Class<?>> classList = new ArrayList<Class<?>>();
        Class<?> classs;
        if (isAddCurrentClass) {
            classs = sourceClass;
        } else {
            classs = sourceClass.getSuperclass();
        }
        while (classs != null) {
            classList.add(classs);
            classs = classs.getSuperclass();
        }
        return classList;
    }

    /**
     * 获取给定类的所有字段
     *
     * @param sourceClass         给定的类
     * @param isGetDeclaredField  是否需要获取Declared字段
     * @param isGetParentField    是否需要把其父类中的字段也取出
     * @param isGetAllParentField 是否需要把所有父类中的字段全取出
     * @param isDESCGet           在最终获取的列表里，父类的字段是否需要排在子类的前面。只有需要把其父类中的字段也取出时此参数才有效
     * @return 给定类的所有字段
     */
    public static List<Field> getFields(Class<?> sourceClass, boolean isGetDeclaredField, boolean isGetParentField, boolean isGetAllParentField, boolean isDESCGet) {
        List<Field> fieldList = new ArrayList<Field>();
        //如果需要从父类中获取
        if (isGetParentField) {
            //获取当前类的所有父类
            List<Class<?>> classList = null;
            if (isGetAllParentField) {
                classList = getSuperClasss(sourceClass, true);
            } else {
                classList = new ArrayList<Class<?>>(2);
                classList.add(sourceClass);
                Class<?> superClass = sourceClass.getSuperclass();
                if (superClass != null) {
                    classList.add(superClass);
                }
            }

            //如果是降序获取
            if (isDESCGet) {
                for (int w = classList.size() - 1; w > -1; w--) {
                    for (Field field : isGetDeclaredField ? classList.get(w).getDeclaredFields() : classList.get(w).getFields()) {
                        fieldList.add(field);
                    }
                }
            } else {
                for (int w = 0; w < classList.size(); w++) {
                    for (Field field : isGetDeclaredField ? classList.get(w).getDeclaredFields() : classList.get(w).getFields()) {
                        fieldList.add(field);
                    }
                }
            }
        } else {
            for (Field field : isGetDeclaredField ? sourceClass.getDeclaredFields() : sourceClass.getFields()) {
                fieldList.add(field);
            }
        }
        return fieldList;
    }

    /**
     * 从指定的类中获取指定的字段
     *
     * @param sourceClass         指定的类
     * @param fieldName           要获取的字段的名字
     * @param isFindDeclaredField 是否查找Declared字段
     * @param isUpwardFind        是否向上去其父类中寻找
     * @return
     */
    public static Field getField(Class<?> sourceClass, String fieldName, boolean isFindDeclaredField, boolean isUpwardFind) {
        Field field = null;
        try {
            field = isFindDeclaredField ? sourceClass.getDeclaredField(fieldName) : sourceClass.getField(fieldName);
        } catch (NoSuchFieldException e1) {
            if (isUpwardFind) {
                Class<?> classs = sourceClass.getSuperclass();
                while (field == null && classs != null) {
                    try {
                        field = isFindDeclaredField ? classs.getDeclaredField(fieldName) : classs.getField(fieldName);
                    } catch (NoSuchFieldException e11) {
                        classs = classs.getSuperclass();
                    }
                }
            }
        }
        return field;
    }

    /**
     * 从指定的类中获取指定的字段，默认获取Declared类型的字段、向上查找
     *
     * @param sourceClass 指定的类
     * @param fieldName   要获取的字段的名字
     * @return
     */
    public static Field getField(Class<?> sourceClass, String fieldName) {
        return getField(sourceClass, fieldName, true, true);
    }

    /**
     * 从指定的类中获取指定的方法
     * @param sourceClass 给定的类
     * @param isFindDeclaredMethod 是否查找Declared字段
     * @param isUpwardFind 是否向上去其父类中寻找
     * @param methodName 要获取的方法的名字
     * @param methodParameterTypes 方法参数类型
     * @return 给定的类中给定名称以及给定参数类型的方法
     */
    public static Method getMethod(Class<?> sourceClass, boolean isFindDeclaredMethod, boolean isUpwardFind, String methodName, Class<?>... methodParameterTypes){
        Method method = null;
        try {
            method = isFindDeclaredMethod ? sourceClass.getDeclaredMethod(methodName, methodParameterTypes) : sourceClass.getMethod(methodName, methodParameterTypes);
        } catch (NoSuchMethodException e1) {
            if(isUpwardFind){
                Class<?> classs = sourceClass.getSuperclass();
                while(method == null && classs != null){
                    try {
                        method = isFindDeclaredMethod ? classs.getDeclaredMethod(methodName, methodParameterTypes) : classs.getMethod(methodName, methodParameterTypes);
                    } catch (NoSuchMethodException e11) {
                        classs = classs.getSuperclass();
                    }
                }
            }
        }
        return method;
    }

    /**
     * 从指定的类中获取指定的方法，默认获取Declared类型的方法、向上查找
     * @param sourceClass 指定的类
     * @param methodName 方法名
     * @param methodParameterTypes 方法参数类型
     * @return
     */
    public static Method getMethod(Class<?> sourceClass, String methodName, Class<?>... methodParameterTypes){
        return getMethod(sourceClass, true, true, methodName, methodParameterTypes);
    }

    /**
     * 从指定的类中获取指定名称的不带任何参数的方法，默认获取Declared类型的方法并且向上查找
     * @param sourceClass 指定的类
     * @param methodName 方法名
     * @return
     */
    public static Method getMethod(Class<?> sourceClass, String methodName){
        return getMethod(sourceClass, methodName, EMPTY_PARAM_TYPES);
    }

    /**
     * 获取给定类的所有方法
     *
     * @param clas                给定的类
     * @param isGetDeclaredMethod 是否需要获取Declared方法
     * @param isFromSuperClassGet 是否需要把其父类中的方法也取出
     * @param isDESCGet           在最终获取的列表里，父类的方法是否需要排在子类的前面。只有需要把其父类中的方法也取出时此参数才有效
     * @return 给定类的所有方法
     */
    public static List<Method> getMethods(Class<?> clas, boolean isGetDeclaredMethod, boolean isFromSuperClassGet, boolean isDESCGet) {
        List<Method> methodList = new ArrayList<Method>();
        //如果需要从父类中获取
        if (isFromSuperClassGet) {
            //获取当前类的所有父类
            List<Class<?>> classList = getSuperClasss(clas, true);

            //如果是降序获取
            if (isDESCGet) {
                for (int w = classList.size() - 1; w > -1; w--) {
                    for (Method method : isGetDeclaredMethod ? classList.get(w).getDeclaredMethods() : classList.get(w).getMethods()) {
                        methodList.add(method);
                    }
                }
            } else {
                for (int w = 0; w < classList.size(); w++) {
                    for (Method method : isGetDeclaredMethod ? classList.get(w).getDeclaredMethods() : classList.get(w).getMethods()) {
                        methodList.add(method);
                    }
                }
            }
        } else {
            for (Method method : isGetDeclaredMethod ? clas.getDeclaredMethods() : clas.getMethods()) {
                methodList.add(method);
            }
        }
        return methodList;
    }

    /**
     * 获取给定类的所有方法
     *
     * @param sourceClass 给定的类
     * @return 给定类的所有方法
     */
    public static List<Method> getMethods(Class<?> sourceClass) {
        return getMethods(sourceClass, true, true, true);
    }
}
