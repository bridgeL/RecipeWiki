package anu.cookcompass.firebase;

import android.net.Uri;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask.TaskSnapshot;

import java.io.File;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;


public class Storage {
    String TAG = getClass().getSimpleName();
    StorageReference ref;
    static Storage instance = null;

    private Storage() {
        ref = FirebaseStorage.getInstance().getReference();
    }

    public static Storage getInstance() {
        if (instance == null) instance = new Storage();
        return instance;
    }

    public CompletableFuture<String> uploadFile(String cloudParentPath, File localFile) {
        CompletableFuture<String> future = new CompletableFuture<>();
        Uri file = Uri.fromFile(localFile);

        ref.child(cloudParentPath).child(file.getLastPathSegment()).putFile(file)
                .addOnSuccessListener(taskSnapshot -> {
                    taskSnapshot.getStorage().getDownloadUrl()
                            .addOnSuccessListener(uri -> future.complete(uri.toString()))
                            .addOnFailureListener(future::completeExceptionally);
                })
                .addOnFailureListener(future::completeExceptionally);

        return future;
    }
}
