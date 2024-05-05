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
import anu.cookcompass.login.Login;
import anu.cookcompass.model.ThemeConfig;

public class LoginActivity extends AppCompatActivity {
    private EditText accountEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // initial code
        Database.getInstance();


        // TODO: main entrance, initialization code write down here
        Button loginButton = findViewById(R.id.loginButton);
        accountEditText = findViewById(R.id.account);
        passwordEditText = findViewById((R.id.password));
        TextView notRegisteredTextView = findViewById(R.id.notRegisteredTextView);

        //login click event
        loginButton.setOnClickListener(v -> {
            String account = accountEditText.getText().toString();
            String password = passwordEditText.getText().toString();

//            account = "comp6442@anu.edu.au";
//            password = "comp6442";

            Login.login(account, password).thenAccept(res -> {
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