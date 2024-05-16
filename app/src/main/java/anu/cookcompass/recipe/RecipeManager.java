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
import anu.cookcompass.pattern.SingletonFactory;
import anu.cookcompass.pattern.Subject;
import anu.cookcompass.model.BinarySearchTree;

/**
 * Manages the loading, displaying, and updating of recipe data.
 * Implements the Subject pattern to notify observers of changes to the recipe data.
 *
 * @autor u7760022, Xinyang Li
 * @feature LoadShowData
 */
public class RecipeManager implements Subject<List<Recipe>> {
    private static final String TAG = "RecipeManager";

    // Reference to recipe data in the cloud
    private CloudData<List<Recipe>> cloudRecipesRef;

    // Binary search tree to store recipes
    private BinarySearchTree<Recipe> recipeBST = new BinarySearchTree<>();

    // List of observers to notify on recipe data changes
    private List<Observer<List<Recipe>>> observers = new ArrayList<>();

    // Currently selected recipe
    public Recipe currentRecipe = null;

    @Override
    public List<Observer<List<Recipe>>> getObservers() {
        return observers;
    }

    /**
     * Sets the current recipe and increments its view count.
     * Also updates the recipe data in the cloud.
     *
     * @param recipe the recipe to set as current
     */
    public void setCurrentRecipe(Recipe recipe) {
        currentRecipe = recipe;
        currentRecipe.view += 1;
        cloudRecipesRef.setValue(getRecipes());
    }

    /**
     * Private constructor to prevent instantiation.
     * Initializes the CloudData reference for recipes.
     */
    private RecipeManager() {
        cloudRecipesRef = new CloudData<>(
                "v2/recipe",
                new GenericTypeIndicator<List<Recipe>>() {},
                false
        );
    }

    /**
     * Returns the singleton instance of RecipeManager.
     * If the instance is null, a new instance is created.
     *
     * @return the singleton instance of RecipeManager
     */
    public static RecipeManager getInstance() {
        return SingletonFactory.getInstance(RecipeManager.class);
    }

    /**
     * Downloads recipes from the cloud and saves them into a local file.
     * Notifies observers when the download is complete.
     *
     * @param storageFile the file to save the downloaded recipes
     */
    public void downloadRecipes(File storageFile) {
        cloudRecipesRef.getOnce(recipes -> {
            recipeBST.insertAll(recipes);
            Log.d(TAG, "Download recipes successfully!");

            // Notify observers that the recipes have been downloaded and updated
            notifyAllObservers(getRecipes());

            // Save recipes to the specified local file
            Utils.saveJson(storageFile, recipes);
        });
    }

    /**
     * Loads recipes from a local file if it exists.
     * If the file does not exist, downloads the recipes from the cloud.
     *
     * @param context the context to get the local file directory
     */
    public void loadRecipes(Context context) {
        File file = new File(context.getFilesDir(), "recipe/recipe.json");

        // If there is no local file, download recipes from the cloud
        if (!file.exists()) {
            // Ensure the parent directory exists
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            // Download recipes from the cloud
            downloadRecipes(file);
            return;
        }

        // Load recipes from the local file
        List<Recipe> recipes = Utils.readJson(file, new TypeToken<List<Recipe>>() {});
        if (recipes != null) {
            recipeBST.insertAll(recipes);
            Log.d(TAG, "Load recipes from local file successfully!");
        } else {
            Log.d(TAG, "No results for recipes");
        }
    }

    /**
     * Returns the list of recipes in sorted order.
     *
     * @return the sorted list of recipes
     */
    public List<Recipe> getRecipes() {
        return recipeBST.inOrderTraversal();
    }
}
