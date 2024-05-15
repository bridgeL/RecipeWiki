package anu.cookcompass;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import anu.cookcompass.search.Tokenizer;

public class SearchTest {
    @Test
    public void test_simple_case(){
        Tokenizer tokenizer = new Tokenizer("title=title0");
        StringBuilder result = new StringBuilder();
        while(tokenizer.hasNext()){
            result.append(tokenizer.current().getToken());
            tokenizer.next();
        }
        assertEquals("ingredients=apple,orange;title=apple for day", result.toString());
    }
}
