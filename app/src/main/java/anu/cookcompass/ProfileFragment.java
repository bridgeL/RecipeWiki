package anu.cookcompass;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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
        // check location permission
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_LOCATION);
        } else {
            getLocationAndUpdateAddress();
        }
        //email bind text view
        TextView emailAddressTextView = rootView.findViewById(R.id.emailAddressTextView);
        emailAddressTextView.setText(themeConfig.getAccount());

        // initialize spinner
        colorSelector = rootView.findViewById(R.id.colorSpinner);
        String[] themeList = Arrays.stream(ThemeType.values()).map(Enum::toString).toArray(String[]::new);
        ArrayAdapter<String> themeAdapter = new ArrayAdapter<>(this.requireActivity(), android.R.layout.simple_list_item_1, themeList);
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
                    case White -> colorValue = "#FFFFFF";
//                        printMsg("Case: white");
                    case Gold -> colorValue = "#FFD700";
//                        printMsg("Case: gold");
                    default ->
                            printMsg("Some problem may have occurred when selecting theme colour.");
                }
                rootView.setBackgroundColor(Color.parseColor(colorValue));
                MainActivity mainActivity = (MainActivity) getActivity();
                assert mainActivity != null;
                ThemeConfig themeConfig = mainActivity.getThemeConfig();
                themeConfig.setTheme(colorValue);
                mainActivity.updateTheme(colorValue);
                System.out.println("theme config in profile" + themeConfig.getTheme());


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

    @Override//check permission
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocationAndUpdateAddress();
            } else {
                Toast.makeText(requireActivity(), "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //get location
    private void getLocationAndUpdateAddress() {
        LocationManager locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        // check permission
        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // single updates
            locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, location -> {
                // if not null ,decode country information
                System.out.println("Net location  " + location);
                Geocoder geocoder = new Geocoder(requireActivity(), Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    assert addresses != null;
                    if (!addresses.isEmpty()) {
                        String countryName = addresses.get(0).getCountryName();
                        String adminArea = addresses.get(0).getAdminArea();
                        String formattedAddress = String.format(requireActivity().getString(R.string.format_country_admin_area), countryName, adminArea);

                        ThemeConfig themeConfig = ((MainActivity) requireActivity()).getThemeConfig();
                        themeConfig.setAddress(formattedAddress);
                        TextView countryAddressTextView = rootView.findViewById(R.id.countryAddressTextView);
                        countryAddressTextView.setText(formattedAddress);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }, null);
        }
    }


    void printMsg(String msg) {
        Utils.showLongToast(this.requireActivity(), msg);
    }

    enum ThemeType {
        Default,
        Gold,
        White
    }
}