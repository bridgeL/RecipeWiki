package anu.cookcompass;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import anu.cookcompass.login.Register;

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

            Register.getInstance().register(account, password1, password2).thenAccept(res -> {
                Utils.showLongToast(this, res.message);
                if (res.successful)
                    finish(); // Destroy the current activity and return to login page
            });
        });
    }
}