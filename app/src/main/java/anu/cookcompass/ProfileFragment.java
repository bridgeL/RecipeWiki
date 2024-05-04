package anu.cookcompass;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.Arrays;
import java.util.Objects;

public class ProfileFragment extends Fragment {
    private Button settingButton;
    private Spinner colorSelector;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile,container,false);
        settingButton=view.findViewById(R.id.settingButton);
        settingButton.setOnClickListener(v -> {
            String colorValue="#FFB241";
            changeMainBackgroundColor(colorValue);
        });

        // initialize spinner
        colorSelector = view.findViewById(R.id.colorSpinner);
        String[] themeList = Arrays.stream(ThemeType.values()).map(Enum::toString).toArray(String[]::new);
        ArrayAdapter<String> themeAdapter = new ArrayAdapter<>(this.requireActivity(), android.R.layout.simple_list_item_1 ,themeList);
        colorSelector.setAdapter(themeAdapter);
        // listeners of spinner
        colorSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // change theme color
                String colorValue;
                switch (ThemeType.values()[position]) {
                    case Default -> {
                        colorValue = "#FFB241";
                        changeMainBackgroundColor(colorValue);
                        printMsg("Case: default");
                    }
                    case Blue -> {
                        colorValue = "#FFFFFF";
                        changeMainBackgroundColor(colorValue);
                        printMsg("Case: blue");
                    }
                    case Green -> {
                        colorValue = "#000000";
                        changeMainBackgroundColor(colorValue);
                        printMsg("Case: green");
                    }
                    default ->
                            printMsg("Some problem may have occurred when selecting theme colour.");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });

        return view;
    }

    public void changeMainBackgroundColor(String colorValue) {
        if (getActivity() instanceof MainActivity mainActivity) {
            mainActivity.changeBackgroundColor(colorValue);
        }
    }

    void printMsg(String msg){
        Utils.showLongToast(this.requireActivity(), msg);
    }

    enum ThemeType{
        Default,
        Green,
        Blue
    }
}