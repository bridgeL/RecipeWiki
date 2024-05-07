package anu.cookcompass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;


public class Utils {

    public static int randInt(int start, int end) {
        return (int) Math.floor(Math.random() * (end - start) + start);
    }

    public static String cutString(String str, int maxLength) {
        str = str.length() > maxLength ? str.substring(0, maxLength) : str;
        return str;
    }

    private static Toast mToast;

    public static void showToast(Context mContext, String text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, text, duration);
        } else {
            mToast.setText(text);
//            mToast.setDuration(duration);
        }
        // no use for API 30 or higher
//        // Positions the Toast at the top of the screen, centered horizontally, with 100px offset from the top
//        mToast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
        mToast.show();
    }

    public static void showShortToast(Context context, String message) {
        showToast(context, message, Toast.LENGTH_SHORT);
    }

    public static void showLongToast(Context context, String message) {
        showToast(context, message, Toast.LENGTH_LONG);
    }

    public static <T> boolean ArraysEqual(List<T> array1, List<T> array2) {
        HashSet<T> set1 = new HashSet<>(array1);
        HashSet<T> set2 = new HashSet<>(array2);
        return set1.containsAll(set2) && set2.containsAll(set1);
    }

    public static Bitmap byteToBitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static void switchPage(Activity first, Class<? extends Activity> secondClass) {
        Intent intent = new Intent(first, secondClass);
        first.startActivity(intent);
    }

    public static byte[] getImageBytesFromImageView(ImageView imageView) {
        // Get the data from an ImageView as bytes
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }


    /**
     * @param fileName filepath
     * @return File
     * Copy file from assets to /data/data/anu.cookcompass/files
     */
    public static File copyFileFromAssets(Context context, String fileName) {
        File file = new File(context.getFilesDir(), fileName);
        if (!file.exists()) {
            try {
                // make sure parent folder exists
                file.getParentFile().mkdirs();

                // copy file
                InputStream inputStream = context.getAssets().open(fileName);
                OutputStream outputStream = new FileOutputStream(file);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }

                inputStream.close();
                outputStream.close();
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

    public static List<String> readTxt(File file) {
        List<String> data = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);
            String line;
            while ((line = br.readLine()) != null) {
                data.add(line);
            }
            br.close();
            fileReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * @param file   json file
     * @param tClass type of object
     * @param <T>    type of object
     * @return data
     * Read Json file and convert it to relative object
     */
    public static <T> T readJson(File file, Class<T> tClass) {
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
     * @param file      json file
     * @param typeToken type of list of object
     * @param <T>       type of list of object
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

    public static String toJson(Object data) {
        Gson gson = new Gson();
        return gson.toJson(data);
    }

    /**
     * @param file json file
     * @param data data
     *             Convert object to json and save it into a file
     */
    public static void saveJson(File file, Object data) {
        try (FileWriter fileWriter = new FileWriter(file)) {
            Gson gson = new Gson();
            fileWriter.write(gson.toJson(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
