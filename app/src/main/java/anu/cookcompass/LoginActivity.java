package anu.cookcompass;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

import anu.cookcompass.firebase.Storage;
import anu.cookcompass.login.Login;
import anu.cookcompass.model.Response;

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
            account = "nightcat@test.com";
            password = "nightcat";

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
//            Utils.switchPage(this, RegisterActivity.class);
            // test
            String[] fileNames;
            try {
                fileNames = getAssets().list("Food Images");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Log.e("", fileNames[0]);

            CompletableFuture<Response> future = CompletableFuture.completedFuture(null);
            for (int i = 5; i < fileNames.length; i++) {
                final int j = i;
                future = future.thenCompose(res -> {
                    Log.e("", "start upload: [" + j + "]" + fileNames[j]);
                    InputStream inputStream;
                    try {
                        inputStream = getAssets().open("Food Images/" + fileNames[j]);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return Storage.uploadStream("/Food Images/" + fileNames[j], inputStream);
                });
            }
        });
    }
}