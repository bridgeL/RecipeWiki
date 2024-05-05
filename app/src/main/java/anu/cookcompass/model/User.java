package anu.cookcompass.model;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;

public class User {
    public String uid;
    public String username;
    public String imageUrl;
    public List<Integer> collections;

    @NonNull
    @Override
    public String toString() {
        return "[uid: " + uid + "] " + username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;  // Check if the references point to the same object
        if (o == null || getClass() != o.getClass())
            return false;  // Check for null and ensure exact class match

        User user = (User) o;  // Type casting

        // Compare all fields
        if (!Objects.equals(uid, user.uid)) return false;  // Compare uid
        if (!Objects.equals(username, user.username)) return false;  // Compare username
        if (!Objects.equals(imageUrl, user.imageUrl)) return false;  // Compare imageUrl
        return Objects.equals(collections, user.collections);  // Compare collections list
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, username, imageUrl, collections);  // Generate hash code
    }
}
