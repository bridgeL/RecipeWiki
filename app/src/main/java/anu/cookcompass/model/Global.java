package anu.cookcompass.model;


import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;

/**
 * Design Pattern: Singleton
 * <p>
 * Global Instances
 * </p>
 */
public class Global {
    private static Global instance = null;

    public File filesDir;
    public AssetManager assetManager;

    private Global(Context context) {
        filesDir = context.getFilesDir();
        assetManager = context.getAssets();
    }

    public static Global getInstance(Context context) {
        assert instance == null;
        instance = new Global(context);
        return instance;
    }

    public static Global getInstance() {
        assert instance != null;
        return instance;
    }
}
