package anu.cookcompass.login;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import anu.cookcompass.model.Response;


public class Login {
    static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    static String TAG = "Authority";

    public static CompletableFuture<Response> login(String username, String password) {
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
        return logIn(username, password);
    }

    public static CompletableFuture<Response> logIn(String email, String password) {
        CompletableFuture<Response> future = new CompletableFuture<>();
        mAuth
                .signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(unused->{
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
