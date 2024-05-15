package anu.cookcompass;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import anu.cookcompass.recipe.Recipe;
import anu.cookcompass.search.SearchService;

public class SearchServiceTest {
    @Test
    public void test_simple_case() {
        SearchService searchService = SearchService.getInstance();
        List<Recipe> recipes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            recipes.add(new Recipe(i, "recipe_" + i, i + 1, 100 - i));
        }

        searchService.query = "";
        searchService.sortType = "id";
        searchService.isDescending = true;

        List<Recipe> recipes2 = searchService.search("recipe_0", "like", true, recipes);
        System.out.println(recipes2);
    }
}
