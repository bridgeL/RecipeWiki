package anu.cookcompass.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

import anu.cookcompass.MainActivity;
import anu.cookcompass.R;
import anu.cookcompass.Utils;
import anu.cookcompass.broadcast.ThemeUpdateEvent;
import anu.cookcompass.gps.UserLocationManager;
import anu.cookcompass.model.ThemeColor;
import anu.cookcompass.model.ThemeConfig;
import anu.cookcompass.user.User;
import anu.cookcompass.pattern.Observer;
import anu.cookcompass.user.UserManager;

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
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        //on create change the color
        ThemeConfig themeConfig = ((MainActivity) requireActivity()).getThemeConfig();
        rootView.setBackgroundColor(Color.parseColor(themeConfig.getTheme()));

        getLocationAndUpdateAddress();
//Image view bind
        imageView = rootView.findViewById(R.id.profileImage);
        imageView.setOnClickListener(v -> showImageOptions());
        //email bind text view
        TextView emailAddressTextView = rootView.findViewById(R.id.emailAddressTextView);
        emailAddressTextView.setText(themeConfig.getAccount());
        System.out.println(getActivity());
//initialize imagePickLauncher
        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        UserManager.getInstance().uploadProfileImage(imageUri);
                        setImageView(imageUri);
                    }
                });
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

        setImageView(Uri.parse(UserManager.getInstance().user.imageUrl));

        UserManager.getInstance().addObserver(user -> {
            setImageView(Uri.parse(user.imageUrl));
        });

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
        ThemeConfig themeConfig = ((MainActivity) requireActivity()).getThemeConfig();
        view.setBackgroundColor(Color.parseColor(themeConfig.getTheme()));
        UserManager.getInstance().addObserver(new Observer<>() {
            @Override
            public void onDataChange(User user) {
                if (user.imageUrl != null) {
                    updateLocalImageView(Uri.parse(user.imageUrl));
                }
            }
        });

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


    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }

    private void updateLocalImageView(Uri imageUri) {
        imageView.setImageURI(imageUri);
    }

    /**
     * @param imageUri the image uri generated by image selection
     * @return a file path
     */
    private String getFilePath(Uri imageUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(imageUri, projection, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            return filePath;
        }
        return null;
    }


    public enum ThemeType {
        Default,
        Gold,
        White
    }
}