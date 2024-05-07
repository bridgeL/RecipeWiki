package anu.cookcompass.model;

import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree<T extends Comparable<T>> {
    public static class TreeNode<T extends Comparable<T>> {
        T val;
        TreeNode<T> left;
        TreeNode<T> right;

        public TreeNode(T val) {
            this.val = val;
            this.left = null;
            this.right = null;
        }
    }

    private TreeNode<T> root;

    public BinarySearchTree() {
        this.root = null;
    }

    public void insert(T val) {
        root = insertRecursive(root, val);
    }

    public void insertAll(List<T> vals) {
        for (T val : vals) {
            insert(val);
        }
    }

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

    public boolean search(T val) {
        return searchRecursive(root, val);
    }

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

    public List<T> inOrderTraversal() {
        List<T> dataArray = new ArrayList<>();
        inOrderTraversalRecursive(root, dataArray);
        return dataArray;
    }

    private void inOrderTraversalRecursive(TreeNode<T> root, List<T> dataArray) {
        if (root != null) {
            inOrderTraversalRecursive(root.left, dataArray);
//            System.out.print(root.val + " ");
            dataArray.add(root.val);
            inOrderTraversalRecursive(root.right, dataArray);
        }
    }

}
