package anu.cookcompass;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {
    private Button settingButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile,container,false);
        settingButton=view.findViewById(R.id.settingButton);
        settingButton.setOnClickListener(v -> {
            String colorValue="#FFB241";
            changeMainBackgroundColor(colorValue);
        });

        return view;
    }

    public void changeMainBackgroundColor(String colorValue) {
        if (getActivity() instanceof MainActivity mainActivity) {
            mainActivity.changeBackgroundColor(colorValue);
        }
    }
}