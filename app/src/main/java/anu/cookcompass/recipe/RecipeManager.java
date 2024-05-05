package anu.cookcompass.recipe;


import android.content.Context;
import android.util.Log;

import com.google.firebase.database.GenericTypeIndicator;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import anu.cookcompass.Utils;
import anu.cookcompass.firebase.Database;
import anu.cookcompass.firebase.Listener;
import anu.cookcompass.firebase.Subject;
import anu.cookcompass.model.BinarySearchTree;
import anu.cookcompass.model.Recipe;


public class RecipeManager implements Subject<List<Recipe>> {
    String TAG = getClass().getSimpleName();
    static RecipeManager instance = null;
    BinarySearchTree<Recipe> recipeBST = new BinarySearchTree<>();
    List<Listener<List<Recipe>>> listeners = new ArrayList<>();

    @Override
    public List<Listener<List<Recipe>>> getListeners() {
        return listeners;
    }

    private RecipeManager() {
    }

    public static RecipeManager getInstance() {
        if (instance == null) instance = new RecipeManager();
        return instance;
    }

    public void loadRecipes(Context context) {
        File file = new File(context.getFilesDir(), "recipe.json");
        if (!file.exists()) {
            Database.getInstance().get("v2/recipe", new GenericTypeIndicator<List<Recipe>>() {
            }).thenAccept(recipes -> {
                for (Recipe recipe : recipes) {
                    recipeBST.insert(recipe);
                }
                Log.d(TAG, "load: load recipes successfully!");
                notifyAllListeners(getRecipes());
                Utils.saveJson(file, recipes);
            });
        } else {
            List<Recipe> recipes = Utils.readJson(file, new TypeToken<List<Recipe>>() {
            });
            for (Recipe recipe : recipes) {
                recipeBST.insert(recipe);
            }
            Log.d(TAG, "load: load recipes successfully!");
        }
    }

    public List<Recipe> getRecipes() {
        return recipeBST.inOrderTraversal();
    }
}
