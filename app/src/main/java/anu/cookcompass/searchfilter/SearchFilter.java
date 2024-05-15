package anu.cookcompass.searchfilter;

import java.util.ArrayList;
import java.util.Comparator;

import anu.cookcompass.recipe.Recipe;
/**
 * @author u7754676, Tashia Tamara
 * @feature Search-filter
 * The class is the back-end of search-filter
 */
class RecipeIdComparator implements Comparator<Recipe> {
    @Override
    public int compare(Recipe recipe1, Recipe recipe2) {
        return recipe1.rid - recipe2.rid; //Smaller id number appears first (Ascending)
    }
}
/**
 * @author u7754676, Tashia Tamara
 * @feature Search-filter
 * The class is the back-end of search-filter
 */
class RecipeTitleComparator implements Comparator<Recipe> {
    @Override
    public int compare(Recipe recipe1, Recipe recipe2) {
        return recipe1.title.compareTo(recipe2.title); //Alphabetical order (Ascending)
    }
}
/**
 * @author u7754676, Tashia Tamara
 * @feature Search-filter
 * The class is the back-end of search-filter
 */
class RecipeViewComparator implements Comparator<Recipe> {
    @Override
    public int compare(Recipe recipe1, Recipe recipe2) {
        return recipe2.view - recipe1.view; //Larger view count appears first (Descending)
    }
}
/**
 * @author u7754676, Tashia Tamara
 * @feature Search-filter
 * The class is the back-end of search-filter
 */
class RecipeLikeComparator implements Comparator<Recipe> {
    @Override
    public int compare(Recipe recipe1, Recipe recipe2) {
        return recipe2.like - recipe1.like; //Larger like count appears first (Descending)
    }
}

/**
 * @author u7754676, Tashia Tamara
 * @feature Search-filter
 * The class is the back-end of search-filter
 */
public class SearchFilter {

    public static void heapSortByName(Recipe[] recipeArray, String criterionName, boolean isAscending) {
        Comparator<Recipe> comparatorType = null;
        if (criterionName.equals("id")) comparatorType = new RecipeIdComparator();
        if (criterionName.equals("title")) comparatorType = new RecipeTitleComparator();
        if (criterionName.equals("view")) comparatorType = new RecipeViewComparator();
        if (criterionName.equals("like")) comparatorType = new RecipeLikeComparator();

        if (!isAscending && comparatorType != null) {
            comparatorType = comparatorType.reversed();
        }
        heapSort(recipeArray, comparatorType);
    }

    //Sort the recipes using heap sort (Using max heap)
    public static void heapSort(Recipe[] recipeArray, Comparator<Recipe> comparator) {
        int length = recipeArray.length;

        //Build max heap using heapify()
        //We only focus on the parent nodes to maintain the max heap property
        //Parent node index i iterates from the index of the last parent node (length / 2 - 1) to the root (top parent node)
        for (int i = length / 2 - 1; i >= 0; i--)
            heapify(recipeArray, length, i, comparator);

        //Extract (move) the largest element and place it at the end of the array (AKA sort the largest element)
        //AKA swap the element at the last index with element at the root
        //Iterate from the last element to the second (bcs the first is always the one to be extracted)
        for (int i = length - 1; i > 0; i--) {
            Recipe temp = recipeArray[0];
            recipeArray[0] = recipeArray[i];
            recipeArray[i] = temp;

            //Rebuild max heap after extraction
            heapify(recipeArray, i, 0, comparator);
        }
        }

    //Logic for building max heap [Helper method to provide heapify() in heapSort()]
    public static void heapify(Recipe[] recipeArray, int length, int parentIndex, Comparator<Recipe> comparatorType) {

        int largest = parentIndex; // Initialize largest as parentIndex
        int leftChild = 2 * parentIndex + 1;
        int rightChild = 2 * parentIndex + 2;

        //Check if left child is within array length and larger than the parent
        if (leftChild < length && comparatorType.compare(recipeArray[leftChild], recipeArray[largest]) > 0)
            largest = leftChild;

        //Check if right child is within array length and larger than the parent
        if (rightChild < length && comparatorType.compare(recipeArray[rightChild], recipeArray[largest]) > 0)
            largest = rightChild;

        //Swap if largest is not the parent, then recursively call heapify again. This is to ensure:
        //(1) largest element is the parent node
        //(2) max-heap property is maintained throughout the tree
        if (largest != parentIndex) {
            Recipe swap = recipeArray[parentIndex];
            recipeArray[parentIndex] = recipeArray[largest];
            recipeArray[largest] = swap;

            heapify(recipeArray, length, largest, comparatorType);
        }
        }

    //Return top N recipes according to order defined by id/title/view/like comparator
    public static ArrayList<Recipe> filterTopNRecipes(Recipe[] recipeArray, Comparator<Recipe> comparatorType, int topN) {

        heapSort(recipeArray, comparatorType);

        ArrayList<Recipe> topRecipeArray = new ArrayList<>();

        for (int i = 0; i < topN; i++) {
            if (topRecipeArray.size() == recipeArray.length) { //Break loop if there are no more recipes available to show
                break;
            }
            topRecipeArray.add(recipeArray[i]);
        }

        return topRecipeArray;
    }

    //Return top N recipes with at least K number of likes or views
    public static ArrayList<Recipe> filterTopNRecipesWithMinLikesOrViews(Recipe[] recipeArray, Comparator<Recipe> comparatorType, int topN, int atLeastK) {

        ArrayList<Recipe> topNRecipesWithMinLikesOrViews = new ArrayList<>();

            heapSort(recipeArray, comparatorType);

            int count = 0;

            //Using like comparator
            if (comparatorType instanceof RecipeLikeComparator) {
                //Top N recipes with at least K likes (listed from most liked to least liked)
                for (Recipe recipe : recipeArray) {
                    //Break loop topN is reached or if there are no more recipes available to show
                    if (count == topN || topNRecipesWithMinLikesOrViews.size() == recipeArray.length) {
                        break; //Break current iteration if topN elements have been added (Early termination)
                    }
                    if (recipe.like >= atLeastK) {
                        topNRecipesWithMinLikesOrViews.add(recipe);
                        count++;
                    }
                }
                //Using view comparator
            } else if (comparatorType instanceof RecipeViewComparator) {
                //Top N recipes with at least K views (listed from most viewed to least viewed)
                for (Recipe recipe : recipeArray) {
                    if (count == topN) {
                        break; //Break current iteration if topN elements have been added (Early termination)
                    }
                    if (recipe.view >= atLeastK) {
                        topNRecipesWithMinLikesOrViews.add(recipe);
                        count++;
                    }
                }
            }
        return topNRecipesWithMinLikesOrViews;
    }
}