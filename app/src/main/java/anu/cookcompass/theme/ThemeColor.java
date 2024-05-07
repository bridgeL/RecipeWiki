package anu.cookcompass.theme;

import android.content.Context;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

/**
 * A singleton class that stores the theme color of the application.
 */
public class ThemeColor {
    private static ThemeColor instance;
    private Context context;
    private String themeColor;

    /**
     * Initialize the singleton by calling the constructor.
     */
    public static void init(Context context){
        instance = new ThemeColor();
        instance.context = context;
    }

    public static void setThemeColor(String themeColor){
        assert instance != null;
        instance.themeColor = themeColor;
    }

    public static String getThemeColor(){
        assert instance != null;
        return instance.themeColor;
    }

    public static ThemeType getThemeName(){
        switch(instance.themeColor){
            case "#FFB241" -> {
                return ThemeType.Default;
            }
            case "#FFFFFF" -> {
                return ThemeType.White;
            }
            case "#FFD700" ->{
                return ThemeType.Gold;
            }
            default -> {
                return ThemeType.Default;
            }
        }
    }

    /**
     * Loads the theme recorded in local txt file. If no such file exists, set theme color to
     * default value and create the file.
     */
    public static void loadTheme(){
        try {
            File file = new File(instance.context.getFilesDir(), "theme.txt");
            if (!file.exists()) {
                // if file not exist, create a default one and set it to the default color
                System.out.println("theme.txt not found. Created by default");
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                bos.write("#FFB241".getBytes());
                bos.flush();
                bos.close();
                fos.close();
                instance.themeColor = "#FFB241";
            }
            else{
                // if the file exists, read the theme color from the file
                FileInputStream fis = new FileInputStream(file);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
                String line = reader.readLine();
                if(line != null){
                    instance.themeColor = line;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Theme color loaded: "+instance.themeColor);
    }

    /**
     * Writes the current selected theme into the local txt file. If no such files exists, create
     * a new file and write the theme into it.
     */
    public static void writeTheme(){
        try {
            File file = new File(instance.context.getFilesDir(), "theme.txt");
            if (!file.exists()) {
                // if file not exist, create a default one and set it to the default color
                System.out.println("theme.txt not found. Created by default");
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                bos.write("#FFB241".getBytes());
                bos.flush();
                bos.close();
                fos.close();
                instance.themeColor = "#FFB241";
            }
            else{
                // if file exists, write the theme color into the file
                FileOutputStream fos = new FileOutputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                bos.write(instance.themeColor.getBytes());
                bos.flush();
                bos.close();
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
