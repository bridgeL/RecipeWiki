package anu.cookcompass.model;

import androidx.annotation.NonNull;

import java.util.List;

public class Recipe implements Comparable<Recipe> {
    public int rid;
    public String title;
    public String imageName;
    public List<String> ingredients;
    public List<String> instructions;
    public int view;
    public int like;

    @Override
    public int compareTo(Recipe o) {
        return rid - o.rid;
    }

    @NonNull
    @Override
    public String toString() {
        return "[rid: " + rid + "] " + title + " (" + imageName + ")";
    }
}
