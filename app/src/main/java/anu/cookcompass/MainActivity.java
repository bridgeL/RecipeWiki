package anu.cookcompass;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

import anu.cookcompass.broadcast.ThemeUpdateEvent;
import anu.cookcompass.model.ThemeColor;
import anu.cookcompass.model.ThemeConfig;

/**
 * This function is the main activity which aims to control the switch between pages
 * {@code @bottomNavigationView} This is the navigation bar to control the switch
 * {@code @searchFragment} This is the fragment of search page
 * {@code @profileFragment} This is the fragment of profile page
 */
public class MainActivity extends AppCompatActivity {//after login ,the application will turn to this page
    private BottomNavigationView bottomNavigationView;
    private Fragment searchFragment, profileFragment;//two fragment to switch with
    private ThemeConfig themeConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchFragment = new SearchFragment();
        profileFragment = new ProfileFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, searchFragment)
                .add(R.id.fragment_container, profileFragment)
                .hide(profileFragment)
                .commit();

        themeConfig=(ThemeConfig)getIntent().getSerializableExtra("themeConfig");
        bottomNavigationView = findViewById(R.id.navigation_bar);//bind with view by id
        bottomNavigationView.setOnItemSelectedListener(menuItem -> {//logic to switch between fragment
            if (menuItem.getItemId() == R.id.navigation_search) {
                replaceFragment(searchFragment);
                return true;
            } else if (menuItem.getItemId() == R.id.navigation_profile) {
                replaceFragment(profileFragment);
                return true;
            }
            return false;
        });

        replaceFragment(searchFragment);

        // register EventBus receiver
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // destroy EventBus receiver
        EventBus.getDefault().unregister(this);
    }

    /**
     * The function that updates the theme color of this page when receiving event from
     * the EventBus.
     * @param event The event object from EventBus
     */
    @Subscribe
    public void onMessageEvent(ThemeUpdateEvent event) {
        // extract color and set theme
        String color = event.getColorValue();
        updateTheme(color);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragment == searchFragment) {
            transaction.hide(profileFragment).show(searchFragment);
        } else if (fragment == profileFragment) {
            transaction.hide(searchFragment).show(profileFragment);
        }
        transaction.commit();

    }

    public void updateTheme(String themeColor) {
        themeConfig.setTheme(themeColor);
        searchFragment.requireView().setBackgroundColor(Color.parseColor(themeColor));
        profileFragment.requireView().setBackgroundColor(Color.parseColor(themeColor));
    }

    public ThemeConfig getThemeConfig() {
        return themeConfig;
    }
}
