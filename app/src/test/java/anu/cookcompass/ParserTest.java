package anu.cookcompass;
import org.junit.Test;

import static org.junit.Assert.*;

import anu.cookcompass.TokenizerAndParser.Parser;
import anu.cookcompass.TokenizerAndParser.QueryObject;
import anu.cookcompass.TokenizerAndParser.Tokenizer;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ParserTest {
    static String simple_query1 = "ingredients = apple, orange;  title = apple pie;";
    static String simple_query2 = "ingredients = apple";
    static String simple_query3 = "like > 3; collect < 10";
    static String simple_query_fail1 = "ingredients = ,";
    static String simple_query_fail2 = "ingredients = apple,";
    static String simple_query_fail3 = "ingredients = apple; ingredients = apple";
    static String simple_query_fail4 = "ing = apple";
    @Test
    public void test_simple_case(){
        Tokenizer tokenizer = new Tokenizer(simple_query1);
        Parser parser = new Parser(tokenizer);
        QueryObject parseResult = parser.parseQuery();
        assertFalse(parseResult.queryInvalid);
        assertNull(parseResult.errorMessage);
        assertArrayEquals(new String[]{"apple", "orange"},parseResult.ingredient_keywords);
        assertArrayEquals(new String[]{"apple pie"}, parseResult.title_keywords);

        QueryObject parseResult2 = parseQuery(simple_query3);
        assertFalse(parseResult2.queryInvalid);
        assertArrayEquals(new int[]{3,-1}, parseResult2.like_range);
        assertArrayEquals(new int[]{0,10}, parseResult2.collect_range);
    }

    @Test
    public void simple_query_fail_case(){
        QueryObject parseResult1 = parseQuery(simple_query_fail1);
        assertTrue(parseResult1.queryInvalid);
        assertEquals("Invalid search: invalid value list for keyword \"ingredients\".",parseResult1.errorMessage);

        QueryObject parseResult2 = parseQuery(simple_query_fail2);
        assertTrue(parseResult2.queryInvalid);
        assertEquals("Invalid search: invalid value list for keyword \"ingredients\".",parseResult2.errorMessage);

        QueryObject parseResult3 = parseQuery(simple_query_fail3);
        assertTrue(parseResult3.queryInvalid);
        assertEquals("Invalid search: Keyword \"ingredients\" duplicated.",parseResult3.errorMessage);

        QueryObject parseResult4 = parseQuery(simple_query_fail4);
        assertTrue(parseResult4.queryInvalid);
        assertEquals("Invalid search: unexpected keyword.",parseResult4.errorMessage);
    }

    static QueryObject parseQuery(String query){
        Tokenizer tokenizer = new Tokenizer(query);
        Parser parser = new Parser(tokenizer);
        QueryObject parseResult = parser.parseQuery();
        return parseResult;
    }
}
