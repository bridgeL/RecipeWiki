package anu.cookcompass;

import android.content.Context;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;
import java.util.Objects;

import anu.cookcompass.broadcast.ThemeUpdateEvent;
import anu.cookcompass.gps.UserLocationManager;
import anu.cookcompass.model.ThemeColor;
import anu.cookcompass.model.ThemeConfig;

public class ProfileFragment extends Fragment {
    private View rootView;
    private Spinner colorSelector;
    private static final int PERMISSION_REQUEST_LOCATION = 1001;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        //on create change the color
        ThemeConfig themeConfig = ((MainActivity) requireActivity()).getThemeConfig();
        rootView.setBackgroundColor(Color.parseColor(themeConfig.getTheme()));

        getLocationAndUpdateAddress();

        //email bind text view
        TextView emailAddressTextView = rootView.findViewById(R.id.emailAddressTextView);
        emailAddressTextView.setText(themeConfig.getAccount());
        System.out.println(getActivity());

        // initialize spinner
        colorSelector = rootView.findViewById(R.id.colorSpinner);
        String[] themeList = Arrays.stream(ThemeType.values()).map(Enum::toString).toArray(String[]::new);
        ArrayAdapter<String> themeAdapter = new ArrayAdapter<>(this.requireActivity(), android.R.layout.simple_list_item_1, themeList);
        colorSelector.setAdapter(themeAdapter);
        // set spinner: set the selected element being the current theme
        for (int i = 0; i < themeAdapter.getCount(); i++) {
            if (Objects.equals(themeAdapter.getItem(i), ThemeColor.getThemeName().toString())) {
                //
                colorSelector.setSelection(i);
                break;
            }
        }

        // listeners of spinner
        colorSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // change theme color
                String colorValue = null;
                switch (ThemeType.values()[position]) {
                    case Default -> colorValue = "#FFB241";
                    case White -> colorValue = "#FFFFFF";
                    case Gold -> colorValue = "#FFD700";
                    default ->
                            printMsg("Some problem may have occurred when selecting theme colour.");
                }
                rootView.setBackgroundColor(Color.parseColor(colorValue));
                EventBus.getDefault().post(new ThemeUpdateEvent(colorValue));
                ThemeColor.setThemeColor(colorValue);
                ThemeColor.writeTheme();    // write new color value into file
                // for bug avoidance, still set ThemeConfig
                MainActivity mainActivity = (MainActivity) getActivity();
                assert mainActivity != null;
                ThemeConfig themeConfig = mainActivity.getThemeConfig();
                themeConfig.setTheme(colorValue);
//                mainActivity.updateTheme(colorValue);
//                System.out.println("theme config in profile" + themeConfig.getTheme());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });

        // set initial theme
        rootView.setBackgroundColor(Color.parseColor(ThemeColor.getThemeColor()));
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ThemeConfig themeConfig = ((MainActivity) requireActivity()).getThemeConfig();
        view.setBackgroundColor(Color.parseColor(themeConfig.getTheme()));
    }


    //get location
    private void getLocationAndUpdateAddress() {
        UserLocationManager locationManager = new UserLocationManager((LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE));
        locationManager.getLocation(getActivity(), location -> {
            String locationString = locationManager.decodeLocation(getActivity(), location);
            TextView countryAddressTextView = rootView.findViewById(R.id.countryAddressTextView);
            countryAddressTextView.setText(locationString);
        });
    }


    void printMsg(String msg) {
        Utils.showLongToast(this.requireActivity(), msg);
    }

    public enum ThemeType {
        Default,
        Gold,
        White
    }
}