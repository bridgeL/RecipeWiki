package anu.cookcompass.searchfilter;

import java.util.Comparator;

import anu.cookcompass.model.Recipe;

class RecipeIdComparator implements Comparator<Recipe> {
    @Override
    public int compare(Recipe recipe1, Recipe recipe2) {
        return recipe1.id - recipe2.id; //Smaller id number appears first
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

    public static void heapSort (Recipe[] recipeArray, Comparator<Recipe> comparatorType) {
        int length = recipeArray.length;

        //Actually build the max heap using the array
        //We only focus on the parent nodes to maintain the max heap property
        //Parent node index i iterates from the index of the last parent node (length / 2 - 1) to the root (top parent node)
        for (int i = length - 1; i >= 0; i--) {
            heapify(recipeArray, length, i, comparatorType);
        }

        //Extract (move) the largest element and place it at the end of the array (AKA sort the largest element)
        //AKA swap the element at the last index with element at the root
        //Iterate from the last element to the second (bcs the first is always the one to be extracted)
        for (int i = length - 1; i > 0; i--) {
            Recipe temp = recipeArray[0]; //Store element at the root
            recipeArray[0] = recipeArray[i]; //rA[0] now points to element at the last index
            recipeArray[i] = temp; //rA[i] now points to element previously at the root (AKA root element now at the last index)
        }

        //Rebuild max heap after extraction
        heapify(recipeArray, length, 0, comparatorType);
    }
}
