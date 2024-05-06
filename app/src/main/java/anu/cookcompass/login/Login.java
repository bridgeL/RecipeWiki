package anu.cookcompass.login;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import anu.cookcompass.pattern.Observer;
import anu.cookcompass.model.Response;
import anu.cookcompass.user.UserManager;


public class Login {
    String TAG = getClass().getSimpleName();
    private static Login instance;

    private Login() {
    }

    public static Login getInstance() {
        if (instance == null) instance = new Login();
        return instance;
    }

    public void login(String username, String password, Observer<Response> observer) {
        //Check username and password format

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

        if (Objects.isNull(password) || password.isEmpty()) {
            observer.onDataChange(new Response(false, "Empty password!"));
            return;
        }

        //Return CompletableFuture<Response> based on the success or failure of the login attempt
        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(username, password)
                .addOnSuccessListener(data -> {
                    Log.d(TAG, "Login successful!");
                    observer.onDataChange(new Response(true, "Login successful!"));

                    // download user data from cloud
                    String uid = data.getUser().getUid();
                    UserManager.getInstance().initCloudUser(uid);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Login failed!", e);
                    observer.onDataChange(new Response(false, e.getMessage()));
                });
    }
}
