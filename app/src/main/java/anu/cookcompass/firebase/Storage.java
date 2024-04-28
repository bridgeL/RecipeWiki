package anu.cookcompass.firebase;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

import anu.cookcompass.model.Response;

public class Storage {
    public static CompletableFuture<Response> uploadBytes(String cloudPath, byte[] data) {
        CompletableFuture<Response> future = new CompletableFuture<>();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference ref = storage.getReference().child(cloudPath);
        UploadTask uploadTask = ref.putBytes(data);
        uploadTask.addOnFailureListener(exception -> {
            future.complete(new Response(false, exception.getMessage()));
        }).addOnSuccessListener(taskSnapshot -> {
            future.complete(new Response(true, "upload successfully!"));
        });
        return future;
    }

    public static CompletableFuture<Response> uploadStream(String cloudPath, InputStream stream) {
        CompletableFuture<Response> future = new CompletableFuture<>();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference ref = storage.getReference().child(cloudPath);
        UploadTask uploadTask = ref.putStream(stream);
        uploadTask.addOnFailureListener(exception -> {
            future.complete(new Response(false, exception.getMessage()));
        }).addOnSuccessListener(taskSnapshot -> {
            future.complete(new Response(true, "upload successfully!"));
        });
        return future;
    }
}
