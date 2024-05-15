package anu.cookcompass;

import org.junit.Test;

import static org.junit.Assert.*;

import anu.cookcompass.search.Tokenizer;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TokenizerTest {
    static String simple_query1 = "ingredients = apple, orange; title = apple for day";
    @Test
    public void test_simple_case(){
        Tokenizer tokenizer = new Tokenizer(simple_query1);
        StringBuilder result = new StringBuilder();
        while(tokenizer.hasNext()){
            result.append(tokenizer.current().getToken());
            tokenizer.next();
        }
        assertEquals("ingredients=apple,orange;title=apple for day", result.toString());
    }

    @Test
    public void test_simple_case2(){
        Tokenizer tokenizer = new Tokenizer("apple < 10");
        String result = "";
        while(tokenizer.hasNext()){
            result += tokenizer.current().getToken();
            System.out.println(tokenizer.current().getToken());
            tokenizer.next();
        }
        assertEquals("apple<10", result);
    }
}
