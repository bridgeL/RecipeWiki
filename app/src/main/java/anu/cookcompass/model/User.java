package anu.cookcompass.model;

import androidx.annotation.NonNull;

import java.util.List;

public class User {
    public int uid;
    public String username;
    public String imageUrl;
    public List<Integer> collections;

    @NonNull
    @Override
    public String toString() {
        return "[uid: " + uid + "] " + username;
    }
}
