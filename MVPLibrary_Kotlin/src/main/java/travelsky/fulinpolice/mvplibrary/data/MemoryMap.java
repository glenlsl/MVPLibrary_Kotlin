package travelsky.fulinpolice.mvplibrary.data;

import android.support.v4.util.ArrayMap;

public class MemoryMap {

    private static final ArrayMap<String, Object> sMap = new ArrayMap<>();

    public static void putObject(String key, Object value) {
        sMap.put(key, value);
    }

    public static void remove(String key) {
        sMap.remove(key);
    }

    public static void clear() {
        sMap.clear();
    }

    public static boolean getBoolean(String key) {
        Boolean val = getObject(key);
        if (val == null)
            return false;
        return val;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getObject(String key) {
        if (containsKey(key))
            return (T) sMap.get(key);
        else
            return null;
    }

    public static <T> T getObject(String key, Class<T> cls) {
        if (containsKey(key))
            return cls.cast(sMap.get(key));
        else
            return null;
    }

    public static boolean containsValue(Object obj) {
        return sMap.containsValue(obj);
    }

    public static boolean containsKey(String key) {
        return sMap.containsKey(key);
    }
}