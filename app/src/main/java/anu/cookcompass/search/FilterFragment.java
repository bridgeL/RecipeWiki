package anu.cookcompass.search;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.content.Context;

import anu.cookcompass.R;

public class FilterFragment extends Fragment {

    private Button idBtn, titleBtn, viewBtn, likeBtn;
    private ImageButton sortToggle;
//    private EditText editTopN, editMinViews, editMinLikes;
//    private Button btnFilterResults;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);
        setupViews(view);
        return view;
    }

    private void setupViews(View view) {
        // ToggleButtons
        idBtn = view.findViewById(R.id.idSortBtn);
        titleBtn = view.findViewById(R.id.titleSortBtn);
        viewBtn = view.findViewById(R.id.viewSortBtn);
        likeBtn = view.findViewById(R.id.likeSortBtn);
        sortToggle = view.findViewById(R.id.sortToggle);

        SearchService searchService = SearchService.getInstance();
        idBtn.setOnClickListener(l -> {
            searchService.sortType = "id";
            searchService.search(getContext());
        });
        titleBtn.setOnClickListener(l -> {
            searchService.sortType = "title";
            searchService.search(getContext());
        });
        viewBtn.setOnClickListener(l -> {
            searchService.sortType = "view";
            searchService.search(getContext());
        });
        likeBtn.setOnClickListener(l -> {
            searchService.sortType = "like";
            searchService.search(getContext());
        });

        sortToggle.setOnClickListener(v -> {
                searchService.isAscending = !searchService.isAscending;
                sortToggle.setImageResource(searchService.isAscending ? R.drawable.ic_sort_asc : R.drawable.ic_sort_desc);
                searchService.search(getContext());
        });

        sortToggle.setImageResource(searchService.isAscending ? R.drawable.ic_sort_asc : R.drawable.ic_sort_desc);
    }

}