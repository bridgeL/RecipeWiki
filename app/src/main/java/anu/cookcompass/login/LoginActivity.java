package anu.cookcompass.login;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import anu.cookcompass.MainActivity;
import anu.cookcompass.MyToast;
import anu.cookcompass.R;
import anu.cookcompass.Utils;
import anu.cookcompass.recipe.RecipeManager;
import anu.cookcompass.theme.ThemeColor;
import anu.cookcompass.theme.ThemeUpdateEvent;

/**
 * @author u7693070, Changlai Sun
 * @feature Login
 * The class is a Login front-end code
 */
public class LoginActivity extends AppCompatActivity {
    private EditText accountEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // ======================================
        // create view
        // ======================================

        accountEditText = findViewById(R.id.account);
        passwordEditText = findViewById((R.id.password));
        Button loginButton = findViewById(R.id.loginButton);
        Button registerButton = findViewById(R.id.registerButton);
        Button quickLoginButton = findViewById(R.id.quickLoginButton);

        // ======================================
        // create instance
        // ======================================

        Login login = Login.getInstance();

        // ======================================
        // bind view listener / callback / handler
        // ======================================

        //login click event
        loginButton.setOnClickListener(v -> {
            String account = accountEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            login.login(account, password, res -> {
                Utils.showShortToast(res.message);
                // if successful, show the search page (main page)
                if (res.successful) Utils.switchPage(this, MainActivity.class);
            });
        });

        //register click event
        registerButton.setOnClickListener(v -> {
            Utils.switchPage(this, RegisterActivity.class);
        });

        // quick login button click event
        quickLoginButton.setOnClickListener(v -> {
            login.login("comp6442@anu.edu.au", "comp6442", res -> {
                Utils.showShortToast(res.message);
                // if successful, show the search page (main page)
                if (res.successful) Utils.switchPage(this, MainActivity.class);
            });
        });

        // ======================================
        // other initial code
        // ======================================

        MyToast.getInstance().init(this.getApplicationContext());

        // recipes load
        RecipeManager.getInstance().loadRecipes(this);

        // register EventBus receiver
        EventBus.getDefault().register(this);

        // load theme from the file system
        ThemeColor.init(getApplicationContext());
        ThemeColor.loadTheme();
        this.getWindow().getDecorView().setBackgroundColor(Color.parseColor(ThemeColor.getThemeColor()));
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
     *
     * @param event The event object from EventBus
     */
    @Subscribe
    public void onMessageEvent(ThemeUpdateEvent event) {
        // extract color and set theme
        String color = event.getColorValue();
        this.getWindow().getDecorView().setBackgroundColor(Color.parseColor(color));
    }
}