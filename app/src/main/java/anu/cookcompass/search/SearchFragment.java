package anu.cookcompass.search;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import anu.cookcompass.MainActivity;
import anu.cookcompass.R;
import anu.cookcompass.Utils;
import anu.cookcompass.theme.ThemeColor;
import anu.cookcompass.theme.ThemeConfig;
import anu.cookcompass.recipe.Recipe;
import anu.cookcompass.recipe.RecipeActivity;
import anu.cookcompass.recipe.RecipeManager;

public class SearchFragment extends Fragment {

    private SearchView searchView;
    private ListView listView;
    private RecipeAdapter adapter;
    private View rootView;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_search, container, false);
        //change the color when create
        ThemeConfig themeConfig = ((MainActivity) requireActivity()).getThemeConfig();
        rootView.setBackgroundColor(Color.parseColor(themeConfig.getTheme()));
        System.out.println("theme config in search" + themeConfig.getTheme());

        searchView = rootView.findViewById(R.id.search_view);
        listView = rootView.findViewById(R.id.results_listview);
        setupSearchView();

        adapter = new RecipeAdapter(getActivity(), RecipeManager.getInstance().getRecipes());
        listView.setAdapter(adapter);

        // callback for viewing recipe page
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Recipe selectedRecipe = RecipeManager.getInstance().getRecipes().get(position);
                Intent intent = new Intent(parent.getContext().getApplicationContext(), RecipeActivity.class);
                RecipeManager.getInstance().setCurrentRecipe(adapter.getItem(position));
                startActivity(intent);
            }
        });

        // set initial theme
        System.out.println("Set search fragment theme color to " + ThemeColor.getThemeColor());
        rootView.setBackgroundColor(Color.parseColor(ThemeColor.getThemeColor()));


        RecipeManager.getInstance().addObserver(recipes -> {
            adapter.clear();
            adapter.addAll(recipes);
            adapter.notifyDataSetChanged();
        });
        return rootView;
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
                // Check if the newText is empty and if it's considered as a submit action
                if (newText.isEmpty()) {
                    updateSearchResults(newText);
                    return true;
                }
                return false;
            }
        });
    }

    QueryObject parseQuery(String query) {
        Tokenizer tokenizer = new Tokenizer(query);
        Parser parser = new Parser(tokenizer);
        return parser.parseQuery();
    }

    private void updateSearchResults(String query) {
        List<Recipe> recipes = RecipeManager.getInstance().getRecipes();

        if (query.isEmpty()) {
            adapter.clear();
            adapter.addAll(recipes);
            adapter.notifyDataSetChanged();
            return;
        }

        // feature: search invalid
        if (!query.contains(";")) query = query + ";";
        QueryObject temp = parseQuery(query);

        if (temp.queryInvalid) {
            query = "title=" + query;
            temp = parseQuery(query);
        }

        QueryObject queryObject = temp;

        List<Recipe> searchResults = new ArrayList<>();

        if (queryObject.queryInvalid) {
            Utils.showLongToast(getActivity(), queryObject.errorMessage);
            Log.e("query", queryObject.errorMessage);
        } else {
            searchResults = recipes.stream().filter(r -> {
                for (String keyword : queryObject.title_keywords) {
                    if (!r.title.contains(keyword)) return false;
                }

                String ingredientsString = String.join(" ", r.ingredients);
                for (String keyword : queryObject.ingredient_keywords) {
                    if (!ingredientsString.contains(keyword)) return false;
                }

                // TODO: Like range filter

                return true;
            }).collect(Collectors.toList());
        }

        adapter.clear();
        adapter.addAll(searchResults);
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