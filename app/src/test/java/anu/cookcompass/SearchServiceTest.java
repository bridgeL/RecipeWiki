package anu.cookcompass;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import anu.cookcompass.recipe.Recipe;
import anu.cookcompass.search.SearchService;


public class SearchServiceTest {

    private SearchService searchService;
    private List<Recipe> recipes;

    public SearchServiceTest() {
        searchService = SearchService.getInstance();
        recipes = new ArrayList<>();
        recipes.add(new Recipe(1, "Apple Pie", 100, 200));
        recipes.add(new Recipe(2, "Banana Bread", 150, 300));
        recipes.add(new Recipe(3, "Carrot Cake", 200, 400));
        recipes.add(new Recipe(4, "Date Squares", 250, 500));

        // Adding ingredients to recipes
        recipes.get(0).ingredients = Arrays.asList("apple", "sugar", "flour");
        recipes.get(1).ingredients = Arrays.asList("banana", "sugar", "flour");
        recipes.get(2).ingredients = Arrays.asList("carrot", "sugar", "flour");
        recipes.get(3).ingredients = Arrays.asList("dates", "sugar", "flour");
    }

    @Test
    public void testSearchByTitle() {
        List<Recipe> results = searchService.search("title=Apple;", "id", true, recipes);
        assertEquals(1, results.size());
        assertEquals("Apple Pie", results.get(0).title);
    }

    @Test
    public void testSearchByIngredients() {
        List<Recipe> results = searchService.search("ingredients=banana;", "id", true, recipes);
        assertEquals(1, results.size());
        assertEquals("Banana Bread", results.get(0).title);
    }

    @Test
    public void testSearchByLikeRange() {
        List<Recipe> results = searchService.search("like>200;", "id", true, recipes);
        assertEquals(3, results.size());
    }

    @Test
    public void testSearchByViewRange() {
        List<Recipe> results = searchService.search("view<200;", "id", true, recipes);
        assertEquals(2, results.size());
    }

    @Test
    public void testSearchWithMultipleCriteria() {
        List<Recipe> results = searchService.search("title=Cake; like>150; view<500;", "id", true, recipes);
        assertEquals(1, results.size());
        assertEquals("Carrot Cake", results.get(0).title);
    }

    @Test
    public void testSearchWithInvalidQuery() {
        List<Recipe> results = searchService.search("invalid query", "id", true, recipes);
        assertTrue(results.isEmpty());
    }

    @Test
    public void testSearchWithEmptyQuery() {
        List<Recipe> results = searchService.search("", "id", true, recipes);
        assertEquals(4, results.size());
    }

    @Test
    public void testSearchSortAscending() {
        List<Recipe> results = searchService.search("", "like", false, recipes);
        assertEquals(4, results.size());
        assertEquals("Apple Pie", results.get(0).title);
        assertEquals("Date Squares", results.get(3).title);
    }

    @Test
    public void testSearchSortDescending() {
        List<Recipe> results = searchService.search("", "like", true, recipes);
        assertEquals(4, results.size());
        assertEquals("Date Squares", results.get(0).title);
        assertEquals("Apple Pie", results.get(3).title);
    }

    int[] getRids(List<Recipe> recipes) {
        return recipes.stream().mapToInt(r -> r.rid).toArray();
    }

    @Test
    public void test_simple_case() {
        SearchService searchService = SearchService.getInstance();
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe(0, "recipe_" + 0, 10, 1));
        recipes.add(new Recipe(1, "drecipe_" + 1, 11, 26));
        recipes.add(new Recipe(2, "drecipe_" + 2, 3, 22));
        recipes.add(new Recipe(3, "recipe_" + 3, 7, 10));

        List<Recipe> recipes2;

        // search title = re, show all data
        recipes2 = searchService.search("re", "like", false, recipes);
        assertArrayEquals(new int[]{0, 3, 2, 1}, getRids(recipes2));

        // search title = dr, show partial data
        recipes2 = searchService.search("title=dr;", "like", false, recipes);
        assertArrayEquals(new int[]{2, 1}, getRids(recipes2));

        // search query is invalid, show the last results
        recipes2 = searchService.search("title>2", "like", false, recipes);
        assertArrayEquals(new int[]{2, 1}, getRids(recipes2));

        // search query is empty, show all data
        recipes2 = searchService.search("", "like", true, recipes);
        assertArrayEquals(new int[]{1, 2, 3, 0}, getRids(recipes2));

        // show nothing
        recipes2 = searchService.search("nothing", "like", true, recipes);
        assertArrayEquals(new int[]{}, getRids(recipes2));
    }
}
