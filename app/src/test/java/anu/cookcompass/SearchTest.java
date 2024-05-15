package anu.cookcompass;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import anu.cookcompass.pattern.Observer;
import anu.cookcompass.recipe.Recipe;
import anu.cookcompass.search.SearchService;
import anu.cookcompass.search.Tokenizer;

public class SearchTest {
    @Test
    public void test_simple_case(){
        SearchService searchService = SearchService.getInstance();

        searchService.query = "";
        searchService.sortType = "id";
        searchService.isDescending = true;

        Tokenizer tokenizer = new Tokenizer("title=title0");
        StringBuilder result = new StringBuilder();
        while(tokenizer.hasNext()){
            result.append(tokenizer.current().getToken());
            tokenizer.next();
        }
        assertEquals("ingredients=apple,orange;title=apple for day", result.toString());
    }
}
