package anu.cookcompass.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import anu.cookcompass.Utils;

public class User {
    public String uid = "";
    public String username = "";
    public String imageUrl = "";
    public List<Integer> likes = new ArrayList<>();

    @NonNull
    @Override
    public String toString() {
        return "[uid: " + uid + "] " + username + " likes:" + likes.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;  // Check if the references point to the same object
        if (o == null || getClass() != o.getClass())
            return false;  // Check for null and ensure exact class match

        User user = (User) o;  // Type casting

        // Compare all fields
        if(!uid.equals(user.uid)) return false;
        if(!username.equals(user.username)) return false;
        if(!imageUrl.equals(user.imageUrl)) return false;
        return Utils.ArraysEqual(likes, user.likes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, username, imageUrl, likes);  // Generate hash code
    }
}
