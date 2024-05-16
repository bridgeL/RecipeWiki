package anu.cookcompass.user;

import android.net.Uri;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import anu.cookcompass.Utils;
import anu.cookcompass.firebase.CloudData;
import anu.cookcompass.pattern.SingletonFactory;
import anu.cookcompass.popmsg.PopMsg;
import anu.cookcompass.popmsg.PopMsgType;
import anu.cookcompass.recipe.Recipe;
import anu.cookcompass.pattern.Observer;
import anu.cookcompass.pattern.Subject;
import anu.cookcompass.popmsg.PopMsgManager;

/**
 * Manages user-related operations such as liking recipes, uploading user data to the cloud,
 * and handling user profile images.
 *
 * Implements the Subject pattern to notify observers of changes to user data.
 *
 * @autor u7760022, Xinyang Li
 */
public class UserManager implements Subject<User> {
    private static final String TAG = "UserManager";

    // Singleton instance of UserManager
    private static UserManager instance;

    // Current user object
    public User user = new User();

    // CloudData instance for managing user data in the cloud
    private CloudData<User> cloudUser;

    // List of observers to notify on user data changes
    private List<Observer<User>> observers = new ArrayList<>();

    /**
     * Private constructor to prevent instantiation.
     */
    private UserManager() {
    }

    /**
     * Returns the singleton instance of UserManager.
     * If the instance is null, a new instance is created.
     *
     * @return the singleton instance of UserManager
     */
    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    @Override
    public List<Observer<User>> getObservers() {
        return observers;
    }

    /**
     * Checks if the current user has liked the given recipe.
     *
     * @param recipe the recipe to check
     * @return true if the user has liked the recipe, false otherwise
     */
    public boolean hasLiked(Recipe recipe) {
        int rid = recipe.rid;
        return user.likes.contains(rid);
    }

    /**
     * Toggles the current user's like status on the given recipe and returns the updated like status.
     * Also uploads the user data and sends a notification message.
     *
     * @param recipe the recipe to toggle like status on
     * @param location the location associated with the action
     * @return true if the recipe is now liked, false if it is now unliked
     */
    public boolean toggleLike(Recipe recipe, String location) {
        Integer rid = recipe.rid;
        boolean like;
        PopMsgType type;

        if (user.likes.contains(rid)) {
            user.likes.remove(rid);
            type = PopMsgType.UNLIKE;
            recipe.like -= 1;
            like = false;
        } else {
            user.likes.add(rid);
            type = PopMsgType.LIKE;
            recipe.like += 1;
            like = true;
        }
        uploadUser();

        PopMsg popMsg = new PopMsg(
                user.uid,
                user.username,
                recipe.rid,
                recipe.title,
                location,
                type,
                Utils.getTimestamp()
        );
        PopMsgManager.getInstance().pushPopMsg(popMsg);

        return like;
    }

    /**
     * Creates a user data instance on the cloud when a user first registers an account.
     *
     * @param firebaseUser the FirebaseUser object representing the registered user
     */
    public void createUserData(FirebaseUser firebaseUser) {
        String uid = firebaseUser.getUid();
        String username = firebaseUser.getEmail();

        user.uid = uid;
        user.username = username;
        initCloudUser(uid);
        uploadUser();
    }

    /**
     * Uploads the current user data to the cloud.
     */
    public void uploadUser() {
        cloudUser.setValue(user);
    }

    /**
     * Initializes the CloudData object for the user to avoid creating it multiple times.
     *
     * @param uid the user ID
     */
    public void initCloudUser(String uid) {
        if (cloudUser == null) {
            cloudUser = new CloudData<>(
                    "v3/user/" + uid,
                    new GenericTypeIndicator<User>() {}
            );

            cloudUser.addObserver(data -> {
                // Synchronize user data
                user = data;
                Log.d(TAG, "Synchronize user data successfully!");
                notifyAllObservers(user);
            });
        } else {
            cloudUser.stopListen();
            cloudUser = null;
            initCloudUser(uid);
        }
    }

    /**
     * Uploads a profile image to the cloud and updates the user's image URL.
     *
     * @param file the local URI of the image file
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
                // Extract URL
                String url = data.toString();
                Log.d(TAG, "Upload user image successfully! " + url);
                // Set user image URL
                user.imageUrl = url;
                // Upload user data to cloud
                uploadUser();
            }).addOnFailureListener(e -> Log.e(TAG, e.getMessage()));
        }).addOnFailureListener(e -> Log.e(TAG, e.getMessage()));
    }
}
