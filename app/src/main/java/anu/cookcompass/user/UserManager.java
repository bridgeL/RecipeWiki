package anu.cookcompass.user;


import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.GenericTypeIndicator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import anu.cookcompass.firebase.Database;
import anu.cookcompass.firebase.DatabaseWatcher;
import anu.cookcompass.firebase.Observer;
import anu.cookcompass.firebase.Storage;
import anu.cookcompass.model.User;

public class UserManager implements Observer {
    String TAG = getClass().getSimpleName();
    static UserManager instance = null;
    List<User> users;
    DatabaseWatcher watcher;
    User currentUser = null;

    private UserManager() {
        users = new ArrayList<>();
        watcher = new DatabaseWatcher("v2/user", this);
    }

    public void updateAllUsers() {
        Database.getInstance().get("v2/user", new GenericTypeIndicator<List<User>>() {
        }).thenAccept(cloudUsers -> {
            users.clear();
            for (User user : cloudUsers) {
                if (user != null) users.add(user);
            }
            Log.d(TAG, "load: load users successfully!");
            Log.d(TAG, users.toString());
        });
    }

    @Override
    public void notify(DataSnapshot dataSnapshot) {
        updateAllUsers();
    }

    public static UserManager getInstance() {
        if (instance == null) instance = new UserManager();
        return instance;
    }

    public User getUser(String username) {
        for (User user : users) {
            if (user.username.equals(username)) return user;
        }
        return null;
    }

    public void setCurrentUser(String username) {
        User user = getUser(username);

        // if not exists, create new one and upload to cloud
        if (user == null) user = new User();
        user.uid = users.get(users.size() - 1).uid + 1;
        user.username = username;

        // set current user
        currentUser = user;

        // upload
        watcher.ref.child(String.valueOf(currentUser.uid)).setValue(currentUser);
    }

    public CompletableFuture<String> uploadUserProfileImage(File file) {
        return Storage.getInstance()
                .uploadFile("User Images/" + currentUser.uid, file)
                .thenApply(url -> {
                    Log.d(TAG, "upload: upload user image successfully! " + url);
                    currentUser.imageUrl = url;

                    // upload
                    watcher.ref.child(String.valueOf(currentUser.uid)).setValue(currentUser);

                    // return
                    return url;
                });
    }
}
