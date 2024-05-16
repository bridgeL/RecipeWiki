package anu.cookcompass;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import anu.cookcompass.model.BinarySearchTree;

/**
 * @author u7760022, Xinyang Li
 * The class contains tests for BinarySearchTree
 */
public class BinarySearchTreeTest {

    @Test
    public void testInsert() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.insert(5);
        bst.insert(3);
        bst.insert(7);

        List<Integer> expected = Arrays.asList(3, 5, 7);
        assertEquals(expected, bst.inOrderTraversal());
    }

    @Test
    public void testInsertAll() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.insertAll(Arrays.asList(5, 3, 7, 1, 4, 6, 8));

        List<Integer> expected = Arrays.asList(1, 3, 4, 5, 6, 7, 8);
        assertEquals(expected, bst.inOrderTraversal());
    }

    @Test
    public void testSearchFound() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.insertAll(Arrays.asList(5, 3, 7, 1, 4, 6, 8));

        assertTrue(bst.search(5));
        assertTrue(bst.search(1));
        assertTrue(bst.search(8));
    }

    @Test
    public void testSearchNotFound() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.insertAll(Arrays.asList(5, 3, 7, 1, 4, 6, 8));

        assertFalse(bst.search(0));
        assertFalse(bst.search(2));
        assertFalse(bst.search(9));
    }

    @Test
    public void testInOrderTraversal() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.insertAll(Arrays.asList(5, 3, 7, 1, 4, 6, 8));

        List<Integer> expected = Arrays.asList(1, 3, 4, 5, 6, 7, 8);
        assertEquals(expected, bst.inOrderTraversal());
    }

    @Test
    public void testEmptyTree() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        assertTrue(bst.inOrderTraversal().isEmpty());
        assertFalse(bst.search(1));
    }

    @Test
    public void testDuplicateInsert() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.insertAll(Arrays.asList(5, 3, 7, 3, 7));

        List<Integer> expected = Arrays.asList(3, 5, 7); // Duplicates should not be added
        assertEquals(expected, bst.inOrderTraversal());
    }
}

