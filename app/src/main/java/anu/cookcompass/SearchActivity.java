package anu.cookcompass;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.search.SearchBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import anu.cookcompass.database.LocalDatabase;
import anu.cookcompass.model.Global;
import anu.cookcompass.model.Recipe;
import anu.cookcompass.search.RecipeAdapter;

public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;
    private ListView listView;
    private RecipeAdapter adapter;
    private LocalDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Global.init(getApplicationContext());
        Global global = Global.getInstance();

        searchView = findViewById(R.id.search_view);
        listView = findViewById(R.id.results_listview);

        setupSearchView();

        List<Recipe> recipes = global.database.searchRecipes(""); // Initial load with empty search
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

        List<Recipe> searchResults = global.database.searchRecipes(query);
        adapter.clear();
        adapter.addAll(searchResults);
        adapter.notifyDataSetChanged();
    }
}