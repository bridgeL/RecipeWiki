package anu.cookcompass.search;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;

import java.util.List;

import anu.cookcompass.R;
import anu.cookcompass.theme.ThemeColor;
import anu.cookcompass.recipe.Recipe;
import anu.cookcompass.recipe.RecipeActivity;
import anu.cookcompass.recipe.RecipeManager;

public class SearchFragment extends Fragment {
    private RecipeAdapter adapter;
    private View rootView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_search, container, false);

        // ======================================
        // create view
        // ======================================

        SearchView searchView = rootView.findViewById(R.id.search_view);
        ListView listView = rootView.findViewById(R.id.results_listview);

        // ======================================
        // create instance
        // ======================================

        adapter = new RecipeAdapter(getActivity(), RecipeManager.getInstance().getRecipes());

        // ======================================
        // bind view listener / callback / handler
        // ======================================

        // callback for viewing recipe page
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(parent.getContext().getApplicationContext(), RecipeActivity.class);
            RecipeManager.getInstance().setCurrentRecipe(adapter.getItem(position));
            startActivity(intent);
        });

        // setupSearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                updateSearchResults(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (query.isEmpty()) {
                    updateSearchResults(query);
                    return true;
                }
                return false;
            }
        });

        // ======================================
        // other initial code
        // ======================================

        listView.setAdapter(adapter);


        // set initial theme
        rootView.setBackgroundColor(Color.parseColor(ThemeColor.getThemeColor()));

        RecipeManager.getInstance().addObserver(recipes -> {
            adapter.clear();
            adapter.addAll(recipes);
            adapter.notifyDataSetChanged();
        });

        return rootView;
    }

    private void updateSearchResults(String query) {
        List<Recipe> recipes = SearchService.getInstance().search(getContext(), query);
        adapter.clear();
        adapter.addAll(recipes);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        // switch color when visiable
        rootView.setBackgroundColor(Color.parseColor(ThemeColor.getThemeColor()));
        System.out.println("theme config in search" + ThemeColor.getThemeColor());
    }
}