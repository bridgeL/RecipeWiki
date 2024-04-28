package anu.cookcompass.firebase;

import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import java.util.concurrent.CompletableFuture;

import anu.cookcompass.login.Response;

public class Authority {
    static String TAG = "Authority";

    public static CompletableFuture<Response> createAccount(String email, String password) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        CompletableFuture<Response> future = new CompletableFuture<>();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        future.complete(new Response(true, "createUserWithEmail:success"));
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        future.complete(new Response(false, task.getException().getMessage()));
                    }
                });
        return future;
    }

    public static CompletableFuture<Response> signIn(String email, String password) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        CompletableFuture<Response> future = new CompletableFuture<>();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        future.complete(new Response(true, "signInWithEmail:success"));
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e(TAG, "signInWithEmail:failure", task.getException());
                        future.complete(new Response(false, task.getException().getMessage()));
                    }
                });
        return future;
    }
}
