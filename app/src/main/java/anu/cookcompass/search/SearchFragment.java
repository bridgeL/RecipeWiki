package anu.cookcompass.search;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.File;

import anu.cookcompass.R;
import anu.cookcompass.Utils;
import anu.cookcompass.pattern.SingletonFactory;
import anu.cookcompass.theme.ThemeColor;
import anu.cookcompass.recipe.Recipe;
import anu.cookcompass.recipe.RecipeActivity;
import anu.cookcompass.recipe.RecipeManager;

/**
 * @author u7693070, Changlai Sun
 * @feature Search
 * The class is the front-end of Search
 */
public class SearchFragment extends Fragment {
    private RecipeAdapter adapter;
    private View rootView;
    private SearchView searchView;
    private ListView listView;
    private DrawerLayout drawerLayout;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_search, container, false);

        // ======================================
        // create view
        // ======================================

        setupToolbar();

        listView = rootView.findViewById(R.id.results_listview);

        // ======================================
        // create instance
        // ======================================

        adapter = new RecipeAdapter(getActivity(), RecipeManager.getInstance().getRecipes());
        SingletonFactory.setInstance(adapter);

        // ======================================
        // bind view listener / callback / handler
        // ======================================

        // callback for viewing recipe page
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(parent.getContext().getApplicationContext(), RecipeActivity.class);
            Recipe recipe = adapter.getItem(position);
            RecipeManager.getInstance().setCurrentRecipe(recipe);
            adapter.notifyDataSetChanged();

            File file = new File(getActivity().getFilesDir(), "recipe/recipe.json");
            Utils.saveJson(file, RecipeManager.getInstance().getRecipes());
            startActivity(intent);
        });

        // setupSearchView

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

    private void setupToolbar() {
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        drawerLayout = rootView.findViewById(R.id.drawer_layout);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchMenuItem.getActionView();
        setupSearchView();

        SearchService.getInstance().addObserver(recipes -> {
            adapter.clear();
            adapter.addAll(recipes);
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        } else if (id == R.id.action_sort) {
            drawerLayout.openDrawer(GravityCompat.END);
            showFilterFragment();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showFilterFragment() {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FilterFragment filterFragment = new FilterFragment();
        fragmentTransaction.replace(R.id.drawer_container, filterFragment);
        fragmentTransaction.commit();
    }

    private void setupSearchView() {
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

        searchView.setQueryHint(getString(R.string.search_hint));
    }

    private void updateSearchResults(String query) {
        SearchService.getInstance().query = query;
        SearchService.getInstance().searchAndShow();
    }

    @Override
    public void onResume() {
        super.onResume();
        // switch color when visiable
        rootView.setBackgroundColor(Color.parseColor(ThemeColor.getThemeColor()));
        System.out.println("theme config in search" + ThemeColor.getThemeColor());
    }
}