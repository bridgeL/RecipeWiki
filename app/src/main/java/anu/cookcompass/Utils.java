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
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * Utility class providing various helper methods.
 * Features include random integer generation, string manipulation, toast messages, page switching,
 * timestamp conversion, and file operations.
 *
 * @version 1.0
 * @author
 * u7760022, Xinyang Li
 * u7752874, Xinlei Wen
 * @feature Data-format
 */
public class Utils {

    /**
     * Generates a random integer between the specified start (inclusive) and end (exclusive).
     * @param start the starting value (inclusive)
     * @param end the ending value (exclusive)
     * @return a random integer between start and end
     */
    public static int randInt(int start, int end) {
        // Math.random() generates a random double between 0.0 and 1.0.
        // Multiplying by (end - start) and adding start adjusts the range.
        return (int) Math.floor(Math.random() * (end - start) + start);
    }

    /**
     * Cuts a string to the specified maximum length.
     * @param str the string to be cut
     * @param maxLength the maximum length of the string
     * @return the cut string
     */
    public static String cutString(String str, int maxLength) {
        // Truncate the string if its length exceeds maxLength.
        str = str.length() > maxLength ? str.substring(0, maxLength) : str;
        return str;
    }

    // Singleton Toast object to prevent multiple toasts from stacking.
    private static Toast mToast;
    static Context context;

    /**
     * Displays a toast message.
     * @param mContext the context in which the toast should be displayed
     * @param text the message to be displayed
     * @param duration the duration of the toast (Toast.LENGTH_SHORT or Toast.LENGTH_LONG)
     */
    public static void showToast(Context mContext, String text, int duration) {
        if (mToast == null) {
            // Create a new Toast if none exists.
            mToast = Toast.makeText(mContext, text, duration);
        } else {
            // Update the existing Toast with the new text.
            mToast.setText(text);
        }
        // Show the Toast.
        mToast.show();
    }

    /**
     * Displays a short toast message.
     * @param context the context in which the toast should be displayed
     * @param message the message to be displayed
     */
    public static void showShortToast(Context context, String message) {
        showToast(context, message, Toast.LENGTH_SHORT);
    }

    /**
     * Displays a long toast message.
     * @param context the context in which the toast should be displayed
     * @param message the message to be displayed
     */
    public static void showLongToast(Context context, String message) {
        showToast(context, message, Toast.LENGTH_LONG);
    }

    /**
     * Switches to a new activity.
     * @param first the current activity
     * @param secondClass the class of the activity to switch to
     */
    public static void switchPage(Activity first, Class<? extends Activity> secondClass) {
        // Create an intent to start the new activity.
        Intent intent = new Intent(first, secondClass);
        // Start the new activity.
        first.startActivity(intent);
    }

    /**
     * Converts a Unix timestamp to a formatted string.
     * @param timestamp the Unix timestamp
     * @return the formatted date and time string
     */
    public static String timestamp2string(int timestamp) {
        // Convert the timestamp to LocalDateTime.
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault());
        // Define the date and time format.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // Format and return the date and time.
        return dateTime.format(formatter);
    }

    /**
     * Gets the current Unix timestamp.
     * @return the current Unix timestamp
     */
    public static int getTimestamp() {
        // Get the current time in milliseconds and convert to seconds.
        return (int) (System.currentTimeMillis() / 1000);
    }

    /**
     * Copies a file from the assets folder to the app's internal storage.
     * @param context the context of the application
     * @param fileName the name of the file to copy
     * @return the copied file
     */
    public static File copyFileFromAssets(Context context, String fileName) {
        // Create a new file object in the app's internal storage.
        File file = new File(context.getFilesDir(), fileName);
        if (!file.exists()) {
            try {
                // Ensure the parent directory exists.
                file.getParentFile().mkdirs();

                // Open an input stream to the file in the assets folder.
                InputStream inputStream = context.getAssets().open(fileName);
                // Open an output stream to the new file.
                OutputStream outputStream = new FileOutputStream(file);

                byte[] buffer = new byte[1024];
                int length;
                // Read from the input stream and write to the output stream.
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }

                // Close the streams.
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                // Print the stack trace if an exception occurs.
                e.printStackTrace();
            }
        }
        // Return the copied file.
        return file;
    }

    /**
     * Reads a CSV file and returns the data as a list of string arrays.
     * @param file the CSV file to read
     * @return a list of string arrays containing the CSV data
     */
    public static List<String[]> readCsv(File file) {
        List<String[]> data = null;
        try {
            // Open a FileReader to the CSV file.
            FileReader fileReader = new FileReader(file);
            // Create a CSVReader to parse the CSV file.
            CSVReader reader = new CSVReader(fileReader);
            // Read all data from the CSV file.
            data = reader.readAll();
            // Close the readers.
            reader.close();
            fileReader.close();
        } catch (Exception e) {
            // Print the stack trace if an exception occurs.
            e.printStackTrace();
        }
        // Return the CSV data.
        return data;
    }

    /**
     * Reads a text file and returns the data as a list of strings.
     * @param file the text file to read
     * @return a list of strings containing the file data
     */
    public static List<String> readTxt(File file) {
        List<String> data = new ArrayList<>();
        try {
            // Open a FileReader to the text file.
            FileReader fileReader = new FileReader(file);
            // Create a BufferedReader to read the file line by line.
            BufferedReader br = new BufferedReader(fileReader);
            String line;
            // Read each line and add it to the data list.
            while ((line = br.readLine()) != null) {
                data.add(line);
            }
            // Close the readers.
            br.close();
            fileReader.close();
        } catch (Exception e) {
            // Print the stack trace if an exception occurs.
            e.printStackTrace();
        }
        // Return the file data.
        return data;
    }

    /**
     * Reads a JSON file and converts it to a specified object type.
     * @param <T> the type of the object to return
     * @param file the JSON file to read
     * @param typeToken the type token representing the object type
     * @return the object read from the JSON file
     */
    public static <T> T readJson(File file, TypeToken<T> typeToken) {
        T data = null;
        // Use a try-with-resources statement to ensure the FileReader is closed.
        try (FileReader fileReader = new FileReader(file)) {
            // Create a Gson object to handle JSON parsing.
            Gson gson = new Gson();
            // Get the type from the type token.
            Type type = typeToken.getType();
            // Parse the JSON file to the specified object type.
            data = gson.fromJson(fileReader, type);
        } catch (IOException e) {
            // Print the stack trace if an exception occurs.
            e.printStackTrace();
        }
        // Return the parsed object.
        return data;
    }

    /**
     * Converts an object to a JSON string.
     * @param data the object to convert
     * @return the JSON string representation of the object
     */
    public static String toJson(Object data) {
        // Create a Gson object to handle JSON conversion.
        Gson gson = new Gson();
        // Convert the object to a JSON string.
        return gson.toJson(data);
    }

    /**
     * Converts an object to a JSON string and saves it to a file.
     * @param file the file to save the JSON data to
     * @param data the object to convert and save
     */
    public static void saveJson(File file, Object data) {
        // Use a try-with-resources statement to ensure the FileWriter is closed.
        try (FileWriter fileWriter = new FileWriter(file)) {
            // Create a Gson object to handle JSON conversion.
            Gson gson = new Gson();
            // Convert the object to a JSON string and write it to the file.
            fileWriter.write(gson.toJson(data));
        } catch (IOException e) {
            // Print the stack trace if an exception occurs.
            e.printStackTrace();
        }
    }
}
