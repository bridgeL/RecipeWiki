package anu.cookcompass.search;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import anu.cookcompass.Utils;
import anu.cookcompass.recipe.Recipe;
import anu.cookcompass.recipe.RecipeManager;

public class SearchService {
    static SearchService instance = null;
    List<Recipe> lastRecipes = new ArrayList<>();

    private SearchService() {
    }

    public static SearchService getInstance() {
        if (instance == null) instance = new SearchService();
        return instance;
    }

    static QueryObject parseQuery(String query) {
        Tokenizer tokenizer = new Tokenizer(query);
        Parser parser = new Parser(tokenizer);
        return parser.parseQuery();
    }

    public List<Recipe> search(Context context, String query) {
        lastRecipes = innerSearch(context, query);
        return lastRecipes;
    }

    public List<Recipe> innerSearch(Context context, String query) {
        List<Recipe> recipes = RecipeManager.getInstance().getRecipes();

        // if query is empty, return all data
        if (query.isEmpty()) {
            Utils.showShortToast(context, "get " + recipes.size() + " results");
            return recipes;
        }

        // feature: search invalid
        if (!query.contains(";")) query = query + ";";
        QueryObject temp = parseQuery(query);

        if (temp.queryInvalid) {
            query = "title=" + query;
            temp = parseQuery(query);
        }

        QueryObject queryObject = temp;

        // if still invalid, show error message
        if (queryObject.queryInvalid) {
            Utils.showShortToast(context, queryObject.errorMessage);
            Log.e("query", queryObject.errorMessage);
            return lastRecipes;
        }

        // get search results
        List<Recipe> searchResults = recipes.stream().filter(r -> {
            // search title
            for (String keyword : queryObject.title_keywords) {
                if (!r.title.contains(keyword)) return false;
            }

            // search ingredients
            String ingredientsString = String.join(" ", r.ingredients);
            for (String keyword : queryObject.ingredient_keywords) {
                if (!ingredientsString.contains(keyword)) return false;
            }

            // like range limit
            int a = queryObject.like_range[0];
            int b = queryObject.like_range[1];
            if (a < 0) a = 0;
            if (b < 0) b = Integer.MAX_VALUE;
            return r.like > a && r.like < b;
        }).collect(Collectors.toList());

        // show number of results
        Utils.showShortToast(context, "get " + searchResults.size() + " results");

        return searchResults;
    }
}
