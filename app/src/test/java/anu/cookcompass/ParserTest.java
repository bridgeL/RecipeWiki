package anu.cookcompass;

import org.junit.Test;

import static org.junit.Assert.*;


import anu.cookcompass.search.Parser;
import anu.cookcompass.search.QueryObject;
import anu.cookcompass.search.Tokenizer;


public class ParserTest {

    @Test
    public void testEmptyInput() {
        Tokenizer tokenizer = new Tokenizer("");
        Parser parser = new Parser(tokenizer);
        QueryObject result = parser.parseQuery();
        assertTrue(result.queryInvalid);
        assertEquals("Invalid search: empty search input.", result.errorMessage);
    }

    @Test
    public void testInvalidStartToken() {
        Tokenizer tokenizer = new Tokenizer("= ingredients");
        Parser parser = new Parser(tokenizer);
        QueryObject result = parser.parseQuery();
        assertTrue(result.queryInvalid);
        assertEquals("Invalid search: Input not beginning with a keyword.", result.errorMessage);
    }

    @Test
    public void testDuplicateIngredientsKeyword() {
        Tokenizer tokenizer = new Tokenizer("ingredients = apple; ingredients = orange;");
        Parser parser = new Parser(tokenizer);
        QueryObject result = parser.parseQuery();
        assertTrue(result.queryInvalid);
    }

    @Test
    public void testIncorrectPositionOfIngredientsKeyword() {
        Tokenizer tokenizer = new Tokenizer("title = recipe; ingredients = apple;");
        Parser parser = new Parser(tokenizer);
        QueryObject result = parser.parseQuery();
        assertTrue(result.queryInvalid);
    }

    @Test
    public void testValidIngredientsQuery() {
        Tokenizer tokenizer = new Tokenizer("ingredients = apple, banana;");
        Parser parser = new Parser(tokenizer);
        QueryObject result = parser.parseQuery();
        assertFalse(result.queryInvalid);
        assertArrayEquals(new String[]{"apple", "banana"}, result.ingredient_keywords);
    }

    @Test
    public void testValidTitleQuery() {
        Tokenizer tokenizer = new Tokenizer("title= delicious recipes;");
        Parser parser = new Parser(tokenizer);
        QueryObject result = parser.parseQuery();
        assertFalse(result.queryInvalid);
        assertArrayEquals(new String[]{"delicious recipes"}, result.title_keywords);
    }

    @Test
    public void testDuplicateTitleKeyword() {
        Tokenizer tokenizer = new Tokenizer("title = recipe1; title = recipe2;");
        Parser parser = new Parser(tokenizer);
        QueryObject result = parser.parseQuery();
        assertTrue(result.queryInvalid);
    }

    @Test
    public void testValidLikeQuery() {
        Tokenizer tokenizer = new Tokenizer("like > 10;");
        Parser parser = new Parser(tokenizer);
        QueryObject result = parser.parseQuery();
        assertFalse(result.queryInvalid);
        assertArrayEquals(new int[]{10, -1}, result.like_range);
    }

    @Test
    public void testInvalidLikeQuery() {
        Tokenizer tokenizer = new Tokenizer("like > ten;");
        Parser parser = new Parser(tokenizer);
        QueryObject result = parser.parseQuery();
        assertTrue(result.queryInvalid);
    }

    @Test
    public void testValidViewQuery() {
        Tokenizer tokenizer = new Tokenizer("view < 100;");
        Parser parser = new Parser(tokenizer);
        QueryObject result = parser.parseQuery();
        assertFalse(result.queryInvalid);
        assertArrayEquals(new int[]{0, 100}, result.view_range);
    }

    @Test
    public void testInvalidViewQuery() {
        Tokenizer tokenizer = new Tokenizer("view =;");
        Parser parser = new Parser(tokenizer);
        QueryObject result = parser.parseQuery();
        assertTrue(result.queryInvalid);
    }

    @Test
    public void testUnexpectedKeyword() {
        Tokenizer tokenizer = new Tokenizer("unexpected = value;");
        Parser parser = new Parser(tokenizer);
        QueryObject result = parser.parseQuery();
        assertTrue(result.queryInvalid);
        assertEquals("Invalid search: unexpected keyword.", result.errorMessage);
    }
}
