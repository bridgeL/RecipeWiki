package anu.cookcompass.database;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import anu.cookcompass.Utils;
import anu.cookcompass.model.Ingredient;
import anu.cookcompass.model.User;

public class LocalDatabase {
    List<Ingredient> ingredients;
    List<User> users;
    transient File ingredientFile;
    transient File userFile;

    public LocalDatabase(File ingredientFile, File userFile) {
        // load ingredients
        ingredients = Utils.readJson(ingredientFile, new TypeToken<List<Ingredient>>() {
        });
        this.ingredientFile = ingredientFile;

        // load users
        users = Utils.readJson(userFile, new TypeToken<List<User>>() {
        });
        this.userFile = userFile;
    }

    public Ingredient getIngredientById(int id){
        for (Ingredient ingredient : ingredients) {
            if(ingredient.id == id) return ingredient;
        }
        return null;
    }

    public User getUserByUsername(String username){
        for (User user : users) {
            if(user.username.equals(username)) return user;
        }
        return null;
    }

    public void save(){
        Utils.saveJson(ingredientFile, ingredients);
        Utils.saveJson(userFile, users);
    }

    public List<Ingredient> searchIngredients(String searchString){
        // 1. use tokenizer get tokens

        // 2. use tokens search ingredients

        // 3. return ingredients
        return easySearchIngredients(new String[]{"apple", "cream"}, new String[]{"bread", "pineapple"});
    }

    public List<Ingredient> easySearchIngredients(String[] includes, String[] excludes){
        for (int i = 0; i < includes.length; i++) {
            includes[i] = includes[i].toLowerCase();
        }
        for (int i = 0; i < excludes.length; i++) {
            excludes[i] = excludes[i].toLowerCase();
        }

        return ingredients.stream().filter(e->{
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
