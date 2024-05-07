package anu.cookcompass.search;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

import anu.cookcompass.R;

public class FilterFragment extends Fragment {

    private ToggleButton toggleId, toggleTitle, toggleView, toggleLike;
    private EditText editTopN, editMinViews, editMinLikes;
    private Button btnFilterResults;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);
        setupViews(view);
        return view;
    }

    private void setupViews(View view) {
        // ToggleButtons
        toggleId = view.findViewById(R.id.toggle_sort_by_id);
        toggleTitle = view.findViewById(R.id.toggle_sort_by_title);
        toggleView = view.findViewById(R.id.toggle_sort_by_view);
        toggleLike = view.findViewById(R.id.toggle_sort_by_like);

        // EditTexts for input
        editTopN = view.findViewById(R.id.editTopN);
        editMinViews = view.findViewById(R.id.editMinViews);
        editMinLikes = view.findViewById(R.id.editMinLikes);

        // Button to execute filter
        btnFilterResults = view.findViewById(R.id.btnFilterResults);

        // Set up the listener for toggle buttons
        setupToggleButtons();

        // Setup button listener
        btnFilterResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterResults();
            }
        });
    }

    private void setupToggleButtons() {
        CompoundButton.OnCheckedChangeListener listener = (buttonView, isChecked) -> {
            if (isChecked) {
                if (buttonView != toggleId) toggleId.setChecked(false);
                if (buttonView != toggleTitle) toggleTitle.setChecked(false);
                if (buttonView != toggleView) toggleView.setChecked(false);
                if (buttonView != toggleLike) toggleLike.setChecked(false);
                buttonView.setChecked(true);
            }
        };

        toggleId.setOnCheckedChangeListener(listener);
        toggleTitle.setOnCheckedChangeListener(listener);
        toggleView.setOnCheckedChangeListener(listener);
        toggleLike.setOnCheckedChangeListener(listener);
    }

    private void filterResults() {
        // Implement filtering logic here
        // You can use editTopN, editMinViews, editMinLikes to get the user input
        // And then update your list accordingly
    }
}