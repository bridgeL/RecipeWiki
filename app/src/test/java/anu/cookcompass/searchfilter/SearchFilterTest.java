package anu.cookcompass.searchfilter;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;

import anu.cookcompass.recipe.Recipe;

public class SearchFilterTest {

    @Test
    public void recipeIdComparatorTest() {

        RecipeIdComparator comparator = new RecipeIdComparator();

        Recipe[] recipes = {
                new Recipe(0, "Beef Stew", 30, 15),
                new Recipe(1, "Apple Pie", 105, 32),
                new Recipe(2, "Sushi", 100, 54),
                new Recipe(3, "Chicken Soup", 234, 67),
                new Recipe(4, "Cheese Cake", 50, 20)
        };

        int result1 = comparator.compare(recipes[0], recipes[1]);
        int result2 = comparator.compare(recipes[2], recipes[1]);

        assertTrue(result1 < 0);
        assertTrue(result2 > 0);
    }

    @Test
    public void recipeTitleComparatorTest() {

        RecipeTitleComparator comparator = new RecipeTitleComparator();

        Recipe[] recipes = {
                new Recipe(0, "Beef Stew", 30, 15),
                new Recipe(1, "Apple Pie", 105, 32),
                new Recipe(2, "Sushi", 100, 54),
                new Recipe(3, "Chicken Soup", 234, 67),
                new Recipe(4, "Cheese Cake", 50, 20)
        };

        int result1 = comparator.compare(recipes[0], recipes[1]);
        int result2 = comparator.compare(recipes[1], recipes[2]);

        assertTrue(result1 > 0);
        assertTrue(result2 < 0);
    }

    @Test
    public void recipeViewComparatorTest() {

        RecipeViewComparator comparator = new RecipeViewComparator();

        Recipe[] recipes = {
                new Recipe(0, "Beef Stew", 30, 15),
                new Recipe(1, "Apple Pie", 105, 32),
                new Recipe(2, "Sushi", 100, 54),
                new Recipe(3, "Chicken Soup", 234, 67),
                new Recipe(4, "Cheese Cake", 50, 20)
        };

        int result1 = comparator.compare(recipes[0], recipes[1]);
        int result2 = comparator.compare(recipes[1], recipes[2]);

        assertTrue(result1 > 0);
        assertTrue(result2 < 0);
    }

    @Test
    public void recipeLikeComparatorTest() {

        RecipeLikeComparator comparator = new RecipeLikeComparator();

        Recipe[] recipes = {
                new Recipe(0, "Beef Stew", 30, 15),
                new Recipe(1, "Apple Pie", 105, 32),
                new Recipe(2, "Sushi", 100, 54),
                new Recipe(3, "Chicken Soup", 234, 67),
                new Recipe(4, "Cheese Cake", 50, 20)
        };

        int result1 = comparator.compare(recipes[0], recipes[1]);
        int result2 = comparator.compare(recipes[2], recipes[1]);

        assertTrue(result1 > 0);
        assertTrue(result2 < 0);
    }

    @Test
    public void heapSortTestWithIdComparator() {

        Recipe[] recipes = {
                new Recipe(2, "Sushi", 100, 54),
                new Recipe(0, "Beef Stew", 30, 15),
                new Recipe(1, "Apple Pie", 105, 32),
                new Recipe(3, "Chicken Soup", 234, 67),
                new Recipe(4, "Cheese Cake", 50, 20)
        };

        SearchFilter.heapSort(recipes, new RecipeIdComparator());

        assertEquals(0, recipes[0].rid);
        assertEquals(4, recipes[4].rid);
    }

    @Test
    public void heapSortTestWithTitleComparator() {

        Recipe[] recipes = {
                new Recipe(2, "Sushi", 100, 54),
                new Recipe(0, "Beef Stew", 30, 15),
                new Recipe(1, "Apple Pie", 105, 32),
                new Recipe(3, "Chicken Soup", 234, 67),
                new Recipe(4, "Cheese Cake", 50, 20)
        };

        SearchFilter.heapSort(recipes, new RecipeTitleComparator());

        assertEquals("Apple Pie", recipes[0].title);
        assertEquals("Sushi", recipes[4].title);
    }

    @Test
    public void heapSortTestWithViewComparator() {

        Recipe[] recipes = {
                new Recipe(2, "Sushi", 100, 54),
                new Recipe(0, "Beef Stew", 30, 15),
                new Recipe(1, "Apple Pie", 105, 32),
                new Recipe(3, "Chicken Soup", 234, 67),
                new Recipe(4, "Cheese Cake", 50, 20)
        };

        SearchFilter.heapSort(recipes, new RecipeViewComparator());

        assertEquals(234, recipes[0].view);
        assertEquals(30, recipes[4].view);
    }

    @Test
    public void heapSortTestWithLikeComparator() {

        Recipe[] recipes = {
                new Recipe(2, "Sushi", 100, 54),
                new Recipe(0, "Beef Stew", 30, 15),
                new Recipe(1, "Apple Pie", 105, 32),
                new Recipe(3, "Chicken Soup", 234, 67),
                new Recipe(4, "Cheese Cake", 50, 20)
        };

        SearchFilter.heapSort(recipes, new RecipeLikeComparator());

        assertEquals(67, recipes[0].like);
        assertEquals(15, recipes[4].like);
    }

    @Test
    public void filterTopNRecipesTestWithIdComparator() {

        Recipe[] recipes = {
                new Recipe(2, "Sushi", 100, 54),
                new Recipe(0, "Beef Stew", 30, 15),
                new Recipe(1, "Apple Pie", 105, 32),
                new Recipe(3, "Chicken Soup", 234, 67),
                new Recipe(4, "Cheese Cake", 50, 20)
        };

        ArrayList<Recipe> result;

        result = SearchFilter.filterTopNRecipes(recipes, new RecipeIdComparator(), 3);

        assertEquals(3, result.size());
        assertEquals(0, result.get(0).rid);
        assertEquals(2, result.get(2).rid);
    }

    @Test
    public void filterTopNRecipesTestWithTitleComparator() {

        Recipe[] recipes = {
                new Recipe(2, "Sushi", 100, 54),
                new Recipe(0, "Beef Stew", 30, 15),
                new Recipe(1, "Apple Pie", 105, 32),
                new Recipe(3, "Chicken Soup", 234, 67),
                new Recipe(4, "Cheese Cake", 50, 20)
        };

        ArrayList<Recipe> result;

        result = SearchFilter.filterTopNRecipes(recipes, new RecipeTitleComparator(), 3);

        assertEquals(3, result.size());
        assertEquals("Apple Pie", result.get(0).title);
        assertEquals("Cheese Cake", result.get(2).title);
    }

    @Test
    public void filterTopNRecipesTestWithViewComparator() {

        Recipe[] recipes = {
                new Recipe(2, "Sushi", 100, 54),
                new Recipe(0, "Beef Stew", 30, 15),
                new Recipe(1, "Apple Pie", 105, 32),
                new Recipe(3, "Chicken Soup", 234, 67),
                new Recipe(4, "Cheese Cake", 50, 20)
        };

        ArrayList<Recipe> result;

        result = SearchFilter.filterTopNRecipes(recipes, new RecipeViewComparator(), 3);

        assertEquals(3, result.size());
        assertEquals(234, result.get(0).view);
        assertEquals(100, result.get(2).view);
    }
    @Test
    public void filterTopNRecipesTestWithLikeComparator() {

        Recipe[] recipes = {
                new Recipe(2, "Sushi", 100, 54),
                new Recipe(0, "Beef Stew", 30, 15),
                new Recipe(1, "Apple Pie", 105, 32),
                new Recipe(3, "Chicken Soup", 234, 67),
                new Recipe(4, "Cheese Cake", 50, 20)
        };

        ArrayList<Recipe> result;

        result = SearchFilter.filterTopNRecipes(recipes, new RecipeLikeComparator(), 3);

        assertEquals(3, result.size());
        assertEquals(67, result.get(0).like);
        assertEquals(32, result.get(2).like);
    }

    @Test
    public void filterTopNRecipesTestWithNotEnoughResultsAvailable() {

        Recipe[] recipes = {
                new Recipe(2, "Sushi", 100, 54),
                new Recipe(0, "Beef Stew", 30, 15),
                new Recipe(1, "Apple Pie", 105, 32),
                new Recipe(3, "Chicken Soup", 234, 67),
                new Recipe(4, "Cheese Cake", 50, 20)
        };

        ArrayList<Recipe> result;

        result = SearchFilter.filterTopNRecipes(recipes, new RecipeViewComparator(), 10);

        assertEquals(5, result.size());
        assertEquals(234, result.get(0).view);
        assertEquals(30, result.get(4).view);
    }

    @Test
    public void filterTopNRecipesWithMinViews() {

        Recipe[] recipes = {
                new Recipe(2, "Sushi", 100, 54),
                new Recipe(0, "Beef Stew", 30, 15),
                new Recipe(1, "Apple Pie", 105, 32),
                new Recipe(3, "Chicken Soup", 234, 67),
                new Recipe(4, "Cheese Cake", 50, 20)
        };

        ArrayList<Recipe> result;

        result = SearchFilter.filterTopNRecipesWithMinLikesOrViews(recipes, new RecipeViewComparator(), 3, 100);

        assertEquals(3, result.size());
        assertEquals(234, result.get(0).view);
        assertEquals(100, result.get(2).view);
    }

    @Test
    public void filterTopNRecipesWithMinLikes() {

        Recipe[] recipes = {
                new Recipe(2, "Sushi", 100, 54),
                new Recipe(0, "Beef Stew", 30, 15),
                new Recipe(1, "Apple Pie", 105, 32),
                new Recipe(3, "Chicken Soup", 234, 67),
                new Recipe(4, "Cheese Cake", 50, 20)
        };

        ArrayList<Recipe> result;

        result = SearchFilter.filterTopNRecipesWithMinLikesOrViews(recipes, new RecipeLikeComparator(), 3, 30);

        assertEquals(3, result.size());
        assertEquals(67, result.get(0).like);
        assertEquals(32, result.get(2).like);
    }

    @Test
    public void filterTopNRecipesWithMinLikesOrViewsTestWithNotEnoughResultsAvailable() {

        Recipe[] recipes = {
                new Recipe(2, "Sushi", 100, 54),
                new Recipe(0, "Beef Stew", 30, 15),
                new Recipe(1, "Apple Pie", 105, 32),
                new Recipe(3, "Chicken Soup", 234, 67),
                new Recipe(4, "Cheese Cake", 50, 20)
        };

        ArrayList<Recipe> result;

        result = SearchFilter.filterTopNRecipesWithMinLikesOrViews(recipes, new RecipeViewComparator(), 3, 200);

        assertEquals(1, result.size());
        assertEquals(234, result.get(0).view);
    }

    @Test
    public void filterTopNRecipesWithNoResultsAvailable() {

        Recipe[] recipes = {
                new Recipe(2, "Sushi", 100, 54),
                new Recipe(0, "Beef Stew", 30, 15),
                new Recipe(1, "Apple Pie", 105, 32),
                new Recipe(3, "Chicken Soup", 234, 67),
                new Recipe(4, "Cheese Cake", 50, 20)
        };

        ArrayList<Recipe> result;

        result = SearchFilter.filterTopNRecipesWithMinLikesOrViews(recipes, new RecipeLikeComparator(), 3, 1000);

        assertEquals(0, result.size());
    }
}