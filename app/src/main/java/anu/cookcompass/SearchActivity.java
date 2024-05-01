package anu.cookcompass;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import anu.cookcompass.TokenizerAndParser.Parser;
import anu.cookcompass.TokenizerAndParser.QueryObject;
import anu.cookcompass.TokenizerAndParser.Tokenizer;
import anu.cookcompass.database.Database;
import anu.cookcompass.model.Global;
import anu.cookcompass.model.Recipe;
import anu.cookcompass.search.RecipeAdapter;

public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;
    private ListView listView;
    private RecipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = findViewById(R.id.search_view);
        listView = findViewById(R.id.results_listview);

        setupSearchView();

        List<Recipe> recipes = Database.getInstance().getRecipes();
        adapter = new RecipeAdapter(this, recipes);
        listView.setAdapter(adapter);

    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                updateSearchResults(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                updateSearchResults(newText);
                return true;
            }
        });
    }

    private void updateSearchResults(String query) {
        List<Recipe> recipes = Database.getInstance().getRecipes();
        Tokenizer tokenizer = new Tokenizer(query);
        Parser parser = new Parser(tokenizer);
        QueryObject queryObject = parser.parseQuery();

        List<Recipe> searchResults = new ArrayList<>();

        if(queryObject.queryInvalid){
            Utils.showLongToast(this, queryObject.errorMessage);
            Log.e("query", queryObject.errorMessage);
        }
        else{
            searchResults = recipes.stream().filter(r->{
                if(queryObject.queryInvalid) return false;
                for (String keyword : queryObject.title_keywords) {
                    if(!r.title.contains(keyword)) return false;
                }

                // TODO: Ingredient keywords search
                // TODO: Like range filter
                // TODO: Collect range filter

                return true;
            }).collect(Collectors.toList());
        }

        adapter.clear();
        adapter.addAll(searchResults);
        adapter.notifyDataSetChanged();
    }
}