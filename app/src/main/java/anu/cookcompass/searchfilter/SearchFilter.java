package anu.cookcompass.searchfilter;

import java.util.ArrayList;
import java.util.Comparator;

import anu.cookcompass.model.Recipe;

class RecipeIdComparator implements Comparator<Recipe> {
    @Override
    public int compare(Recipe recipe1, Recipe recipe2) {
        return recipe1.id - recipe2.id; //Smaller id number appears first
    }
}

class RecipeTitleComparator implements Comparator<Recipe> {
    @Override
    public int compare(Recipe recipe1, Recipe recipe2) {
        return recipe1.title.compareTo(recipe2.title); //Alphabetical order
    }
}

class RecipeViewComparator implements Comparator<Recipe> {
    @Override
    public int compare(Recipe recipe1, Recipe recipe2) {
        return recipe2.view - recipe1.view; //Larger view count appears first
    }
}

class RecipeLikeComparator implements Comparator<Recipe> {
    @Override
    public int compare(Recipe recipe1, Recipe recipe2) {
        return recipe2.like - recipe1.like; //Larger like count appears first
    }
}

public class SearchFilter { //Using heap sort

    //Logic for building max heap
    public static void heapify(Recipe[] recipeArray, int length, int parentIndex, Comparator<Recipe> comparatorType) {
        int largest = parentIndex; //Initialize largest element as the parent node
        int leftChild = 2 * parentIndex + 1;
        int rightChild = 2 * parentIndex + 2;

        //Check if left child is within array length and larger than the parent
        if (leftChild < length && comparatorType.compare(recipeArray[leftChild], recipeArray[largest]) > 0) {
            largest = leftChild;
        }

        //Check if right child is within array length and larger than the parent
        if (rightChild < length && comparatorType.compare(recipeArray[rightChild], recipeArray[largest]) > 0) {
            largest = rightChild;
        }

        //Swap if largest is not the parent, the call heapify again. This is to ensure:
        //(1) largest element is the parent node
        //(2) max-heap property is maintained throughout the tree
        if (largest != parentIndex) {
            Recipe temp = recipeArray[parentIndex];
            recipeArray[parentIndex] = recipeArray[largest];
            recipeArray[largest] = temp;
            heapify(recipeArray, length, largest, comparatorType);
        }
    }

    //Sort the recipes using heap sort
    public static void heapSort(Recipe[] recipeArray, Comparator<Recipe> comparatorType) {
        int length = recipeArray.length;

        //Actually build the max heap using the array
        //We only focus on the parent nodes to maintain the max heap property
        //Parent node index i iterates from the index of the last parent node (length / 2 - 1) to the root (top parent node)
        for (int i = length / 2 - 1; i >= 0; i--) {
            heapify(recipeArray, length, i, comparatorType);
        }

        //Extract (move) the largest element and place it at the end of the array (AKA sort the largest element)
        //AKA swap the element at the last index with element at the root
        //Iterate from the last element to the second (bcs the first is always the one to be extracted)
        for (int i = length - 1; i > 0; i--) {
            Recipe temp = recipeArray[0]; //Store element at the root
            recipeArray[0] = recipeArray[i]; //rA[0] now points to element at the last index
            recipeArray[i] = temp; //rA[i] now points to element previously at the root (AKA root element now at the last index)

            //Rebuild max heap after extraction
            //Length is i (the last index)
            heapify(recipeArray, i, 0, comparatorType);
        }
    }

    //Return top N recipes according to order defined by id/title/view/like comparator
    public static ArrayList<Recipe> filterTopNRecipes(Recipe[] recipeArray, Comparator<Recipe> comparatorType, int topN) {
        //Sort recipes according to the chosen comparator using the heapSort() method
        heapSort(recipeArray, comparatorType);

        ArrayList<Recipe> topRecipeArray = new ArrayList<>();

        for (int i = 0; i < topN; i++) {
            topRecipeArray.add(recipeArray[i]);
        }

        return topRecipeArray;
    }

    //Return top N recipes with at least K number of likes or views
    public static ArrayList<Recipe> filterTopNMostLovedRecipes(Recipe[] recipeArray, Comparator<Recipe> comparatorType, int topN, int atLeastK) {

        ArrayList<Recipe> topNMostLovedRecipeArray = new ArrayList<>();

        if (!(comparatorType instanceof RecipeIdComparator) && !(comparatorType instanceof RecipeTitleComparator)) {
            //Sort recipes according to the chosen comparator (like or view comparator) using the heapSort() method
            heapSort(recipeArray, comparatorType);

            int count = 0;

            //Using like comparator
            if (comparatorType instanceof RecipeLikeComparator) {
                //Top N recipes with at least K likes (listed from most liked to least liked)
                while (count < topN) { //To control overall iteration / outer loop
                    for (Recipe recipe : recipeArray) {
                        if (recipe.like >= atLeastK) {
                            topNMostLovedRecipeArray.add(recipe);
                            count++;
                            if (count == topN) {
                                break; //Break current iteration if topN elements have been added (Early termination)
                            }
                        }
                    }
                }
            //Using view comparator
            } else if (comparatorType instanceof RecipeViewComparator) {
                //Top N recipes with at least K views (listed from most viewed to least viewed)
                while (count < topN) {
                    for (Recipe recipe : recipeArray) {
                        if (recipe.view >= atLeastK) {
                            topNMostLovedRecipeArray.add(recipe);
                            count++;
                            if (count == topN) {
                                break; //Break current iteration if topN elements have been added (Early termination)
                            }
                        }
                    }
                }
            }
        }
        return topNMostLovedRecipeArray;
    }
}