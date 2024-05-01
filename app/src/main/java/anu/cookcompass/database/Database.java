package anu.cookcompass.database;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import anu.cookcompass.Utils;
import anu.cookcompass.model.BinarySearchTree;
import anu.cookcompass.model.Recipe;

public class Database {
    static Database inst = null;
    String TAG = "Database";
    DatabaseReference db;
    BinarySearchTree<Recipe> recipeBST;
    GenericTypeIndicator<List<Recipe>> recipeType;

    public static class Watcher<T> extends CompletableFuture<T> {
        private ValueEventListener listener;
        private DatabaseReference dbRef;

        public Watcher(DatabaseReference databaseReference, Class<T> type) {
            super();
            Watcher that = this;
            dbRef = databaseReference;
            listener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    T data = dataSnapshot.getValue(type);
                    that.complete(data);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w("listener", "loadPost:onCancelled", databaseError.toException());
                    that.completeExceptionally(databaseError.toException());
                }
            };
        }

        public Watcher(DatabaseReference databaseReference, GenericTypeIndicator<T> type) {
            super();
            Watcher that = this;
            dbRef = databaseReference;
            listener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    T data = dataSnapshot.getValue(type);
                    that.complete(data);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w("listener", "loadPost:onCancelled", databaseError.toException());
                    that.completeExceptionally(databaseError.toException());
                }
            };
        }

        public Watcher<T> start() {
            dbRef.addValueEventListener(listener);
            return this;
        }

        public void stop() {
            dbRef.removeEventListener(listener);
        }
    }

    private Database() {
        db = FirebaseDatabase.getInstance().getReference();
        recipeType = new GenericTypeIndicator<>() {
        };
        recipeBST = new BinarySearchTree<>();
        loadRecipes();
//        recipeChangeFuture = listen("recipe", recipeType).thenAccept(unused -> recipeBST = loadRecipes());
    }

    public void loadRecipes() {
//        File timestampFile = new File(context.getFilesDir(), "recipe_timestamp.txt");
//        File recipeFile = new File(context.getFilesDir(), "recipe.json");
//        if (!timestampFile.exists() || !recipeFile.exists()) {
//            return;
//        }
//
//        int timestamp = Integer.parseInt(Utils.readTxt(timestampFile).get(0));
//        get("recipe_timestamp", String.class);
//        getWatcher("recipe_timestamp", String.class);

        get("recipe", recipeType).thenAccept(recipes -> {
            for (Recipe recipe : recipes) {
                Log.w("test", recipe.toString());
                recipeBST.insert(recipe);
            }
        });
    }

    public List<Recipe> getRecipes() {
        return recipeBST.inOrderTraversal();
    }

    public static Database getInstance() {
        if (inst == null) inst = new Database();
        return inst;
    }

    public <T> CompletableFuture<T> get(String cloudPath, Class<T> type) {
        CompletableFuture<T> future = new CompletableFuture<>();
        db
                .child(cloudPath).get()
                .addOnSuccessListener(task -> {
                    T data = task.getValue(type);
                    Log.d(TAG, "get data successfully!");
                    future.complete(data);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, e.getMessage());
                    future.complete(null);
                });
        return future;
    }

    public <T> CompletableFuture<T> get(String cloudPath, GenericTypeIndicator<T> type) {
        CompletableFuture<T> future = new CompletableFuture<>();
        db
                .child(cloudPath).get()
                .addOnSuccessListener(task -> {
                    T data = task.getValue(type);
                    Log.d(TAG, "get data successfully!");
                    future.complete(data);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, e.getMessage());
                    future.complete(null);
                });
        return future;
    }

    public <T> Watcher<T> getWatcher(String cloudPath, Class<T> type) {
        return new Watcher<>(db.child(cloudPath), type);
    }

    public <T> Watcher<T> getWatcher(String cloudPath, GenericTypeIndicator<T> type) {
        return new Watcher<>(db.child(cloudPath), type);
    }
}
