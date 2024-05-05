package anu.cookcompass.user;


import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import anu.cookcompass.firebase.Storage;
import anu.cookcompass.model.User;

public class UserManager {
    String TAG = getClass().getSimpleName();
    static UserManager instance = null;
    public User currentUser = null;
    DatabaseReference allUsersRef;
    DatabaseReference currentUserRef;
    List<User> allUsers = new ArrayList<>();

    private UserManager() {
        // download user data from cloud
        allUsersRef = FirebaseDatabase.getInstance().getReference().child("v3/user");
    }

    public void loadUsers(){
        allUsersRef.get().addOnSuccessListener(data -> {
            List<User> users = data.getValue(new GenericTypeIndicator<List<User>>() {
            });
            for (User user : users) {
                if (user == null) continue;
                allUsers.add(user);
            }
            Log.d(TAG, allUsers.toString());
        }).addOnFailureListener(e -> Log.e(TAG, e.getMessage()));
    }

    public static UserManager getInstance() {
        if (instance == null) instance = new UserManager();
        return instance;
    }

    public void start() {
        String username = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        // if cloud doesn't have this user's data, create a new instance for it.
        User user = null;
        int last_uid = 0;
        for (User cloudUser : allUsers) {
            last_uid = cloudUser.uid;
            if (cloudUser.username.equals(username)) {
                user = cloudUser;
                break;
            }
        }

        if (user == null) {
            // create a new instance for it
            user = new User();
            user.uid = last_uid + 1;
            user.username = username;

            // push it to cloud
            allUsersRef.child(String.valueOf(user.uid)).setValue(user);
        }

        currentUserRef = allUsersRef.child(String.valueOf(user.uid));
        currentUser = user;

        // start listen on this uid
        currentUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // when it changed, update the current user data
                User user = snapshot.getValue(User.class);
                Log.d(TAG, user.toString());
                currentUser = user;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, error.getMessage());
            }
        });
    }

    /**
     * @param imageFile local image file
     * @return use thenAccept to get the download url of uploaded image
     */
    public CompletableFuture<String> uploadCurrentUserProfileImage(File imageFile) {
        CompletableFuture<String> future = new CompletableFuture<>();
        Uri file = Uri.fromFile(imageFile);

        StorageReference ref = FirebaseStorage.getInstance().getReference()
                .child("User Images")
                .child(String.valueOf(currentUser.uid))
                .child(file.getLastPathSegment());

        ref.putFile(file).addOnSuccessListener(unused -> {
            ref.getDownloadUrl().addOnSuccessListener(data -> {
                String url = data.toString();
                Log.d(TAG, "upload: upload user image successfully! " + url);

                // update user
                currentUser.imageUrl = url;

                // load user data to cloud
                currentUserRef.setValue(currentUser);

                future.complete(url);
            }).addOnFailureListener(future::completeExceptionally);
        }).addOnFailureListener(future::completeExceptionally);

        return future;
    }
}
