package anu.cookcompass.model;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;

import anu.cookcompass.Utils;
import anu.cookcompass.database.LocalDatabase;
import anu.cookcompass.firebase.Storage;

/**
 * Design Pattern: Singleton
 * <p>
 *     Global Instances
 * </p>
 */
public class Global {
    public Config config;
    public User currentUser = null;  // the user who login this app
    public LocalDatabase database;

    private FirebaseAuth mAuth;
    public Storage storage;
    public AppCompatActivity currentActivity = null;

    private static Global instance = null;

    private Global(Context context) {
        // load config
        File configFile = Utils.copyFileFromAssets(context, "config.json");
        config = Config.load(configFile);

        // load database (recipes and users)
        File recipeFile = Utils.copyFileFromAssets(context, "recipes.json");
        File userFile = Utils.copyFileFromAssets(context, "users.json");
        database = new LocalDatabase(recipeFile, userFile);

        mAuth = FirebaseAuth.getInstance();
        storage = new Storage();
    }

    /**
     * @param context
     * <p>
     *     this function must be called before getInstance()
     * </p>
     */
    public static void init(Context context) {
        if (instance == null) {
            instance = new Global(context);
        }
    }

    /**
     * @return Global instance
     * <p>
     *     this function can only be called after init()
     * </p>
     */
    public static Global getInstance() {
        assert instance != null;
        return instance;
    }
}
