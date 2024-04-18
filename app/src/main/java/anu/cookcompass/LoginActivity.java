package anu.cookcompass;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import anu.cookcompass.model.Global;
import anu.cookcompass.model.Ingredient;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // TODO: main entrance, initialization code write down here

        // init global module
        Global.init(this);
        Global global = Global.getInstance();

        // switch night mode
        global.config.setNight(this, true); // TODO: BUG: When config.setNight(!config.isNight()), the app will drop in restart loop.

        // search ingredients
        List<Ingredient> ingredients = global.database.searchIngredients("test...");
    }
}