package anu.cookcompass;

import android.content.Context;
import android.widget.Toast;

/**
 * @author u7760022, Xinyang Li
 * The class is MyToast
 */
public class MyToast {
    static MyToast inst;
    Toast mToast = null;
    Context mContext = null;

    private MyToast() {
    }

    public static MyToast getInstance() {
        if (inst == null) inst = new MyToast();
        return inst;
    }

    public void init(Context context) {
        mContext = context;
    }

    public void showToast(String text) {
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
