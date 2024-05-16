package anu.cookcompass.login;


import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import anu.cookcompass.pattern.Observer;
import anu.cookcompass.pattern.SingletonFactory;
import anu.cookcompass.user.UserManager;

public class Register {
    String TAG = "Register";
    private Register() {}

    public static Register getInstance() {
        return SingletonFactory.getInstance(Register.class);
    }

    public void register(String username, String password1, String password2, Observer<Response> observer) {
        //Check username format

        /*
        Regex breakdown:
        a) [A-Za-z0-9._%+-]+ : One or more numbers and/or letters and/or special characters
        b) @ : @ symbol in emails
        c) [A-Za-z0-9.-]+ : One or more numbers, letters, dots, and/or hyphens
        d) \\. : A dot
        e) [A-Za-z]{2,} : Two or more letters
         */
        if (Objects.isNull(username) || username.isEmpty() || !username.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            observer.onDataChange(new Response(false, "Wrong username format!"));
            return;
        }

        //Make sure password is not null or empty
        if (Objects.isNull(password1) || password1.isEmpty()) {
            observer.onDataChange(new Response(false, "Empty password!"));
            return;
        }

        //Make sure rewritten password matches the first password
        if (!password1.equals(password2)) {
            observer.onDataChange(new Response(false, "Second password doesn't match the first!"));
            return;
        }

        //Return CompletableFuture<Response> based on the success or failure of the register attempt
        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(username, password1)
                .addOnSuccessListener(data -> {
                    Log.d(TAG, "Account registration successful!");
                    observer.onDataChange(new Response(true, "Account registration successful!"));

                    // create user data, and upload it to cloud
                    UserManager.getInstance().createUserData(data.getUser());
                })
                .addOnFailureListener(e -> {
                    // If register fails, display a message to the user.
                    Log.w(TAG, "Account registration failed!", e);
                    observer.onDataChange(new Response(false, e.getMessage()));
                });
    }
}
