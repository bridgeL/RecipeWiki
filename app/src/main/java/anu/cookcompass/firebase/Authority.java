package anu.cookcompass.firebase;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import anu.cookcompass.Utils;
import anu.cookcompass.login.Response;

public class Authority {
    static String TAG = "Authority";

    public static CompletableFuture<Response> createAccount(String email, String password) {
        // [START create_user_with_email]
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        CompletableFuture<Response> future = new CompletableFuture<>();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            future.complete(new Response(true, "createUserWithEmail:success"));
                            Log.d(TAG, "createUserWithEmail:success");
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            future.complete(new Response(false, "createUserWithEmail:failure"));
                        }
                    }
                });
        // [END create_user_with_email]
        return future;
    }

    public static CompletableFuture<Response> signIn(String email, String password) {
        // [START sign_in_with_email]
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        CompletableFuture<Response> future = new CompletableFuture<>();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e(TAG, "signInWithEmail:success");
                            future.complete(new Response(true, "signInWithEmail:success"));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e(TAG, "signInWithEmail:failure", task.getException());
                            future.complete(new Response(false, "signInWithEmail:failure"));
                        }
                    }
                });
        // [END sign_in_with_email]
        return future;
    }
}
