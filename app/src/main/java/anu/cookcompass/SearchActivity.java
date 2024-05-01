package anu.cookcompass;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

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
                updateSearchResults(newText);
                return true;
            }
        });
    }

    private void updateSearchResults(String query) {
        Global global = Global.getInstance();

//        List<Recipe> searchResults = global.database.searchRecipes(query);
//        adapter.clear();
//        adapter.addAll(searchResults);
//        adapter.notifyDataSetChanged();
    }
}