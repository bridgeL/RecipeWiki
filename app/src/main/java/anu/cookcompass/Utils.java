package anu.cookcompass;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;


public class Utils {
    public static File copyFileFromAssets(Context context, String fileName) {
        File file = new File(context.getFilesDir(), fileName);
        if (!file.exists()) {
            try {
                InputStream inputStream = context.getAssets().open(fileName);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                FileWriter fileWriter = new FileWriter(file);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    fileWriter.write(line + "\n");
                }
                fileWriter.flush();
                fileWriter.close();
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static List<String[]> readCsv(File file) {
        List<String[]> data = null;
        try {
            FileReader fileReader = new FileReader(file);
            CSVReader reader = new CSVReader(fileReader);
            data = reader.readAll();
            reader.close();
            fileReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static <T> T readJson(File file, Class<T> tClass){
        T data = null;
        try (FileReader fileReader = new FileReader(file)) {
            Gson gson = new Gson();
            Type type = TypeToken.getParameterized(tClass).getType();
            data = gson.fromJson(fileReader, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static <T> T readJson(File file, TypeToken<T> typeToken) {
        T data = null;
        try (FileReader fileReader = new FileReader(file)) {
            Gson gson = new Gson();
            Type type = typeToken.getType();
            data = gson.fromJson(fileReader, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void saveJson(File file, Object data){
        try (FileWriter fileWriter = new FileWriter(file)) {
            Gson gson = new Gson();
            fileWriter.write(gson.toJson(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
