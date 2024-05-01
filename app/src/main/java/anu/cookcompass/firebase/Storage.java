package anu.cookcompass.firebase;

import android.util.Log;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask.TaskSnapshot;

import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

import anu.cookcompass.model.Response;

public class Storage {
    static StorageReference ref = FirebaseStorage.getInstance().getReference();


    public static CompletableFuture<TaskSnapshot> uploadBytes(String cloudPath, byte[] data) {
        CompletableFuture<TaskSnapshot> future = new CompletableFuture<>();
        ref
                .child(cloudPath)
                .putBytes(data)
                .addOnSuccessListener(future::complete)
                .addOnFailureListener(future::completeExceptionally);
        return future;
    }

    public static CompletableFuture<TaskSnapshot> uploadStream(String cloudPath, InputStream stream) {
        CompletableFuture<TaskSnapshot> future = new CompletableFuture<>();
        ref
                .child(cloudPath)
                .putStream(stream)
                .addOnSuccessListener(future::complete)
                .addOnFailureListener(future::completeExceptionally);
        return future;
    }

    public static CompletableFuture<byte[]> downloadBytes(String cloudPath) {
        CompletableFuture<byte[]> future = new CompletableFuture<>();
        ref
                .child(cloudPath)
                .getBytes(1024*1024)
                .addOnSuccessListener(future::complete)
                .addOnFailureListener(future::completeExceptionally);
        return future;
    }
}
