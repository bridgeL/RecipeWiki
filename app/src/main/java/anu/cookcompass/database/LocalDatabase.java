package anu.cookcompass.database;

import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import anu.cookcompass.Utils;
import anu.cookcompass.model.Recipe;
import anu.cookcompass.model.User;

public class LocalDatabase {
    List<Recipe> recipes;
    List<User> users;
    transient File recipeFile;
    transient File userFile;

    public LocalDatabase(File recipeFile, File userFile) {
        // load recipes
        recipes = Utils.readJson(recipeFile, new TypeToken<List<Recipe>>() {
        });
        this.recipeFile = recipeFile;

        // load users
        users = Utils.readJson(userFile, new TypeToken<List<User>>() {
        });
        this.userFile = userFile;
    }

    public Recipe getRecipeById(int id){
        for (Recipe recipe : recipes) {
            if(recipe.id == id) return recipe;
        }
        return null;
    }

    public User getUserByUsername(String username){
        for (User user : users) {
            if(user.username.equals(username)) return user;
        }
        return null;
    }

    //New method for adding user to user list in database (May be removed if incorrect)
    public void addUser(User newUser) {
        users.add(newUser);
        save();
    }

    public void save(){
        Utils.saveJson(recipeFile, recipes);
        Utils.saveJson(userFile, users);
    }

    public List<Recipe> searchRecipes(String searchString){
        // 1. use tokenizer get tokens

        // 2. use tokens search ingredients

        // 3. return ingredients
        return easySearchRecipes(new String[]{"apple", "cream"}, new String[]{"bread", "pineapple"});
    }

    public List<Recipe> easySearchRecipes(String[] includes, String[] excludes){
        for (int i = 0; i < includes.length; i++) {
            includes[i] = includes[i].toLowerCase();
        }
        for (int i = 0; i < excludes.length; i++) {
            excludes[i] = excludes[i].toLowerCase();
        }

        return recipes.stream().filter(e->{
            String title = e.title.toLowerCase();
            for (String include : includes) {
                if(!title.contains(include)) return false;
            }
            for (String exclude : excludes) {
                if(title.contains(exclude)) return false;
            }
            return true;
        }).collect(Collectors.toList());
    }
}
