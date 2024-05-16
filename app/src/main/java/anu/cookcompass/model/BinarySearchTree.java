package anu.cookcompass.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a Binary Search Tree (BST) data structure.
 * The BST supports generic types that extend Comparable.
 *
 * @autor u7760022, Xinyang Li
 */
public class BinarySearchTree<T extends Comparable<T>> {

    /**
     * A nested static class representing a node in the BST.
     * Each node contains a value, a left child, and a right child.
     */
    public static class TreeNode<T extends Comparable<T>> {
        T val;
        TreeNode<T> left;
        TreeNode<T> right;

        /**
         * Constructor to initialize the node with a value.
         *
         * @param val the value to be stored in the node
         */
        public TreeNode(T val) {
            this.val = val;
            this.left = null;
            this.right = null;
        }
    }

    private TreeNode<T> root;

    /**
     * Constructor to initialize an empty BST.
     */
    public BinarySearchTree() {
        this.root = null;
    }

    /**
     * Inserts a value into the BST.
     *
     * @param val the value to be inserted
     */
    public void insert(T val) {
        root = insertRecursive(root, val);
    }

    /**
     * Inserts a list of values into the BST.
     *
     * @param vals the list of values to be inserted
     */
    public void insertAll(List<T> vals) {
        for (T val : vals) {
            insert(val);
        }
    }

    /**
     * Recursively inserts a value into the BST.
     *
     * @param root the root of the subtree where the value is to be inserted
     * @param val the value to be inserted
     * @return the root of the subtree after insertion
     */
    private TreeNode<T> insertRecursive(TreeNode<T> root, T val) {
        if (root == null) {
            root = new TreeNode<>(val);
            return root;
        }

        if (val.compareTo(root.val) < 0) {
            root.left = insertRecursive(root.left, val);
        } else if (val.compareTo(root.val) > 0) {
            root.right = insertRecursive(root.right, val);
        }

        return root;
    }

    /**
     * Searches for a value in the BST.
     *
     * @param val the value to be searched for
     * @return true if the value is found, false otherwise
     */
    public boolean search(T val) {
        return searchRecursive(root, val);
    }

    /**
     * Recursively searches for a value in the BST.
     *
     * @param root the root of the subtree to be searched
     * @param val the value to be searched for
     * @return true if the value is found, false otherwise
     */
    private boolean searchRecursive(TreeNode<T> root, T val) {
        if (root == null) {
            return false;
        }

        if (val.compareTo(root.val) == 0) {
            return true;
        }

        if (val.compareTo(root.val) < 0) {
            return searchRecursive(root.left, val);
        } else {
            return searchRecursive(root.right, val);
        }
    }

    /**
     * Performs an in-order traversal of the BST and returns the list of values in sorted order.
     *
     * @return the list of values in sorted order
     */
    public List<T> inOrderTraversal() {
        List<T> dataArray = new ArrayList<>();
        inOrderTraversalRecursive(root, dataArray);
        return dataArray;
    }

    /**
     * Recursively performs an in-order traversal of the BST.
     *
     * @param root the root of the subtree to be traversed
     * @param dataArray the list to store the values in sorted order
     */
    private void inOrderTraversalRecursive(TreeNode<T> root, List<T> dataArray) {
        if (root != null) {
            inOrderTraversalRecursive(root.left, dataArray);
            dataArray.add(root.val);
            inOrderTraversalRecursive(root.right, dataArray);
        }
    }
}
