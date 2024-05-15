package anu.cookcompass.user;

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
        return Utils.toJson(this);
    }
}
