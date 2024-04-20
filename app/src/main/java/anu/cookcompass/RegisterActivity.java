package anu.cookcompass;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import anu.cookcompass.model.User;

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
            boolean accountExists = checkAccountExists(account);
            if (accountExists) {//check exist, if exist toast
                Toast.makeText(RegisterActivity.this, "User already exists, please log in directly", Toast.LENGTH_LONG).show();

            } else if (!password1.equals(password2)) {//check
                Toast.makeText(RegisterActivity.this, "The two password inputs do not match", Toast.LENGTH_LONG).show();
            } else {
                // Save account to users.json
                saveAccount(account, password1);
                Toast.makeText(RegisterActivity.this, "Registration successful, redirecting to login", Toast.LENGTH_SHORT).show();
                finish(); // Destroy the current activity and return to login page

            }
        });
    }


    //check account exist function
    private boolean checkAccountExists(String account) {
        File userFile = Utils.copyFileFromAssets(this, "users.json");
        List<User> userList = Utils.readJson(userFile, new TypeToken<>() {
        });
        if (userList != null) {
            for (User each : userList) {
                if (each.username.equals(account)) {
                    return true;
                }
            }
        }
        return false;
    }

    //save account details into the json file
    private void saveAccount(String account, String password) {
        File userFile = Utils.copyFileFromAssets(this, "users.json");
        List<User> userList = Utils.readJson(userFile, new TypeToken<>() {
        });
        if (userList == null) {
            userList = new ArrayList<>();
        }
        User newUser = new User();
        newUser.username = account;
        newUser.password = password;
        newUser.collections = new ArrayList<>();
        userList.add(newUser);
        Utils.saveJson(userFile, userList);
    }


}