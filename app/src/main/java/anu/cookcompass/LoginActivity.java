package anu.cookcompass;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import anu.cookcompass.broadcast.ThemeUpdateEvent;
import anu.cookcompass.login.Login;
import anu.cookcompass.model.ThemeColor;
import anu.cookcompass.model.ThemeConfig;
import anu.cookcompass.recipe.RecipeManager;
import anu.cookcompass.user.UserManager;

public class LoginActivity extends AppCompatActivity {
    String TAG = getClass().getSimpleName();
    private EditText accountEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // initial code
        // compulsory
        RecipeManager.getInstance().loadRecipes(this);
        UserManager.getInstance().loadUsers();

        Button loginButton = findViewById(R.id.loginButton);
        accountEditText = findViewById(R.id.account);
        passwordEditText = findViewById((R.id.password));
        Button notRegisteredTextView = findViewById(R.id.registerButton);

        //login click event
        loginButton.setOnClickListener(v -> {
//            String account = accountEditText.getText().toString();
//            String password = passwordEditText.getText().toString();

            String account = "test@163.com";
            String password = "test1234";

            Login.getInstance().login(account, password).thenAccept(res -> {
                if (res.successful) {
                    //if successful, show the search page (main page)
                    UserManager.getInstance().start();

                    ThemeConfig themeConfig = new ThemeConfig(account, "", "#FFB241");
                    Utils.showShortToast(this, res.message);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("themeConfig", themeConfig);
                    startActivity(intent);
                } else {// if not correct, depends on the message, show the error hint
                    Utils.showLongToast(this, res.message);
                }
            }).exceptionally(e -> {
                Log.e(TAG, e.getMessage());
                return null;
            });
        });

        //register click event
        notRegisteredTextView.setOnClickListener(v -> {
            Utils.switchPage(this, RegisterActivity.class);
        });

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
     * @param event The event object from EventBus
     */
    @Subscribe
    public void onMessageEvent(ThemeUpdateEvent event) {
        // extract color and set theme
        String color = event.getColorValue();
        this.getWindow().getDecorView().setBackgroundColor(Color.parseColor(color));
    }
}