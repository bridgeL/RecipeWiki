package anu.cookcompass;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Arrays;

import anu.cookcompass.model.ThemeConfig;

public class ProfileFragment extends Fragment {
private View rootView;
private Spinner colorSelector;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_profile,container,false);
//on create change the color
        ThemeConfig themeConfig = ((MainActivity) requireActivity()).getThemeConfig();
        rootView.setBackgroundColor(Color.parseColor(themeConfig.getTheme()));
        // initialize spinner
        colorSelector = rootView.findViewById(R.id.colorSpinner);
        String[] themeList = Arrays.stream(ThemeType.values()).map(Enum::toString).toArray(String[]::new);
        ArrayAdapter<String> themeAdapter = new ArrayAdapter<>(this.requireActivity(), android.R.layout.simple_list_item_1 ,themeList);
        colorSelector.setAdapter(themeAdapter);
        // listeners of spinner
        colorSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // change theme color
                String colorValue = null;
                switch (ThemeType.values()[position]) {
                    case Default -> //                        printMsg("Case: default");
                            colorValue = "#FFB241";
                    case Blue ->
                        colorValue = "#FFFFFF";
//                        printMsg("Case: blue");
                    case Green ->
                        colorValue = "#000000";
//                        printMsg("Case: green");
                    default ->
                            printMsg("Some problem may have occurred when selecting theme colour.");
                }
                rootView.setBackgroundColor(Color.parseColor(colorValue));
                MainActivity mainActivity=(MainActivity) getActivity();
                assert mainActivity != null;
                ThemeConfig themeConfig = mainActivity.getThemeConfig();
                themeConfig.setTheme(colorValue);
                mainActivity.updateTheme(colorValue);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });

        return rootView;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ThemeConfig themeConfig = ((MainActivity) requireActivity()).getThemeConfig();
        view.setBackgroundColor(Color.parseColor(themeConfig.getTheme()));
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