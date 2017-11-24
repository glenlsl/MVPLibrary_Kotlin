package travelsky.fulinpolice.mvplibrary.util;

import android.text.TextUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Solin on 2017/1/6.
 */

public class VerifyTool {
    /**
     * 判断是否为null
     *
     * @param str
     * @return true：""
     */
    public static String nullToEmpty(String str) {
        if (TextUtils.isEmpty(str) || "null".equals(str)) {
            str = "";
        }
        return str;
    }

    public static boolean isEmpty(String str) {
        return nullToEmpty(str).isEmpty();
    }

    /**
     * 将对象中所有为null的字段赋值为""
     *
     * @param obj 需要赋值的对象
     * @return obj
     */
    public static <T> T notNullBean(T obj) {
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Field.setAccessible(fields, true);
        for (Field f : fields) {
            try {
                Object o = f.get(obj);
                if (String.class.equals(f.getType())) {
                    if (null == o || "null".equals(o)) {
                        f.set(obj, "");
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    /**
     * 将对象中int类型字段为0的赋值为-1
     *
     * @param obj 需要赋值的对象
     * @return obj
     */
    public static <T> T notZeroBean(T obj, int toNum) {
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Field.setAccessible(fields, true);
        for (Field f : fields) {
            try {
                Object o = f.get(obj);
                if (Integer.class.equals(f.getType()) || Long.class.equals(f.getType())) {
                    if (null == o || o.equals(0)) {
                        if (!TextUtils.equals(f.getName(), "_id")) {
                            f.set(obj, toNum);
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    public static <T> T notZeroNullBean(T obj, int toNum) {
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Field.setAccessible(fields, true);
        for (Field f : fields) {
            try {
                Object o = f.get(obj);
                if (Integer.class.equals(f.getType()) || Long.class.equals(f.getType())) {
                    if (null == o || o.equals(0)) {
                        f.set(obj, toNum);
                    }
                } else if (String.class.equals(f.getType())) {
                    if (null == o || "null".equals(o)) {
                        f.set(obj, "");
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    /**
     * 将列表所有对象中String类型字段为null的赋值为""
     *
     * @param tList 需要赋值的列表
     * @return tList
     */
    public static <T> List<T> notNullListBean(List<T> tList) {
        for (int i = 0; i < tList.size(); i++) {
            tList.set(i, notNullBean(tList.get(i)));
        }
        return tList;
    }

    /**
     * 将列表所有对象中int类型字段为0的赋值为-1
     *
     * @param tList 需要赋值的列表
     * @return tList
     */
    public static <T> List<T> notZeroListBean(List<T> tList, int toNum) {
        for (int i = 0; i < tList.size(); i++) {
            tList.set(i, notZeroBean(tList.get(i), toNum));
        }
        return tList;
    }

    public static <T> List<T> notNullListBean(List<T> tList, int toNum) {
        for (int i = 0; i < tList.size(); i++) {
            tList.set(i, notZeroNullBean(tList.get(i), toNum));
        }
        return tList;
    }
}
