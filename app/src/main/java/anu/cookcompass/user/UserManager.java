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
import anu.cookcompass.popmsg.PopMsg;
import anu.cookcompass.popmsg.PopMsgType;
import anu.cookcompass.recipe.Recipe;
import anu.cookcompass.pattern.Observer;
import anu.cookcompass.pattern.Subject;
import anu.cookcompass.popmsg.PopMsgManager;

public class UserManager implements Subject<User> {
    String TAG = "UserManager";
    static UserManager instance = null;
    public User user = new User();
    public CloudData<User> cloudUser;
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
     * toggle current user's like on given recipe, return the like status now
     *
     * @param recipe   recipe
     * @param location location
     * @return like flag
     */
    public boolean toggleLike(Recipe recipe, String location) {
        PopMsg popMsg = new PopMsg();
        popMsg.uid = user.uid;
        popMsg.username = user.username;
        popMsg.rid = recipe.rid;
        popMsg.title = recipe.title;
        popMsg.location = location;

        Integer rid = (Integer) recipe.rid;
        boolean like;

        if (user.likes.contains(rid)) {
            user.likes.remove(rid);
            popMsg.type = PopMsgType.UNLIKE;
            recipe.like -= 1;
            like = false;
        } else {
            user.likes.add(rid);
            popMsg.type = PopMsgType.LIKE;
            recipe.like += 1;
            like = true;
        }

        uploadUser();
        PopMsgManager.getInstance().pushPopMsg(popMsg);

        return like;
    }

    /**
     * when user first registered an account, create a user data instance on cloud
     */
    public void createUserData(FirebaseUser firebaseUser) {
        String uid = firebaseUser.getUid();
        String username = firebaseUser.getEmail();

//        user = new User();
        user.uid = uid;
        user.username = username;
        initCloudUser(uid);
        uploadUser();
    }

    /**
     * upload current user to cloud
     */
    public void uploadUser() {
        cloudUser.setValue(user);
    }

    /**
     * by calling this function to avoid create cloudUser multiple times
     *
     * @param uid user uid
     * @return cloudUser
     */
    public void initCloudUser(String uid) {
        if (cloudUser == null) {
            cloudUser = new CloudData<>(
                    "v3/user/" + uid,
                    new GenericTypeIndicator<User>() {
                    }
            );

            cloudUser.addObserver(data -> {
                // user is totally same to cloudUser, there is no need to synchronize
                if (user.equals(data)) return;

                // synchronize data
                user = data;
                Log.d(TAG, "synchronize user data successfully!");
                notifyAllObservers(user);
            });
        }
        else{
            cloudUser.stopListen();
            cloudUser = null;
            initCloudUser(uid);
        }
    }

    /**
     * @param file local image file uri
     * @return use thenAccept to get the download url of uploaded image
     */
    public void uploadProfileImage(Uri file) {
        String fullPath = file.getLastPathSegment();
        String fileName = fullPath.substring(fullPath.lastIndexOf('/') + 1);

        StorageReference imageRef = FirebaseStorage.getInstance().getReference()
                .child("User Images")
                .child(user.uid)
                .child(fileName);

        imageRef.putFile(file).addOnSuccessListener(unused -> {
            imageRef.getDownloadUrl().addOnSuccessListener(data -> {
                // extract url
                String url = data.toString();
                Log.d(TAG, "upload user image successfully! " + url);
                // set user image url
                user.imageUrl = url;
                // upload user data to cloud
                uploadUser();
            }).addOnFailureListener(e -> Log.e(TAG, e.getMessage()));
        }).addOnFailureListener(e -> Log.e(TAG, e.getMessage()));
    }
}