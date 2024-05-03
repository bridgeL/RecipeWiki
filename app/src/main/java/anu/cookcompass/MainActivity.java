package anu.cookcompass;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * This function is the main activity which aims to control the switch between pages
 * {@code @bottomNavigationView} This is the navigation bar to control the switch
 * {@code @searchFragment} This is the fragment of search page
 * {@code @profileFragment} This is the fragment of profile page
 */
public class MainActivity extends AppCompatActivity {//after login ,the application will turn to this page
    private BottomNavigationView bottomNavigationView;
    private Fragment searchFragment, profileFragment;//two fragment to switch with

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.navigation_bar);//bind with view by id
        bottomNavigationView.setOnItemSelectedListener(menuItem -> {//logic to switch between fragment
            if (menuItem.getItemId()==R.id.navigation_search){
                replaceFragment(searchFragment);
                return true;
            } else if (menuItem.getItemId()==R.id.navigation_profile) {
                replaceFragment(profileFragment);
                return true;
            }
            return false;
        });
        searchFragment=new SearchFragment();
        profileFragment=new ProfileFragment();
        replaceFragment(searchFragment);

    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container,fragment);
        transaction.commit();
    }
    public void changeBackgroundColor(String colorValue){
        View mainView = findViewById(android.R.id.content);
        mainView.setBackgroundColor(Color.parseColor(colorValue));
    }
}
