package anu.cookcompass.pattern;

import android.util.Log;

import java.util.HashMap;

/**
 * @author u7760022, Xinyang Li
 * The class is a SingletonFactory (design pattern), help to implement a lot of singleton
 */
public class SingletonFactory {
    static String TAG = "SingletonFactory";
    static HashMap<Class<?>, Object> data = new HashMap<>();

    public static <T> T getInstance(Class<T> clazz) {
        if (!data.containsKey(clazz)) {
            try {
                // Create a new instance via reflection and store it
                data.put(clazz, clazz.getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                throw new RuntimeException("Failed to create singleton instance", e);
            }
        }
        return clazz.cast(data.get(clazz));
    }

    public static void setInstance(Object obj) {
        Class<?> clazz = obj.getClass();
        if (data.containsKey(clazz)) {
            Log.w(TAG, "object has an instance already! previous instance will be replaced by the new one");
        }
        data.put(clazz, obj);
    }
}
