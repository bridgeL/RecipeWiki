package anu.cookcompass;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

import anu.cookcompass.model.Global;


public class Utils {
    public static void switchPage(AppCompatActivity first, AppCompatActivity second){
        Global.getInstance().currentActivity = second;
    }

    public static void toastMessage(String message){
        Toast.makeText(Global.getInstance().currentActivity, message, Toast.LENGTH_SHORT).show();
    }

    public static byte[] getImageBytesFromImageView(ImageView imageView){
        // Get the data from an ImageView as bytes
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }


    /**
     * @param context activity
     * @param fileName filepath
     * @return File
     * Copy file from assets to /data/data/anu.cookcompass/files
     */
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

    /**
     * @param file csv file
     * @return List
     * read csv file
     */
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

    /**
     * @param file json file
     * @param tClass type of object
     * @param <T> type of object
     * @return data
     * Read Json file and convert it to relative object
     */
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

    /**
     * @param file json file
     * @param typeToken type of list of object
     * @param <T> type of list of object
     * @return List of data
     * Read Json file and convert it to an array of relative object
     */
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

    /**
     * @param file json file
     * @param data data
     * Convert object to json and save it into a file
     */
    public static void saveJson(File file, Object data){
        try (FileWriter fileWriter = new FileWriter(file)) {
            Gson gson = new Gson();
            fileWriter.write(gson.toJson(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
