package anu.cookcompass.recipe;

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

    //Constructor added for SearchFilter testing purposes
    public Recipe(int rid, String title, int view, int like) {
        this.rid = rid;
        this.title = title;
        this.view = view;
        this.like = like;
    }
    //End of addition

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
