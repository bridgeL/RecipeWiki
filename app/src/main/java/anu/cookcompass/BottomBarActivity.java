package anu.cookcompass;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomBarActivity extends AppCompatActivity {
    private TextView bottomNavTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_bar);

        BottomNavigationView bottomNavView = findViewById(R.id.navigation_bar);
        bottomNavTextView=findViewById(R.id.text_message);

        bottomNavView.setOnItemSelectedListener(menuItem -> {
           int itemId=menuItem.getItemId();
            if (itemId == R.id.navigation_home) {
                bottomNavTextView.setText(R.string.title_home);
                return true;
            } else if (itemId == R.id.navigation_search) {
                bottomNavTextView.setText(R.string.title_Search);
                return true;
            } else if (itemId == R.id.navigation_profile) {
                bottomNavTextView.setText(R.string.title_profile);
                return true;
            }
            return false;
        });


    }


}