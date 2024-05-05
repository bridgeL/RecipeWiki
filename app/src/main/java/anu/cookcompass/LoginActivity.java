package anu.cookcompass;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import anu.cookcompass.broadcast.ThemeUpdateEvent;
import anu.cookcompass.database.Database;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;

import anu.cookcompass.firebase.Database;
import anu.cookcompass.login.Login;
import anu.cookcompass.model.ThemeColor;
import anu.cookcompass.login.Register;
import anu.cookcompass.model.Global;
import anu.cookcompass.model.User;
import anu.cookcompass.recipe.RecipeManager;
import anu.cookcompass.user.UserManager;
import anu.cookcompass.model.ThemeConfig;

public class LoginActivity extends AppCompatActivity {
    private EditText accountEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // initial code
        // compulsory
        Global.getInstance(this);
        Database.getInstance();
        RecipeManager.getInstance();
        UserManager.getInstance();

        // optional
        Login.getInstance();
        Register.getInstance();
        FirebaseAuth.getInstance();
        FirebaseDatabase.getInstance();
        FirebaseStorage.getInstance();

        // TODO: main entrance, initialization code write down here
        Button loginButton = findViewById(R.id.loginButton);
        accountEditText = findViewById(R.id.account);
        passwordEditText = findViewById((R.id.password));
        TextView notRegisteredTextView = findViewById(R.id.notRegisteredTextView);

        //login click event
        loginButton.setOnClickListener(v -> {
            String account = accountEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            Login.getInstance().login(account, password).thenAccept(res -> {
                if (res.successful) {
                    //if successful, show the search page (main page)
                    ThemeConfig themeConfig=new ThemeConfig(account,"","#FFB241");
                    Utils.showShortToast(this, res.message);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("themeConfig", themeConfig);
                    startActivity(intent);
//                    Utils.switchPage(this, MainActivity.class);
                } else {// if not correct, depends on the message, show the error hint
                    Utils.showLongToast(this, res.message);
                }
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