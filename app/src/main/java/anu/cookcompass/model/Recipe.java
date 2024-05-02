package anu.cookcompass.model;

import androidx.annotation.NonNull;

public class Recipe implements Comparable<Recipe> {
    public int id;
    public String title;
    public String imageName;
    public int view;
    public int like;
    public int collect;

    @Override
    public int compareTo(Recipe o) {
        return id - o.id;
    }

    @NonNull
    @Override
    public String toString() {
        return "[id: " + id + "] " + title + " (" + imageName + ")";
    }
}
