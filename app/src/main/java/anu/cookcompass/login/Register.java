package anu.cookcompass.login;


import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import anu.cookcompass.model.Response;

public class Register {
    static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    static String TAG = "Authority";

    public static CompletableFuture<Response> register(String username, String password1, String password2) {
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
            return CompletableFuture.completedFuture(new Response(false, "Wrong username format!"));
        }

        //Make sure password is not null or empty
        if (Objects.isNull(password1) || password1.isEmpty()) {
            return CompletableFuture.completedFuture(new Response(false, "Empty password!"));
        }

        //Make sure rewritten password matches the first password
        if (!password1.equals(password2)) {
            return CompletableFuture.completedFuture(new Response(false, "Second password doesn't match the first!"));
        }

        //Return CompletableFuture<Response> based on the success or failure of the register attempt
        return createAccount(username, password1);
    }

    public static CompletableFuture<Response> createAccount(String email, String password) {
        CompletableFuture<Response> future = new CompletableFuture<>();
        mAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(unused -> {
                    Log.d(TAG, "Account registration successful!");
                    future.complete(new Response(true, "Account registration successful!"));
                })
                .addOnFailureListener(e -> {
                    // If register fails, display a message to the user.
                    Log.w(TAG, "Account registration failed!", e);
                    future.complete(new Response(false, e.getMessage()));
                });
        return future;
    }
}
