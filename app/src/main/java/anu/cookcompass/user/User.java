package anu.cookcompass.user;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import anu.cookcompass.Utils;

/**
 * @author u7760022, Xinyang Li
 * The class is User
 */
public class User {
    public String uid = "";
    public String username = "";
    public String imageUrl = "";
    public List<Integer> likes = new ArrayList<>();

    @NonNull
    @Override
    public String toString() {
        return Utils.toJson(this);
    }
}
