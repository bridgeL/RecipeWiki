package anu.cookcompass.recipe;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author u7760022, Xinyang Li
 * @feature LoadShowData
 * The class is Recipe
 */
public class Recipe implements Comparable<Recipe> {
    public int rid;
    public String title;
    public String imageName = "";
    public List<String> ingredients = new ArrayList<>();
    public List<String> instructions = new ArrayList<>();
    public int view;
    public int like;

    public Recipe() {
        this(0, "title", 0, 0);
    }

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
