package anu.cookcompass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.metrics.Event;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import anu.cookcompass.broadcast.ThemeUpdateEvent;
import anu.cookcompass.model.Recipe;
import anu.cookcompass.model.ThemeColor;
import anu.cookcompass.recipe.RecipeManager;

public class RecipeActivity extends AppCompatActivity {
    Button likeButton, collectButton;
    ImageView imageView;
    TextView recipeTitle, recipeText;
    Recipe currentRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        // get references of UI components
        likeButton = findViewById(R.id.LikeButton);
        collectButton = findViewById(R.id.CollectButton);
        imageView = findViewById(R.id.RecipePicture);
        recipeTitle = findViewById(R.id.RecipeTitle);
        recipeText = findViewById(R.id.RecipeText);

        // grab intent and retrieve Recipe object
        Intent intent = getIntent();
        int recipeID = intent.getIntExtra("RecipeID", -1);
        currentRecipe = RecipeManager.getInstance().getRecipes().get(recipeID);
        // set displays
        recipeTitle.setText(currentRecipe.title);
        // setup text to be displayed
        StringBuilder sb = new StringBuilder();
        sb.append("Ingredients: \n");
        for(String s: currentRecipe.ingredients){
            sb.append(s).append("; ");
        }
        sb.append("\n\nInstructions: \n");
        for(String s: currentRecipe.instructions){
            sb.append(s).append("\n");
        }
        recipeText.setText(sb.toString());


        // set button callbacks
        likeButton.setOnClickListener(l ->{

        });
        collectButton.setOnClickListener(l ->{

        });

        // register EventBus receiver
        EventBus.getDefault().register(this);
        // set initial theme
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