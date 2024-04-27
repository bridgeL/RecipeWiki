package anu.cookcompass.firebase;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

public class Storage {
    FirebaseStorage storage;

    public Storage() {
        storage = FirebaseStorage.getInstance();
    }

    public CompletableFuture<String> uploadBytes(String cloudPath, byte[] data) {
        // async task
        CompletableFuture<String> future = new CompletableFuture<>();

        // cloud storage ref
        StorageReference ref = storage.getReference().child(cloudPath);

        // start an async task
        UploadTask uploadTask = ref.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                future.completeExceptionally(exception);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                future.complete("upload successfully!");
            }
        });

        return future;
    }

    public CompletableFuture<String> uploadStream(String cloudPath, InputStream stream) {
        // async task
        CompletableFuture<String> future = new CompletableFuture<>();

        // cloud storage ref
        StorageReference ref = storage.getReference().child(cloudPath);

        // start an async task
        UploadTask uploadTask = ref.putStream(stream);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                future.completeExceptionally(exception);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                future.complete("upload successfully!");
            }
        });
        return future;
    }
}
