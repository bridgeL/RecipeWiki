package anu.cookcompass.search;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;

import anu.cookcompass.R;

public class FilterFragment extends Fragment {

    private Button idBtn, titleBtn, viewBtn, likeBtn;
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

        SearchService searchService = SearchService.getInstance();
        idBtn.setOnClickListener(l->{
            searchService.sortType = "id";
            searchService.search(getContext());
        });
        titleBtn.setOnClickListener(l->{
            searchService.sortType = "title";
            searchService.search(getContext());
        });
        viewBtn.setOnClickListener(l->{
            searchService.sortType = "view";
            searchService.search(getContext());
        });
        likeBtn.setOnClickListener(l->{
            searchService.sortType = "like";
            searchService.search(getContext());
        });
//        // EditTexts for input
//        editTopN = view.findViewById(R.id.editTopN);
//        editMinViews = view.findViewById(R.id.editMinViews);
//        editMinLikes = view.findViewById(R.id.editMinLikes);
//
//        // Button to execute filter
//        btnFilterResults = view.findViewById(R.id.btnFilterResults);

        // Set up the listener for toggle buttons
//        setupToggleButtons();

//        // Setup button listener
//        btnFilterResults.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                filterResults();
//            }
//        });
    }

//    private void setupToggleButtons() {
//        CompoundButton.OnCheckedChangeListener listener = (buttonView, isChecked) -> {
//            if (isChecked) {
//                if (buttonView != idBtn) idBtn.setChecked(false);
//                if (buttonView != titleBtn) titleBtn.setChecked(false);
//                if (buttonView != viewBtn) viewBtn.setChecked(false);
//                if (buttonView != likeBtn) likeBtn.setChecked(false);
//                buttonView.setChecked(true);
//
////                if (buttonView != toggleId) toggleId.setChecked(false);
////                if (buttonView != toggleTitle) toggleTitle.setChecked(false);
////                if (buttonView != toggleView) toggleView.setChecked(false);
////                if (buttonView != toggleLike) toggleLike.setChecked(false);
////                buttonView.setChecked(true);
//            }
//        };
//
//        idBtn.setOnCheckedChangeListener(listener);
//        titleBtn.setOnCheckedChangeListener(listener);
//        viewBtn.setOnCheckedChangeListener(listener);
//        likeBtn.setOnCheckedChangeListener(listener);
//    }
//
//    private void filterResults() {
//        // Implement filtering logic here
//        // You can use editTopN, editMinViews, editMinLikes to get the user input
//        // And then update your list accordingly
//    }
}