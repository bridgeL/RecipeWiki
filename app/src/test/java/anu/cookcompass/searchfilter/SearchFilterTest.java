package anu.cookcompass.searchfilter;

import static org.junit.Assert.*;

import org.junit.Test;

import anu.cookcompass.recipe.Recipe;

/**
 * @author u7754676, Tashia Tamara
 * @feature Search-Filter
 * The class contains tests for the Search-Filter feature
 */
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
    public void heapSortByTitleWithAscendingOrder() {

        Recipe[] recipes = {
                new Recipe(2, "Sushi", 100, 54),
                new Recipe(0, "Beef Stew", 30, 15),
                new Recipe(1, "Apple Pie", 105, 32),
                new Recipe(3, "Chicken Soup", 234, 67),
                new Recipe(4, "Cheese Cake", 50, 20)
        };

        SearchFilter.heapSortByName(recipes, "title", true);

        assertEquals("Apple Pie", recipes[0].title);
        assertEquals("Sushi", recipes[4].title);
    }

    @Test
    public void heapSortByTitleWithDescendingOrder() {

        Recipe[] recipes = {
                new Recipe(2, "Sushi", 100, 54),
                new Recipe(0, "Beef Stew", 30, 15),
                new Recipe(1, "Apple Pie", 105, 32),
                new Recipe(3, "Chicken Soup", 234, 67),
                new Recipe(4, "Cheese Cake", 50, 20)
        };

        SearchFilter.heapSortByName(recipes, "title", false);

        assertEquals("Sushi", recipes[0].title);
        assertEquals("Apple Pie", recipes[4].title);
    }

    @Test
    public void heapSortByLikeWithAscendingOrder() {

        Recipe[] recipes = {
                new Recipe(2, "Sushi", 100, 54),
                new Recipe(0, "Beef Stew", 30, 15),
                new Recipe(1, "Apple Pie", 105, 32),
                new Recipe(3, "Chicken Soup", 234, 67),
                new Recipe(4, "Cheese Cake", 50, 20)
        };

        SearchFilter.heapSortByName(recipes, "like", false);

        assertEquals(15, recipes[0].like);
        assertEquals(67, recipes[4].like);
    }

    @Test
    public void heapSortByLikeWithDescendingOrder() {

        Recipe[] recipes = {
                new Recipe(2, "Sushi", 100, 54),
                new Recipe(0, "Beef Stew", 30, 15),
                new Recipe(1, "Apple Pie", 105, 32),
                new Recipe(3, "Chicken Soup", 234, 67),
                new Recipe(4, "Cheese Cake", 50, 20)
        };

        SearchFilter.heapSortByName(recipes, "like", true);

        assertEquals(67, recipes[0].like);
        assertEquals(15, recipes[4].like);
    }

    @Test
    public void heapSortByNullComparator() {

        Recipe[] recipes = {
                new Recipe(2, "Sushi", 100, 54),
                new Recipe(0, "Beef Stew", 30, 15),
                new Recipe(1, "Apple Pie", 105, 32),
                new Recipe(3, "Chicken Soup", 234, 67),
                new Recipe(4, "Cheese Cake", 50, 20)
        };

        assertThrows(NullPointerException.class, () -> SearchFilter.heapSortByName(recipes, null, false));
    }

    @Test
    public void heapSortByInvalidCriterionName() {

        Recipe[] recipes = {
                new Recipe(2, "Sushi", 100, 54),
                new Recipe(0, "Beef Stew", 30, 15),
                new Recipe(1, "Apple Pie", 105, 32),
                new Recipe(3, "Chicken Soup", 234, 67),
                new Recipe(4, "Cheese Cake", 50, 20)
        };

        assertThrows(NullPointerException.class, () -> SearchFilter.heapSortByName(recipes, "deliciousness", true));
    }

    @Test
    public void heapSortByViewWithDuplicateElements() {

        Recipe[] recipes = {
                new Recipe(2, "Sushi", 100, 101),
                new Recipe(0, "Beef Stew", 30, 15),
                new Recipe(1, "Sushi", 100, 101),
                new Recipe(3, "Chicken Soup", 234, 67),
                new Recipe(4, "Cheese Cake", 50, 20)
        };

        SearchFilter.heapSortByName(recipes, "like", true);

        //In the case of duplicate entries, order in original array maintained
        assertEquals(2, recipes[0].rid);
        assertEquals(1, recipes[1].rid);
    }

    @Test
    public void heapSortByTitleWithTwoRecipesWithSameName() {

        Recipe[] recipes = {
                new Recipe(2, "Sushi", 100, 101),
                new Recipe(0, "Beef Stew", 30, 15),
                new Recipe(1, "Sushi", 100, 97),
                new Recipe(3, "Chicken Soup", 234, 67),
                new Recipe(4, "Cheese Cake", 50, 20)
        };

        SearchFilter.heapSortByName(recipes, "title", false);

        assertEquals("Sushi", recipes[0].title);
        assertEquals("Sushi", recipes[1].title);
    }

    @Test
    public void heapSortByLikeWithTwoRecipesWithSameName() {

        Recipe[] recipes = {
                new Recipe(2, "Sushi", 100, 101),
                new Recipe(0, "Beef Stew", 30, 15),
                new Recipe(1, "Sushi", 100, 97),
                new Recipe(3, "Chicken Soup", 234, 67),
                new Recipe(4, "Cheese Cake", 50, 20)
        };

        SearchFilter.heapSortByName(recipes, "like", true);

        assertEquals(101, recipes[0].like);
        assertEquals(97, recipes[1].like);
    }

    @Test
    public void heapSortByNameUsingEmptyArray() {

        Recipe[] recipes = new Recipe[0];

        SearchFilter.heapSortByName(recipes, "like", false);

        assertArrayEquals(new Recipe[0], recipes);
    }

    @Test
    public void heapSortByNameUsingNullArray() {

        Recipe[] recipes = null;

        assertThrows(NullPointerException.class, () -> SearchFilter.heapSortByName(recipes, "like", false));
    }

    @Test
    public void heapSortByTitleUsingRecipesStartingWithNumbers() {

        Recipe[] recipes = {
                new Recipe(2, "3-in-1 Ice Cream", 100, 101),
                new Recipe(0, "Beef Stew", 30, 15),
                new Recipe(1, "Lasagna", 100, 97),
                new Recipe(3, "5 Spice Cajun Shrimp", 234, 67),
                new Recipe(4, "Cheese Cake", 50, 20)
        };

        SearchFilter.heapSortByName(recipes, "title", true);

        assertEquals("3-in-1 Ice Cream", recipes[0].title);
        assertEquals("5 Spice Cajun Shrimp", recipes[1].title);
    }

    @Test
    public void heapSortByTitleUsingRecipesThatContainNumbers() {

        Recipe[] recipes = {
                new Recipe(2, "Delicious 4th of July Cocktail", 100, 101),
                new Recipe(0, "Donuts", 30, 15),
                new Recipe(1, "Lasagna", 100, 97),
                new Recipe(3, "5 Spice Cajun Shrimp", 234, 67),
                new Recipe(4, "Fried Rice", 50, 20)
        };

        SearchFilter.heapSortByName(recipes, "title", true);

        assertEquals("5 Spice Cajun Shrimp", recipes[0].title);
        assertEquals("Delicious 4th of July Cocktail", recipes[1].title);
    }

    @Test
    public void heapSortByTitleUsingRecipesStartingWithSpecialCharacters() {

        Recipe[] recipes = {
                new Recipe(2, "$ushi", 100, 101),
                new Recipe(0, "Beef Stew", 30, 15),
                new Recipe(1, "!ce Cream", 100, 97),
                new Recipe(3, "Chicken Soup", 234, 67),
                new Recipe(4, "Cheese Cake", 50, 20)
        };

        SearchFilter.heapSortByName(recipes, "title", true);

        assertEquals("!ce Cream", recipes[0].title);
        assertEquals("$ushi", recipes[1].title);
    }

    @Test
    public void heapSortByTitleUsingRecipesThatContainSpecialCharacters() {

        Recipe[] recipes = {
                new Recipe(2, "Su$hi", 100, 101),
                new Recipe(0, "Beef Stew", 30, 15),
                new Recipe(1, "Ice Cre#m", 100, 97),
                new Recipe(3, "Chicken Soup", 234, 67),
                new Recipe(4, "Cheese Cake", 50, 20)
        };

        SearchFilter.heapSortByName(recipes, "title", true);

        assertEquals("Beef Stew", recipes[0].title);
        assertEquals("Cheese Cake", recipes[1].title);
    }
}

/*
public static void heapSortByName(Recipe[] recipeArray, String criterionName, boolean isAscending) {
        Comparator<Recipe> comparatorType = null;
        if (criterionName.equals("id")) comparatorType = new RecipeIdComparator();
        if (criterionName.equals("title")) comparatorType = new RecipeTitleComparator();
        if (criterionName.equals("view")) comparatorType = new RecipeViewComparator();
        if (criterionName.equals("like")) comparatorType = new RecipeLikeComparator();

        //If isAscending is false, the order is descending
        if (!isAscending && comparatorType != null) {
            comparatorType = comparatorType.reversed();
        }
        heapSort(recipeArray, comparatorType);
    }
 */