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

import anu.cookcompass.theme.ThemeUpdateEvent;
import anu.cookcompass.popmsg.NotificationFragment;
import anu.cookcompass.search.SearchFragment;
import anu.cookcompass.user.ProfileFragment;

/**
 * @author u7759982,Jiangbei Zhang
 * This function is the main activity which aims to control the switch between pages
 * {@code @bottomNavigationView} This is the navigation bar to control the switch
 * {@code @searchFragment} This is the fragment of search page
 * {@code @profileFragment} This is the fragment of profile page
 * @notificationFragment This is the fragment of notification page
 */
public class MainActivity extends AppCompatActivity {//after login ,the application will turn to this page
    private BottomNavigationView bottomNavigationView;
    private Fragment searchFragment, profileFragment,notificationFragment;//three fragment to switch with
//    private ThemeConfig themeConfig;

    /**
     * when create the main activity, start three initial fragment,and switch the fragment depends on the item
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initialize three fragment
        searchFragment = new SearchFragment();
        profileFragment = new ProfileFragment();
        notificationFragment=new NotificationFragment();
        //include all used fragment into the transaction and commit it to implement to UI
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, searchFragment)
                .add(R.id.fragment_container, profileFragment)
                .add(R.id.fragment_container,notificationFragment)
                .hide(profileFragment)
                .hide(notificationFragment)
                .commit();

//        themeConfig=(ThemeConfig)getIntent().getSerializableExtra("themeConfig");
        bottomNavigationView = findViewById(R.id.navigation_bar);//bind with view by id
        bottomNavigationView.setOnItemSelectedListener(menuItem -> {//logic to switch between fragment
            if (menuItem.getItemId() == R.id.navigation_search) {
                replaceFragment(searchFragment);
                return true;
            } else if (menuItem.getItemId() == R.id.navigation_profile) {
                replaceFragment(profileFragment);
                return true;
            }
            else if(menuItem.getItemId() == R.id.navigation_notification){
                replaceFragment(notificationFragment);
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

    /**
     * @param fragment the fragment will switch
     *                 this function will switch fragment when click
     */
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();//get present fragmentManager
        FragmentTransaction transaction = fragmentManager.beginTransaction();//a new transaction
        if (fragment == searchFragment) {
            transaction.hide(profileFragment).hide(notificationFragment).show(searchFragment);
        } else if (fragment == profileFragment) {
            transaction.hide(searchFragment).hide(notificationFragment).show(profileFragment);
        }
        else if(fragment == notificationFragment) {
            transaction.hide(searchFragment).hide(profileFragment).show(notificationFragment);
        }
        transaction.commit();

    }

    public void updateTheme(String themeColor) {
//        themeConfig.setTheme(themeColor);
        searchFragment.requireView().setBackgroundColor(Color.parseColor(themeColor));
        profileFragment.requireView().setBackgroundColor(Color.parseColor(themeColor));
        notificationFragment.requireView().setBackgroundColor(Color.parseColor(themeColor));
    }

//    public ThemeConfig getThemeConfig() {
//        return themeConfig;
//    }
}
