package anu.cookcompass.model;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.io.File;

import anu.cookcompass.Utils;


/**
 * Configuration
 */
public class Config {
    public boolean night; // night mode / day light mode
    transient File file;

    /**
     * @param file
     * @return configuration
     * Load a config from given file
     */
//    public static Config load(File file) {
//        Config config = Utils.readJson(file, Config.class);
//        config.file = file;
//        return config;
//    }

    /**
     * Save the config back to its storage
     */
    public void save() {
        Utils.saveJson(file, this);
    }

    /**
     * @param night
     * Set the night mode and save this configuration back to its config file
     */
    public void setNight(AppCompatActivity activity, boolean night) {
        this.night = night;
        this.save();

        int mode = AppCompatDelegate.MODE_NIGHT_NO;
        if (night) mode = AppCompatDelegate.MODE_NIGHT_YES;
        activity.getDelegate().setLocalNightMode(mode);
    }
}
