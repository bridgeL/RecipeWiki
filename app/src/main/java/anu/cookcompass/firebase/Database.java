package anu.cookcompass.firebase;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.concurrent.CompletableFuture;

public class Database {
    String TAG = getClass().getSimpleName();
    DatabaseReference db;
    static Database instance = null;

    private Database() {
        db = FirebaseDatabase.getInstance().getReference();
    }

    public static Database getInstance() {
        if (instance == null) instance = new Database();
        return instance;
    }

    public <T> CompletableFuture<T> get(String cloudPath, GenericTypeIndicator<T> type) {
        CompletableFuture<T> future = new CompletableFuture<>();
        db
                .child(cloudPath).get()
                .addOnSuccessListener(task -> {
                    Log.d(TAG, "get: download data from \"" + cloudPath + "\" successfully!");
                    future.complete(task.getValue(type));
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, e.getMessage());
                    future.complete(null);
                });
        return future;
    }
}
