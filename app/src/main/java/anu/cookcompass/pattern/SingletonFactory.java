package anu.cookcompass.pattern;

import android.util.Log;

import java.util.HashMap;

public class SingletonFactory {
    static String TAG = "SingletonFactory";
    static HashMap<Class<?>, Object> data = new HashMap<>();

    public static <T> T getInstance(Class<T> clazz) {
        if (!data.containsKey(clazz)) {
            try {
                // Create a new instance via reflection and store it
                // that's so crazy, even though constructor is marked as private,
                // it can still be accessed by this api
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
