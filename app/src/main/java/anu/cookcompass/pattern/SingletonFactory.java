package anu.cookcompass.pattern;

import java.util.HashMap;

public class SingletonFactory {
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
}
