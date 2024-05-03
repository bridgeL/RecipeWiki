package anu.cookcompass.login;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import anu.cookcompass.model.Response;


public class Login {
    String TAG = getClass().getSimpleName();
    private static Login instance;

    private Login() {}

    public static Login getInstance() {
        if (instance == null) instance = new Login();
        return instance;
    }

    public CompletableFuture<Response> login(String username, String password) {
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
            return CompletableFuture.completedFuture(new Response(false, "Wrong username format!"));
        }

        if (Objects.isNull(password) || password.isEmpty()) {
            return CompletableFuture.completedFuture(new Response(false, "Empty password!"));
        }

        //Return CompletableFuture<Response> based on the success or failure of the login attempt
        CompletableFuture<Response> future = new CompletableFuture<>();
        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(username, password)
                .addOnSuccessListener(unused -> {
                    Log.d(TAG, "Login successful!");
                    future.complete(new Response(true, "Login successful!"));
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Login failed!", e);
                    future.complete(new Response(false, e.getMessage()));
                });
        return future;
    }
}
