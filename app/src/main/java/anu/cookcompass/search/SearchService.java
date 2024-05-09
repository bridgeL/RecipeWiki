package anu.cookcompass.search;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import anu.cookcompass.Utils;
import anu.cookcompass.pattern.Observer;
import anu.cookcompass.pattern.SingletonFactory;
import anu.cookcompass.pattern.Subject;
import anu.cookcompass.recipe.Recipe;
import anu.cookcompass.recipe.RecipeManager;

public class SearchService implements Subject<List<Recipe>> {
    List<Recipe> lastRecipes = new ArrayList<>();
    List<Observer<List<Recipe>>> observers = new ArrayList<>();
    public String query = "";
    public String sortType = "id";

    @Override
    public List<Observer<List<Recipe>>> getObservers() {
        return observers;
    }

    private SearchService() {
    }

    public static SearchService getInstance() {
        return SingletonFactory.getInstance(SearchService.class);
    }

    static QueryObject parseQuery(String query) {
        Tokenizer tokenizer = new Tokenizer(query);
        Parser parser = new Parser(tokenizer);
        return parser.parseQuery();
    }

    public void search(Context context) {
        // search
        List<Recipe> recipes = innerSearch(context, query);
        if (recipes == null) {
            notifyAllObservers(lastRecipes);
            return;
        }

        // sort
        Recipe[] recipes2 = new Recipe[recipes.size()];
        for (int i = 0; i < recipes.size(); i++) {
            recipes2[i] = recipes.get(i);
        }
        SearchFilter.heapSortByName(recipes2, sortType);
        recipes.clear();
        recipes.addAll(Arrays.asList(recipes2));
        notifyAllObservers(recipes);
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
            return null;
        }

        // get search results
        List<Recipe> searchResults = recipes.stream().filter(r -> {
            // search title
            for (String keyword : queryObject.title_keywords) {
                if (!r.title.toLowerCase().contains(keyword.toLowerCase())) return false;
            }

            // search ingredients
            String ingredientsString = String.join(" ", r.ingredients).toLowerCase();
            for (String keyword : queryObject.ingredient_keywords) {
                if (!ingredientsString.contains(keyword.toLowerCase())) return false;
            }

            // like range limit
            int a = queryObject.like_range[0];
            int b = queryObject.like_range[1];
            if (a < 0) a = 0;
            if (b < 0) b = Integer.MAX_VALUE;
            if (r.like <= a || r.like >= b) return false;

            // view range limit
            a = queryObject.view_range[0];
            b = queryObject.view_range[1];
            if (a < 0) a = 0;
            if (b < 0) b = Integer.MAX_VALUE;
            return r.view > a && r.view < b;
        }).collect(Collectors.toList());

        // show number of results
        Utils.showShortToast(context, "get " + searchResults.size() + " results");

        return searchResults;
    }


}
