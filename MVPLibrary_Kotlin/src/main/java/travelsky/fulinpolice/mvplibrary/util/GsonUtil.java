package travelsky.fulinpolice.mvplibrary.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Gson常用方法
 * Created by Solin on 2016/12/17.
 */

public class GsonUtil {

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return Builder.gson.fromJson(json, classOfT);
    }

    public static <T> T fromJson(String json, Type type) {
        return Builder.gson.fromJson(json, type);
    }

    public static <T> List<T> fromList(String json) {
        return Builder.gson.fromJson(json, new TypeToken<List<T>>() {
        }.getType());
    }

    public static <T> List<T> fromList(String json, Class<T> classOfT) {
        return Builder.gson.fromJson(json, new TypeToken<List<T>>() {
        }.getType());
    }

    public static <T> ArrayList<T> fromArrayList(String json) {
        return Builder.gson.fromJson(json, new TypeToken<ArrayList<T>>() {
        }.getType());
    }

    public static <T> ArrayList<T> fromArrayList(String json, Class<T> classOfT) {
        return Builder.gson.fromJson(json, new TypeToken<ArrayList<T>>() {
        }.getType());
    }

    public static <K, V> Map<K, V> fromMap(String json) {
        return Builder.gson.fromJson(json,
                new TypeToken<Map<K, V>>() {
                }.getType());
    }

    public static <K, V> Map<K, V> fromMap(String json, Class<K> kClass, Class<V> vClass) {
        return Builder.gson.fromJson(json,
                new TypeToken<Map<K, V>>() {
                }.getType());
    }

    public static <T> String toJson(T t) {
        return Builder.gson.toJson(t);
    }

    public static <T> String toJson(List<T> list) {
        return Builder.gson.toJson(list);
    }

    public static <T> String toJson(ArrayList<T> arrayList) {
        return Builder.gson.toJson(arrayList);
    }

    public static <T> String toJson(Map<String, T> map) {
        return Builder.gson.toJson(map);
    }

    public static Gson getGson() {
        return Builder.gson;
    }

    private static class Builder {
        private static Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .serializeNulls()
                //                .setPrettyPrinting()
                .enableComplexMapKeySerialization()
                .create();

    }
}
