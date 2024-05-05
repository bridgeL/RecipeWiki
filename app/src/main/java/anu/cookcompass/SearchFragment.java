package anu.cookcompass;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import anu.cookcompass.TokenizerAndParser.Parser;
import anu.cookcompass.TokenizerAndParser.QueryObject;
import anu.cookcompass.TokenizerAndParser.Tokenizer;
import anu.cookcompass.database.Database;
import anu.cookcompass.model.Recipe;
import anu.cookcompass.model.ThemeColor;
import anu.cookcompass.model.ThemeConfig;
import anu.cookcompass.search.RecipeAdapter;

public class SearchFragment extends Fragment {

    private SearchView searchView;
    private ListView listView;
    private RecipeAdapter adapter;
    private View rootView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle saveInstanceState){
        rootView=inflater.inflate(R.layout.fragment_search,container,false);

        searchView =rootView.findViewById(R.id.search_view);
        listView=rootView.findViewById(R.id.results_listview);
        setupSearchView();
        List<Recipe>recipes=Database.getInstance().getRecipes();
        adapter=new RecipeAdapter(getActivity(),recipes);
        listView.setAdapter(adapter);

        // set initial theme
        System.out.println("Set search fragment theme color to " + ThemeColor.getThemeColor());
        rootView.setBackgroundColor(Color.parseColor(ThemeColor.getThemeColor()));

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
            Utils.showLongToast(getActivity(), queryObject.errorMessage);
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

    @Override
    public void onResume() {
        super.onResume();
        // switch color when visiable
        rootView.setBackgroundColor(Color.parseColor(ThemeColor.getThemeColor()));
        System.out.println("theme config in search" + ThemeColor.getThemeColor());
    }
}