package anu.cookcompass;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import anu.cookcompass.login.Login;
import anu.cookcompass.login.Response;
import anu.cookcompass.model.Global;
import anu.cookcompass.model.Ingredient;

public class LoginActivity extends AppCompatActivity {
    private EditText accountEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // TODO: main entrance, initialization code write down here
        Button loginButton = findViewById(R.id.loginButton);
        accountEditText=findViewById(R.id.account);
        passwordEditText=findViewById((R.id.password));

        // init global module
        Global.init(this);
        Global global = Global.getInstance();
        // switch night mode
        global.config.setNight(this, true); // TODO: BUG: When config.setNight(!config.isNight()), the app will drop in restart loop.

        //login message
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account= accountEditText.getText().toString();
                String password=passwordEditText.getText().toString();
                Login loginModule=new Login();
                Response res=loginModule.login(account,password);
                if (true){
                    //if successful, show the search page (main page)
                    Toast.makeText(LoginActivity.this, res.message, Toast.LENGTH_SHORT).show();

                }
                else {// if not correct, depends on the message, show the error hint
                    Toast.makeText(LoginActivity.this,res.message,Toast.LENGTH_LONG).show();

                }
            }
        });

        // search ingredients
                List<Ingredient> ingredients = global.database.searchIngredients("test...");
    }
}