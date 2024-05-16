package anu.cookcompass;

import android.content.Context;
import android.widget.Toast;

/**
 * Singleton class for managing Toast messages.
 * This class ensures that only one Toast message is shown at a time.
 * If a new Toast message is shown while another one is still visible, the existing message is updated.
 *
 * @autor u7760022, Xinyang Li
 */
public class MyToast {
    // Singleton instance of MyToast
    static MyToast inst;

    // Instance of Toast to display messages
    Toast mToast = null;

    // Context in which the Toast should be displayed
    Context mContext = null;

    // Private constructor to prevent instantiation
    private MyToast() {
    }

    /**
     * Returns the singleton instance of MyToast.
     * If the instance is null, a new instance is created.
     *
     * @return the singleton instance of MyToast
     */
    public static MyToast getInstance() {
        if (inst == null) inst = new MyToast();
        return inst;
    }

    /**
     * Initializes the context for the MyToast instance.
     * This context is used to display Toast messages.
     *
     * @param context the context in which the Toast should be displayed
     */
    public void init(Context context) {
        mContext = context;
    }

    /**
     * Displays a Toast message with the given text.
     * If a Toast message is already being shown, it updates the text of the existing Toast.
     *
     * @param text the text to be displayed in the Toast message
     */
    public void showToast(String text) {
        // Return if the context is not initialized
        if (mContext == null) return;

        if (mToast == null) {
            // Create a new Toast if none exists.
            mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
        } else {
            // Update the existing Toast with the new text.
            mToast.setText(text);
        }
        // Show the Toast.
        mToast.show();
    }
}
