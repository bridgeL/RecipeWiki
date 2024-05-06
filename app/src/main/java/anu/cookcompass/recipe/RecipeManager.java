package anu.cookcompass.recipe;


import android.content.Context;
import android.util.Log;

import com.google.firebase.database.GenericTypeIndicator;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import anu.cookcompass.Utils;
import anu.cookcompass.firebase.CloudData;
import anu.cookcompass.pattern.Observer;
import anu.cookcompass.pattern.Subject;
import anu.cookcompass.model.BinarySearchTree;
import anu.cookcompass.model.Recipe;


public class RecipeManager implements Subject<List<Recipe>> {
    String TAG = getClass().getSimpleName();
    static RecipeManager instance = null;
    CloudData<List<Recipe>> cloudRecipesRef;
    BinarySearchTree<Recipe> recipeBST = new BinarySearchTree<>();
    List<Observer<List<Recipe>>> observers = new ArrayList<>();

    @Override
    public List<Observer<List<Recipe>>> getObservers() {
        return observers;
    }

    private RecipeManager() {
        cloudRecipesRef = new CloudData<>(
                "v2/recipe",
                new GenericTypeIndicator<List<Recipe>>() {
                },
                false
        );
    }

    public static RecipeManager getInstance() {
        if (instance == null) instance = new RecipeManager();
        return instance;
    }

    /**
     * download recipes from cloud and save it into a local file,
     * so next time the app don't need to re-download it, and save the bandwidth
     *
     * @param storageFile data will be stored in this file
     */
    public void downloadRecipes(File storageFile) {
        cloudRecipesRef.getOnce(recipes -> {
            recipeBST.insertAll(recipes);
            Log.d(TAG, "download recipes successfully!");

            // because its an async function and will cost time,
            // when download completes, it should notify other modules (like front-end viewer) to update their status
            notifyAllObservers(getRecipes());

            // save it into given file
            Utils.saveJson(storageFile, recipes);
        });
    }

    public void loadRecipes(Context context) {
        File file = new File(context.getFilesDir(), "recipe.json");

        // if there is not a local file
        if (!file.exists()) {
            // make sure dirs exist
            file.mkdirs();

            // download recipes from cloud
            downloadRecipes(file);
            return;
        }

        // load it from local file
        List<Recipe> recipes = Utils.readJson(file, new TypeToken<List<Recipe>>() {
        });
        recipeBST.insertAll(recipes);
        Log.d(TAG, "load recipes from local file successfully!");
    }

    public List<Recipe> getRecipes() {
        return recipeBST.inOrderTraversal();
    }
}
