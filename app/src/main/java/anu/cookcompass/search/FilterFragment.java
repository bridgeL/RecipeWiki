package anu.cookcompass.search;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import anu.cookcompass.R;

/**
 * @author u7693070, Changlai Sun
 * @feature Search-Filter
 * The class controls the FilterFragment and combine the data with view. front-end of Search-Filter
 */
public class FilterFragment extends Fragment {

    private Button idBtn, titleBtn, viewBtn, likeBtn;
    private ImageButton sortToggle;
    private TextView sortToggleText;
//    private EditText editTopN, editMinViews, editMinLikes;
//    private Button btnFilterResults;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);
        setupViews(view);
        return view;
    }

    /**
     * Sets up the views and attaches event listeners to the buttons.
     *
     * @param view The root view of the fragment's layout.
     */
    private void setupViews(View view) {
        // ToggleButtons
        idBtn = view.findViewById(R.id.idSortBtn);
        titleBtn = view.findViewById(R.id.titleSortBtn);
        viewBtn = view.findViewById(R.id.viewSortBtn);
        likeBtn = view.findViewById(R.id.likeSortBtn);
        sortToggle = view.findViewById(R.id.sortToggle);
        sortToggleText = view.findViewById(R.id.sortToggleText);

        SearchService searchService = SearchService.getInstance();
        idBtn.setOnClickListener(l -> {
            searchService.sortType = "id";
            searchService.searchAndShow();
        });
        titleBtn.setOnClickListener(l -> {
            searchService.sortType = "title";
            searchService.searchAndShow();
        });
        viewBtn.setOnClickListener(l -> {
            searchService.sortType = "view";
            searchService.searchAndShow();
        });
        likeBtn.setOnClickListener(l -> {
            searchService.sortType = "like";
            searchService.searchAndShow();
        });

        // Set click listener for sort toggle button
        sortToggle.setOnClickListener(v -> {
                searchService.isDescending = !searchService.isDescending;
                sortToggle.setImageResource(searchService.isDescending ? R.drawable.ic_sort_desc : R.drawable.ic_sort_asc);
                sortToggleText.setText(searchService.isDescending ? R.string.sort_descending: R.string.sort_ascending);
                searchService.searchAndShow();
        });

        // Set the initial state of the sort toggle button and text
        sortToggle.setImageResource(searchService.isDescending ? R.drawable.ic_sort_desc : R.drawable.ic_sort_asc);
        sortToggleText.setText(searchService.isDescending ? R.string.sort_descending: R.string.sort_ascending);
    }

}