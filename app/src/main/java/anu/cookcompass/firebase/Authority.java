package anu.cookcompass.firebase;

import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import java.util.concurrent.CompletableFuture;

import anu.cookcompass.model.Response;

public class Authority {
    static String TAG = "Authority";

    public static CompletableFuture<Response> createAccount(String email, String password) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        CompletableFuture<Response> future = new CompletableFuture<>();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        future.complete(new Response(true, "Account registration successful!"));
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "Account registration failed!", task.getException());
                        future.complete(new Response(false, task.getException().getMessage()));
                    }
                });
        return future;
    }

    public static CompletableFuture<Response> logIn(String email, String password) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        CompletableFuture<Response> future = new CompletableFuture<>();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        future.complete(new Response(true, "Login successful!"));
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e(TAG, "Login failed!", task.getException());
                        future.complete(new Response(false, task.getException().getMessage()));
                    }
                });
        return future;
    }
}
