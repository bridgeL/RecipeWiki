package anu.cookcompass;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.internal.Util;

import java.util.List;

import anu.cookcompass.login.Login;
import anu.cookcompass.login.Response;
import anu.cookcompass.model.Global;
import anu.cookcompass.model.Recipe;

public class LoginActivity extends AppCompatActivity {
    private EditText accountEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // TODO: main entrance, initialization code write down here
        Button loginButton = findViewById(R.id.loginButton);
        accountEditText = findViewById(R.id.account);
        passwordEditText = findViewById((R.id.password));
        TextView notRegisteredTextView = findViewById(R.id.notRegisteredTextView);

        //login click event
        loginButton.setOnClickListener(v -> {
            String account = accountEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            Login.login(account, password).thenAccept(res -> {
                if (res.successful) {
                    //if successful, show the search page (main page)
                    Utils.showShortToast(this, res.message);
                } else {// if not correct, depends on the message, show the error hint
                    Utils.showLongToast(this, res.message);
                }
            });
        });

        //register click event
        notRegisteredTextView.setOnClickListener(v -> {
            Utils.switchPage(this, RegisterActivity.class);
        });
    }
}