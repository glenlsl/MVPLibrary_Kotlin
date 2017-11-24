package travelsky.fulinpolice.mvplibrary.util;


import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 常用泛型工具
 * Created by baixiaokang on 16/4/30.
 */
public class GenericityUtil {
    /**
     * 通过实例工厂去实例化相应类
     *
     * @param o   带泛型的对象
     * @param i   需要实例化的对象在泛型中的位置
     * @param <T> 返回实例的泛型类型
     * @return
     */
    public static <T> T getInstance(Object o, int i) {
        if (o.getClass().getGenericSuperclass() instanceof ParameterizedType) {
            Class mClass = getGenericType(o, i);
            return getInstance(mClass);
        }
        return null;
    }

    /**
     * 通过实例工厂去实例化相应类
     *
     * @param <T> 返回实例的泛型类型
     * @return
     */
    public static <T> T getInstance(Class clazz) {
        try {
            //            return (T) InstanceFactory.create(clazz);
            return (T) Class.forName(clazz.getName()).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过实例工厂去实例化相应类
     *
     * @param <?> 返回实例的泛型类型
     * @return
     */
    /*public static <T> T get(Class clazz, View view) {
//        return (T) InstanceFactory.create(clazz, view);
        return (T) InstanceFactory.create(clazz, view);
    }*/
    public static <T> T getInstance(String className) {
        try {
            return (T) Class.forName(className).newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建带参数构造方法创建的实例
     *
     * @param clazz  实例类
     * @param vClass 参数
     * @return 实例
     */
    public static <T> T getInstance(Class clazz, Class vClass) {
        Class<?> cls = null;
        Object obj = null;
        try {
            cls = Class.forName(clazz.getName());
            Constructor<?> con = cls.getConstructor(vClass);
            obj = con.newInstance(vClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return (T) obj;
    }

    /**
     * 创建带参数构造方法创建的实例
     *
     * @param clazz 实例类
     * @param view  参数
     * @return 实例
     */
    public static <T> T getInstance(Class clazz, View view) {
        Class<?> cls = null;
        Object obj = null;
        try {
            cls = Class.forName(clazz.getName());
            Constructor<?> con = cls.getConstructor(view.getClass());
            obj = con.newInstance(view);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return (T) obj;
    }

    /**
     * 获取父类泛型的类型
     *
     * @param o 继承于拥有泛型的对象
     * @param i 下表为i的泛型
     * @return
     */
    public static Class getGenericType(Object o, int i) {
        Type type = o.getClass().getGenericSuperclass();
        ParameterizedType p = (ParameterizedType) type;
        return (Class) p.getActualTypeArguments()[i];
    }

    public static Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
