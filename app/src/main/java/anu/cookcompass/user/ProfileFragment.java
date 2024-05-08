package anu.cookcompass.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;
import java.util.Objects;

import anu.cookcompass.MainActivity;
import anu.cookcompass.R;
import anu.cookcompass.Utils;
import anu.cookcompass.pattern.SingletonFactory;
import anu.cookcompass.theme.ThemeUpdateEvent;
import anu.cookcompass.datastream.UserSimulator;
import anu.cookcompass.gps.UserLocationManager;
import anu.cookcompass.theme.ThemeColor;
import anu.cookcompass.theme.ThemeType;

public class ProfileFragment extends Fragment {
    private View rootView;
    private Spinner colorSelector;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;
    private ActivityResultLauncher<Intent> imagePickerLauncher;

    void setImageView(Uri imageUri) {
        Glide.with(this)
                .load(imageUri)
                .into(imageView);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // ======================================
        // create view
        // ======================================

        //root view
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        //Image view bind
        imageView = rootView.findViewById(R.id.profileImage);

        //email bind text view
        TextView emailAddressTextView = rootView.findViewById(R.id.emailAddressTextView);

        // initialize spinner
        colorSelector = rootView.findViewById(R.id.colorSpinner);

        // data stream button
        Button button = rootView.findViewById(R.id.dataStreamButton);

        // ======================================
        // create instance
        // ======================================

        UserManager userManager = UserManager.getInstance();

        //initialize imagePickLauncher
        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                Uri imageUri = result.getData().getData();
                UserManager.getInstance().uploadProfileImage(imageUri);
                setImageView(imageUri);
            }
        });

        String[] themeList = Arrays.stream(ThemeType.values()).map(Enum::toString).toArray(String[]::new);
        ArrayAdapter<String> themeAdapter = new ArrayAdapter<>(this.requireActivity(), android.R.layout.simple_list_item_1, themeList);

        // ======================================
        // bind view listener / callback / handler
        // ======================================

        imageView.setOnClickListener(v -> showImageOptions());

        // data stream start
        button.setOnClickListener(l -> {
            UserSimulator userSimulator = SingletonFactory.getInstance(UserSimulator.class);
            userSimulator.toggleStart();
            if (userSimulator.started) {
                button.setText("Stop Data Stream");
                Utils.showShortToast(getContext(), "data stream start!");
            } else {
                button.setText("Start Data Stream");
                Utils.showShortToast(getContext(), "data stream stop!");
            }
        });

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
                            Log.e("ProfileFragment", "Some problem may have occurred when selecting theme colour.");
                }
                rootView.setBackgroundColor(Color.parseColor(colorValue));
                EventBus.getDefault().post(new ThemeUpdateEvent(colorValue));
                ThemeColor.setThemeColor(colorValue);
                ThemeColor.writeTheme();    // write new color value into file
                // for bug avoidance, still set ThemeConfig
                MainActivity mainActivity = (MainActivity) getActivity();
                assert mainActivity != null;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });

        userManager.addObserver(user -> {
            setImageView(Uri.parse(user.imageUrl));
            emailAddressTextView.setText(userManager.user.username);
        });

        // ======================================
        // other initial code
        // ======================================

        getLocationAndUpdateAddress();
        emailAddressTextView.setText(userManager.user.username);

        // set spinner: set the selected element being the current theme
        colorSelector.setAdapter(themeAdapter);
        for (int i = 0; i < themeAdapter.getCount(); i++) {
            if (Objects.equals(themeAdapter.getItem(i), ThemeColor.getThemeName().toString())) {
                //
                colorSelector.setSelection(i);
                break;
            }
        }

        setImageView(Uri.parse(UserManager.getInstance().user.imageUrl));

        // set initial theme
        rootView.setBackgroundColor(Color.parseColor(ThemeColor.getThemeColor()));

        return rootView;
    }

    private void showImageOptions() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose");
        builder.setItems(new String[]{"upload image", "close"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        openImagePicker();
                        break;
                    case 1:
                        dialog.dismiss();
                        break;
                }
            }
        });
        builder.show();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UserManager.getInstance().addObserver(user -> {
            if (user.imageUrl != null) {
                updateLocalImageView(Uri.parse(user.imageUrl));
            }
        });
    }


    //get location
    private void getLocationAndUpdateAddress() {
        UserLocationManager locationManager = UserLocationManager.getInstance();
        locationManager.init((LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE));
        locationManager.getLocation(getActivity(), location -> {
            String locationString = locationManager.decodeLocation(getActivity(), location);
            TextView countryAddressTextView = rootView.findViewById(R.id.countryAddressTextView);
            countryAddressTextView.setText(locationString);
        });
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }

    private void updateLocalImageView(Uri imageUri) {
        imageView.setImageURI(imageUri);
    }
}