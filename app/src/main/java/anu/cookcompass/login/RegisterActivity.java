package anu.cookcompass.login;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import anu.cookcompass.R;
import anu.cookcompass.Utils;
import anu.cookcompass.theme.ThemeUpdateEvent;
import anu.cookcompass.theme.ThemeColor;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ImageButton backButton = findViewById(R.id.backButton);
        //back function
        backButton.setOnClickListener(v -> {
            finish();  // finish present activity and return to login
        });
        //judge function for register
        EditText firstPassword = findViewById(R.id.firstRegisterPassword);
        EditText secondPassword = findViewById(R.id.secondRegisterPassword);
        EditText registeredAccount = findViewById(R.id.registerAccount);
        Button registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(v -> {
            //initialize the variables
            String account = registeredAccount.getText().toString();
            String password1 = firstPassword.getText().toString();
            String password2 = secondPassword.getText().toString();

            Register.getInstance().register(account, password1, password2, res -> {
                Utils.showShortToast(this, res.message);
                if (res.successful)
                    finish(); // Destroy the current activity and return to login page
            });
        });

        // register EventBus receiver
        EventBus.getDefault().register(this);
        // set theme
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