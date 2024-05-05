package anu.cookcompass.recipe;


import android.util.Log;

import com.google.firebase.database.GenericTypeIndicator;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.List;

import anu.cookcompass.Utils;
import anu.cookcompass.firebase.Database;
import anu.cookcompass.model.BinarySearchTree;
import anu.cookcompass.model.Global;
import anu.cookcompass.model.Recipe;


public class RecipeManager {
    String TAG = getClass().getSimpleName();
    static RecipeManager instance = null;
    BinarySearchTree<Recipe> recipeBST;

    private RecipeManager() {
        recipeBST = new BinarySearchTree<>();
        loadRecipes();
    }

    public static RecipeManager getInstance() {
        if (instance == null) instance = new RecipeManager();
        return instance;
    }

    public void loadRecipes() {
        Global global = Global.getInstance();
        File file = new File(global.filesDir, "recipe.json");
        if (!file.exists()) {
            Database.getInstance().get("v2/recipe", new GenericTypeIndicator<List<Recipe>>() {
            }).thenAccept(recipes -> {
                for (Recipe recipe : recipes) {
                    recipeBST.insert(recipe);
                }
                Log.d(TAG, "load: load recipes successfully!");
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

    public List<Recipe> getRecipes(){
        return recipeBST.inOrderTraversal();
    }
}
