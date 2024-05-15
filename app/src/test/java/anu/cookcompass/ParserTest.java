package anu.cookcompass;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Arrays;

import anu.cookcompass.search.Parser;
import anu.cookcompass.search.QueryObject;
import anu.cookcompass.search.Tokenizer;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ParserTest {
//    static String simple_query1 = "ingredients = apple, orange;  title = apple pie;";
//    static String simple_query2 = "title=  title0;";
//    static String simple_query3 = "like > 3; collect < 10;";
//    static String simple_query4 = "ingredients = pe   ar;";
//    static String simple_query_fail1 = "ingredients = ,";
//    static String simple_query_fail2 = "ingredients = apple,";
//    static String simple_query_fail3 = "ingredients = apple; ingredients = apple";
//    static String simple_query_fail4 = "ing = apple";
//    static String simple_query_fail5 = "like >3; like < 3";
//    static String simple_query_fail6 = "ingredients = apple";

    @Test
    public void test_special_case1() {
        Tokenizer tokenizer = new Tokenizer("title=title;");
        Parser parser = new Parser(tokenizer);
        QueryObject parseResult = parser.parseQuery();
        assertFalse(parseResult.queryInvalid);
        assertArrayEquals(new String[]{"title"}, parseResult.title_keywords);
    }

    @Test
    public void test_special_case2() {
        Tokenizer tokenizer = new Tokenizer("title=title0;");
        Parser parser = new Parser(tokenizer);
        QueryObject parseResult = parser.parseQuery();
        assertFalse(parseResult.queryInvalid);
        assertArrayEquals(new String[]{"title0"}, parseResult.title_keywords);
    }

    @Test
    public void test_special_case3() {
        Tokenizer tokenizer = new Tokenizer("title=3;");
        Parser parser = new Parser(tokenizer);
        QueryObject parseResult = parser.parseQuery();
        assertFalse(parseResult.queryInvalid);
        assertArrayEquals(new String[]{"3"}, parseResult.title_keywords);
    }

    @Test
    public void test_special_case4() {
        Tokenizer tokenizer = new Tokenizer("title=3a;");
        Parser parser = new Parser(tokenizer);
        QueryObject parseResult = parser.parseQuery();
        assertFalse(parseResult.queryInvalid);
        assertArrayEquals(new String[]{"3a"}, parseResult.title_keywords);
    }

    @Test
    public void test_special_case5() {
        Tokenizer tokenizer = new Tokenizer("title0=apple;");
        Parser parser = new Parser(tokenizer);
        QueryObject parseResult = parser.parseQuery();
        assertTrue(parseResult.queryInvalid);
    }

    @Test
    public void test_special_case6() {
        Tokenizer tokenizer = new Tokenizer("title=apple,   title0;");
        Parser parser = new Parser(tokenizer);
        QueryObject parseResult = parser.parseQuery();
        assertFalse(parseResult.queryInvalid);
        assertArrayEquals(new String[]{"apple", "title0"}, parseResult.title_keywords);
    }

//    @Test
//    public void test_simple_case() {
//        Tokenizer tokenizer = new Tokenizer(simple_query1);
//        Parser parser = new Parser(tokenizer);
//        QueryObject parseResult = parser.parseQuery();
//        assertFalse(parseResult.queryInvalid);
//        assertNull(parseResult.errorMessage);
//        assertArrayEquals(new String[]{"apple", "orange"}, parseResult.ingredient_keywords);
//        assertArrayEquals(new String[]{"apple pie"}, parseResult.title_keywords);
//
//        QueryObject parseResult2 = parseQuery(simple_query2);
//        assertFalse(parseResult2.queryInvalid);
//        assertArrayEquals(new String[]{"title0"}, parseResult2.title_keywords);
//
//        QueryObject parseResult3 = parseQuery(simple_query3);
//        assertFalse(parseResult3.queryInvalid);
//        assertArrayEquals(new int[]{3, -1}, parseResult3.like_range);
//        assertArrayEquals(new int[]{0, 10}, parseResult3.view_range);
//
//        QueryObject parseResult4 = parseQuery(simple_query4);
//        assertFalse(parseResult.queryInvalid);
//        assertArrayEquals(new String[]{"pe   ar"}, parseResult4.ingredient_keywords);
//    }

//    @Test
//    public void simple_query_fail_case() {
//        QueryObject parseResult1 = parseQuery(simple_query_fail1);
//        assertTrue(parseResult1.queryInvalid);
//        assertEquals("Invalid search: invalid value list for keyword \"ingredients\" or missing semicolon at the end.", parseResult1.errorMessage);
//
//        QueryObject parseResult2 = parseQuery(simple_query_fail2);
//        assertTrue(parseResult2.queryInvalid);
//        assertEquals("Invalid search: invalid value list for keyword \"ingredients\" or missing semicolon at the end.", parseResult2.errorMessage);
//
//        QueryObject parseResult3 = parseQuery(simple_query_fail3);
//        assertTrue(parseResult3.queryInvalid);
//        assertEquals("Invalid search: Keyword \"ingredients\" duplicated.", parseResult3.errorMessage);
//
//        QueryObject parseResult4 = parseQuery(simple_query_fail4);
//        assertTrue(parseResult4.queryInvalid);
//        assertEquals("Invalid search: unexpected keyword.", parseResult4.errorMessage);
//
//        QueryObject parseResult5 = parseQuery(simple_query_fail5);
//        assertTrue(parseResult5.queryInvalid);
//        assertEquals("Invalid search: Keyword \"like\" duplicated.", parseResult5.errorMessage);
//
//        QueryObject parseResult6 = parseQuery(simple_query_fail6);
//        assertTrue(parseResult6.queryInvalid);
//        assertEquals("Invalid search: invalid value list for keyword \"ingredients\" or missing semicolon at the end.", parseResult6.errorMessage);
//    }

    static QueryObject parseQuery(String query) {
        Tokenizer tokenizer = new Tokenizer(query);
        Parser parser = new Parser(tokenizer);
        QueryObject parseResult = parser.parseQuery();
        return parseResult;
    }
}
