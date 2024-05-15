package anu.cookcompass;

import android.content.Context;
import android.widget.Toast;

import anu.cookcompass.pattern.SingletonFactory;

public class MyToast {
    private static Toast mToast = null;
    Context mContext = null;

    private MyToast() {
    }

    public static MyToast getInstance() {
        return SingletonFactory.getInstance(MyToast.class);
    }

    public void init(Context context) {
        mContext = context;
    }

    public void showToast(String text, int duration) {
        if (mContext == null) return;

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
}
