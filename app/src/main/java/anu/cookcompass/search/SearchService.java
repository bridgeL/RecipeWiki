package anu.cookcompass.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import anu.cookcompass.Utils;
import anu.cookcompass.pattern.Observer;
import anu.cookcompass.pattern.Subject;
import anu.cookcompass.recipe.Recipe;
import anu.cookcompass.recipe.RecipeManager;
import anu.cookcompass.searchfilter.SearchFilter;

/**
 * @author u7760022, Xinyang Li
 * @feature Search-invalid
 * The class is Facade (design pattern), combining basic search, search-invalid, and search-filter into one service point.
 */
public class SearchService implements Subject<List<Recipe>> {
    // List of the last searched recipes
    List<Recipe> lastRecipes = new ArrayList<>();
    // List of observers to be notified on search updates
    List<Observer<List<Recipe>>> observers = new ArrayList<>();
    // Query string for searching recipes
    public String query = "";
    // Type of sorting to be applied
    public String sortType = "id";
    // Order of sorting (descending by default)
    public boolean isDescending = true;
    // Singleton instance of SearchService
    static SearchService inst = null;

    @Override
    public List<Observer<List<Recipe>>> getObservers() {
        return observers;
    }

    // Private constructor for Singleton pattern
    private SearchService() {
    }

    // Method to get the Singleton instance of SearchService
    public static SearchService getInstance() {
        if (inst == null) inst = new SearchService();
        return inst;
    }

    // Method to parse the search query
    static QueryObject parseQuery(String query) {
        Tokenizer tokenizer = new Tokenizer(query);
        Parser parser = new Parser(tokenizer);
        return parser.parseQuery();
    }

    // Method to perform search and notify observers with the results
    public void searchAndShow() {
        List<Recipe> recipes = search(query, sortType, isDescending, RecipeManager.getInstance().getRecipes());
        notifyAllObservers(recipes);
    }

    // Method to perform search based on query, sort type, and order
    public List<Recipe> search(String query, String sortType, boolean isDescending, List<Recipe> recipes) {
        // Perform search based on the query
        List<Recipe> recipes1 = searchByInformalQuery(query, recipes, lastRecipes);

        // Sort the search results
        List<Recipe> recipes2 = recipeSort(sortType, isDescending, recipes1);

        // Store the sorted results
        lastRecipes = recipes2;

        return recipes2;
    }

    // Method to sort the recipes based on sort type and order
    public List<Recipe> recipeSort(String sortType, boolean isDescending, List<Recipe> recipes1) {
        Recipe[] recipes2 = new Recipe[recipes1.size()];
        for (int i = 0; i < recipes1.size(); i++) {
            recipes2[i] = recipes1.get(i);
        }
        if (!isDescending) {
            SearchFilter.heapSortByName(recipes2, sortType, false);
        } else {
            SearchFilter.heapSortByName(recipes2, sortType, true);
        }
        return Arrays.asList(recipes2);
    }

    // Method to search recipes based on an informal query
    public List<Recipe> searchByInformalQuery(String query, List<Recipe> recipes, List<Recipe> lastRecipes) {
        // Trim the query
        query = query.trim();

        // If query is empty, return all recipes
        if (query.isEmpty()) {
            Utils.showShortToast("get " + recipes.size() + " results");
            return recipes;
        }

        // Append ';' to the query if not present
        if (query.charAt(query.length() - 1) != ';') query = query + ";";
        try {
            return searchByQuery(query, recipes);
        } catch (Exception e) {
        }

        // If the query does not contain keywords, add "title=" prefix
        try {
            query = "title=" + query;
            return searchByQuery(query, recipes);
        } catch (Exception e) {
            // If still an error, return last results
            Utils.showShortToast(e.getMessage());
            return lastRecipes;
        }
    }

    // Method to search recipes based on a structured query
    public List<Recipe> searchByQuery(String query, List<Recipe> recipes) throws Exception {
        QueryObject queryObject = parseQuery(query);

        // Throw an error message if the query is invalid
        if (queryObject.queryInvalid) {
            throw new Exception(queryObject.errorMessage);
        }

        // Get search results based on the query object
        List<Recipe> searchResults = recipes.stream().filter(r -> {
            // Search by title
            for (String keyword : queryObject.title_keywords) {
                if (!r.title.toLowerCase().contains(keyword.toLowerCase())) return false;
            }

            // Search by ingredients
            String ingredientsString;
            if (r.ingredients == null || r.ingredients.size() == 0) ingredientsString = "";
            else ingredientsString = String.join(" ", r.ingredients).toLowerCase();
            for (String keyword : queryObject.ingredient_keywords) {
                if (!ingredientsString.contains(keyword.toLowerCase())) return false;
            }

            // Check like range limit
            int a = queryObject.like_range[0];
            int b = queryObject.like_range[1];
            if (a < 0) a = 0;
            if (b < 0) b = Integer.MAX_VALUE;
            if (r.like <= a || r.like >= b) return false;

            // Check view range limit
            a = queryObject.view_range[0];
            b = queryObject.view_range[1];
            if (a < 0) a = 0;
            if (b < 0) b = Integer.MAX_VALUE;
            return r.view > a && r.view < b;
        }).collect(Collectors.toList());

        // Show the number of search results
        Utils.showShortToast("get " + searchResults.size() + " results");

        return searchResults;
    }
}
