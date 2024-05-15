package anu.cookcompass.theme;

import android.content.Context;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import anu.cookcompass.Utils;

/**
 * @author u7752874, Xinlei Wen
 * @feature Data-format
 */
/**
 * A singleton class that stores the theme color of the application.
 */
public class ThemeColor {
    private static ThemeColor instance;
    private Context context;
    private String themeColor;
    private String themeName;   // the color value and the name of the current theme
    private List<String[]> themeList; // the list for storing the theme
    private String[] themeNameList;    // stores the names of themes

    /**
     * Initialize the singleton by calling the constructor. This function also defines the themes
     * available for this run.
     */
    public static void init(Context context){
        init(context, null);
    }
    public static void init(Context context,String csvFilePath){
        instance = new ThemeColor();
        instance.context = context;

        // try reading the theme from file themeList.csv
        try {
            File file;
            if (csvFilePath != null && !csvFilePath.isEmpty()) {
                file = new File(csvFilePath);
            } else {
                file = new File(instance.context.getFilesDir(), "themeList.csv");
            }
            if (!file.exists()) {
                // if file not exist, create a default one and then read
                System.out.println("themeList.csv not found. Created by default");
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                bos.write(("Default,#FFB241\n" +
                        "White,#FFFFFF\n" +
                        "Ultramarine,#4e72b8\n" +
                        "PaleGreen,#cde67c\n").getBytes());
                bos.flush();
                bos.close();
                fos.close();
            }
            // read the theme color from the file
            instance.themeList = Utils.readCsv(file);
            // create available theme name list
            List<String> temp = new ArrayList<>();
            for(String[] s: instance.themeList){
                temp.add(s[0]);
            }
            instance.themeNameList = temp.toArray(new String[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setThemeColor(String themeColor){
        assert instance != null;
        instance.themeColor = themeColor;
    }

    public static String getThemeColor(){
        assert instance != null;
        return instance.themeColor;
    }

    public static String getThemeName(){
        return instance.themeName;
    }

    public static String[] getThemeList(){
        return instance.themeNameList;
    }

    public static String findColorByName(String name){
        for (String[] s: instance.themeList) {
            if(Objects.equals(s[0], name)){
                return s[1];
            }
        }
        System.out.println("Unable to find theme with name " + name + ", return #000000 by default.");
        return "#000000";
    }

    public static String findNameByColor(String color){
        for (String[] s: instance.themeList) {
            if(Objects.equals(s[1], color)){
                return s[0];
            }
        }
        System.out.println("Unable to find theme with name " + color + ", return Undefined by default.");
        return "Undefined";
    }

    /**
     * Loads the theme recorded in local txt file. If no such file exists, set theme color to
     * default value and create the file.
     */
    public static void loadTheme(){
        loadTheme(null);
    }
    public static void loadTheme(String txtFilePath){
        try {
            File file;
            if (txtFilePath != null && !txtFilePath.isEmpty()) {
                file = new File(txtFilePath);
            } else {
                file = new File(instance.context.getFilesDir(), "theme.txt");
            }
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
                instance.themeName = "Default";
            }
            else{
                // if the file exists, read the theme color from the file
                FileInputStream fis = new FileInputStream(file);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
                String line = reader.readLine();
                if(line != null){
                    instance.themeColor = line;
                    instance.themeName = findNameByColor(line);
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
                instance.themeName = "Default";
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
