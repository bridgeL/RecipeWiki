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
 * The class is Facade (design pattern), combined basic search, search-invalid, search-filter in one service point
 */
public class SearchService implements Subject<List<Recipe>> {
    List<Recipe> lastRecipes = new ArrayList<>();
    List<Observer<List<Recipe>>> observers = new ArrayList<>();
    public String query = "";
    public String sortType = "id";
    public boolean isDescending = true;
    static SearchService inst = null;

    @Override
    public List<Observer<List<Recipe>>> getObservers() {
        return observers;
    }

    private SearchService() {
    }

    public static SearchService getInstance() {
        if (inst == null) inst = new SearchService();
        return inst;
    }

    static QueryObject parseQuery(String query) {
        Tokenizer tokenizer = new Tokenizer(query);
        Parser parser = new Parser(tokenizer);
        return parser.parseQuery();
    }

    public void searchAndShow() {
        List<Recipe> recipes = search(query, sortType, isDescending, RecipeManager.getInstance().getRecipes());
        notifyAllObservers(recipes);
    }

    public List<Recipe> search(String query, String sortType, boolean isDescending, List<Recipe> recipes) {
        // search
        List<Recipe> recipes1 = searchByInformalQuery(query, recipes, lastRecipes);

        // sort
        List<Recipe> recipes2 = recipeSort(sortType, isDescending, recipes1);

        // store
        lastRecipes = recipes2;

        return recipes2;
    }

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
    public List<Recipe> searchByInformalQuery(String query, List<Recipe> recipes, List<Recipe> lastRecipes) {
        // trim
        query = query.trim();

        // if query is empty, return all data
        if (query.isEmpty()) {
            Utils.showShortToast("get " + recipes.size() + " results");
            return recipes;
        }

        // if last one if not ; ;
        if (query.charAt(query.length() - 1) != ';') query = query + ";";
        try {
            return searchByQuery(query, recipes);
        } catch (Exception e) {
        }

        // if not contains keyword
        try {
            query = "title=" + query;
            return searchByQuery(query, recipes);
        } catch (Exception e) {
            // if it is still wrong, return last results
            Utils.showShortToast(e.getMessage());
            return lastRecipes;
        }
    }

    public List<Recipe> searchByQuery(String query, List<Recipe> recipes) throws Exception {
        QueryObject queryObject = parseQuery(query);

        // show error message
        if (queryObject.queryInvalid) {
            throw new Exception(queryObject.errorMessage);
        }
        // get search results
        List<Recipe> searchResults = recipes.stream().filter(r -> {
            // search title
            for (String keyword : queryObject.title_keywords) {
                if (!r.title.toLowerCase().contains(keyword.toLowerCase())) return false;
            }

            // search ingredients
            String ingredientsString;
            if (r.ingredients == null || r.ingredients.size() == 0) ingredientsString = "";
            else ingredientsString = String.join(" ", r.ingredients).toLowerCase();
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
        Utils.showShortToast("get " + searchResults.size() + " results");

        return searchResults;
    }


}
