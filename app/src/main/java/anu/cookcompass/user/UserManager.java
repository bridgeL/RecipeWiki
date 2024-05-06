package anu.cookcompass.user;


import android.net.Uri;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import anu.cookcompass.firebase.CloudData;
import anu.cookcompass.pattern.Observer;
import anu.cookcompass.pattern.Subject;
import anu.cookcompass.model.User;

public class UserManager implements Subject<User> {
    String TAG = "UserManager";
    static UserManager instance = null;
    User user = new User();
    CloudData<User> cloudUser;
    List<Observer<User>> observers = new ArrayList<>();

    @Override
    public List<Observer<User>> getObservers() {
        return observers;
    }

    private UserManager() {
    }

    public static UserManager getInstance() {
        if (instance == null) instance = new UserManager();
        return instance;
    }

    /**
     * when user first registered an account, create a user data instance on cloud
     */
    public void createUserData(FirebaseUser firebaseUser) {
        String uid = firebaseUser.getUid();
        String username = firebaseUser.getEmail();

        User user = new User();
        user.uid = uid;
        user.username = username;
        getCloudUser(uid).upload(user);
    }

    /**
     * by calling this function to avoid create cloudUser multiple times
     * @param uid user uid
     * @return cloudUser
     */
    public CloudData<User> getCloudUser(String uid) {
        if (cloudUser == null) {
            cloudUser = new CloudData<>(
                    "v3/user/" + uid,
                    new GenericTypeIndicator<User>() {
                    }
            );
        }
        return cloudUser;
    }

    /**
     * afer user has logged in successfully, call this function to load user data from cloud
     */
    public void downloadUserData(String uid) {
        getCloudUser(uid).addObserver(data -> {
            // user is totally same to cloudUser, there is no need to synchronize
            if (user.equals(data)) return;

            // synchronize data
            user = data;
            notifyAllObservers(user);
            Log.d(TAG, "synchronize user data successfully!");
        });
    }

    /**
     * @param imageFile local image file
     * @return use thenAccept to get the download url of uploaded image
     */
    public void uploadProfileImage(File imageFile) {
        Uri file = Uri.fromFile(imageFile);

        StorageReference imageRef = FirebaseStorage.getInstance().getReference()
                .child("User Images")
                .child(user.uid)
                .child(file.getLastPathSegment());

        imageRef.putFile(file).addOnSuccessListener(unused -> {
            imageRef.getDownloadUrl().addOnSuccessListener(data -> {
                // extract url
                String url = data.toString();
                Log.d(TAG, "upload user image successfully! " + url);
                // set user image url
                user.imageUrl = url;
                // upload user data to cloud
                cloudUser.upload(user);
            }).addOnFailureListener(e -> Log.e(TAG, e.getMessage()));
        }).addOnFailureListener(e -> Log.e(TAG, e.getMessage()));
    }
}
