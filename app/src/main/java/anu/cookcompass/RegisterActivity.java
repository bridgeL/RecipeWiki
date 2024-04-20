package anu.cookcompass;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
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

            }
            else if (!password1.equals(password2)) {//check
                Toast.makeText(RegisterActivity.this, "The two password inputs do not match", Toast.LENGTH_LONG).show();
            } else {
                // Save account to users.json
                saveAccount(account, password1);
                Toast.makeText(RegisterActivity.this, "Registration successful, redirecting to login", Toast.LENGTH_SHORT).show();
                finish(); // Destroy the current activity and return to login page

            }
        });
    }


    //check account exist function TODO: intergrate this into database
    private boolean checkAccountExists(String account) {
        try {
            InputStream readUsers = getAssets().open("users.json");
            String usersJson;
            // use different methods for different version
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                usersJson = new String(readUsers.readAllBytes());
            } else {
                ByteArrayOutputStream result = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length;
                while ((length = readUsers.read(buffer)) != -1) {
                    result.write(buffer, 0, length);
                }
                usersJson = result.toString("UTF-8");
            }
            Gson usersGson = new Gson();
            //check user existence
            List<User> userList = usersGson.fromJson(usersJson, new TypeToken<List<User>>() {}.getType());
            for (User each : userList) {
                if (each.username.equals(account)) {
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //save account details into the json file
    private void saveAccount(String account, String password)  {
        try {
            InputStream readUsers = getAssets().open("users.json");
            String usersJson;
            // use different methods for different version
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                usersJson = new String(readUsers.readAllBytes());
            } else {
                ByteArrayOutputStream result = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length;
                while ((length = readUsers.read(buffer)) != -1) {
                    result.write(buffer, 0, length);
                }
                usersJson = result.toString("UTF-8");
            }
            Gson usersGson = new Gson();
            List<User> userList = usersGson.fromJson(usersJson, new TypeToken<List<User>>() {}.getType());
            User newUser=new User();
            newUser.username=account;
            newUser.password=password;
            newUser.collections=new ArrayList<>();
            userList.add(newUser);
            String updatedUserJson = usersGson.toJson(userList);
            // write the new user information into the file
            OutputStreamWriter writer = new OutputStreamWriter(openFileOutput("users.json", Context.MODE_PRIVATE));
            writer.write(updatedUserJson);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}