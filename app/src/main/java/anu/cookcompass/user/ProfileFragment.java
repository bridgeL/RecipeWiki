package anu.cookcompass.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
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

import java.util.Objects;

import anu.cookcompass.MainActivity;
import anu.cookcompass.R;
import anu.cookcompass.Utils;
import anu.cookcompass.datastream.UserSimulator;
import anu.cookcompass.gps.UserLocationManager;
import anu.cookcompass.theme.ThemeColor;
import anu.cookcompass.theme.ThemeUpdateEvent;

/**
 * @author u7759982,Jiangbei Zhang
 * @feature Data_Profile
 * This method decides the logic in profileFragment, including display email address,
 * locaiton and profile image
 */
public class ProfileFragment extends Fragment {
    private View rootView;
    private Spinner colorSelector;
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
                Uri file = result.getData().getData();
                UserManager.getInstance().uploadProfileImage(file);
//                Log.e("JUSTDEBUG", imageUri.toString());
//                setImageView(Uri.parse(imageUri.toString()));
                setImageView(file);
            }
        });

        String[] themeList = ThemeColor.getThemeList();
        ArrayAdapter<String> themeAdapter = new ArrayAdapter<>(this.requireActivity(), android.R.layout.simple_list_item_1, themeList);

        // ======================================
        // bind view listener / callback / handler
        // ======================================

        imageView.setOnClickListener(v -> showImageOptions());

        // data stream start
        button.setOnClickListener(l -> {
            UserSimulator userSimulator = UserSimulator.getInstance();
            userSimulator.toggleStart();
            if (userSimulator.started) {
                button.setText("Stop Data Stream");
                Utils.showShortToast("data stream start!");
            } else {
                button.setText("Start Data Stream");
                Utils.showShortToast("data stream stop!");
            }
        });

        // listeners of spinner
        colorSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // change theme color
                String themeName = (String) colorSelector.getSelectedItem();
                String colorValue = ThemeColor.findColorByName(themeName);  // retrieve color value
                rootView.setBackgroundColor(Color.parseColor(colorValue));
                EventBus.getDefault().post(new ThemeUpdateEvent(colorValue));
                ThemeColor.setThemeColor(colorValue);
                ThemeColor.writeTheme();    // write new color value into file
                MainActivity mainActivity = (MainActivity) getActivity();
                assert mainActivity != null;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });

        userManager.addObserver(user -> {
//            Log.e("JUSTDEBUG", user.imageUrl);
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
            if (Objects.equals(themeAdapter.getItem(i), ThemeColor.getThemeName())) {
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

    /**
     * This function will show a new window with two options, one to upload image, another to close the window
     */
    private void showImageOptions() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose");
        builder.setItems(new String[]{"upload image", "close"}, (dialog, which) -> {
            switch (which) {
                case 0:
                    openImagePicker();
                    break;
                case 1:
                    dialog.dismiss();
                    break;
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

    /**
     * open image picker of local resource manager
     */
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }

    private void updateLocalImageView(Uri imageUri) {
        imageView.setImageURI(imageUri);
    }
}