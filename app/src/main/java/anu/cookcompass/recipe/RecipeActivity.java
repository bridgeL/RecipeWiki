package anu.cookcompass.recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import anu.cookcompass.R;
import anu.cookcompass.Utils;
import anu.cookcompass.gps.UserLocationManager;
import anu.cookcompass.pattern.SingletonFactory;
import anu.cookcompass.search.RecipeAdapter;
import anu.cookcompass.theme.ThemeUpdateEvent;
import anu.cookcompass.theme.ThemeColor;
import anu.cookcompass.user.UserManager;

/**
 * @author u7760022, Xinyang Li
 * @feature LoadShowData
 * The class controls the RecipeActivity and combine the data with view. front-end of LoadShowData
 */
public class RecipeActivity extends AppCompatActivity {
    Button likeButton;
    ImageView imageView;
    TextView recipeTitle, recipeText1, recipeText2, recipeText3, recipeText4, likeText, viewText;
    Recipe currentRecipe;

    private Bitmap getImageFromAssetsFile(String filePath) {
        try (InputStream is = getAssets().open(filePath)) {
            return BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        // get references of UI components
        likeButton = findViewById(R.id.LikeButton);
        imageView = findViewById(R.id.RecipePicture);
        recipeTitle = findViewById(R.id.RecipeTitle);
        recipeText1 = findViewById(R.id.SubTitle1);
        recipeText2 = findViewById(R.id.Content1);
        recipeText3 = findViewById(R.id.SubTitle2);
        recipeText4 = findViewById(R.id.Content2);
        likeText = findViewById(R.id.likeText);
        viewText = findViewById(R.id.viewText);

        // grab intent and retrieve Recipe object
        currentRecipe = RecipeManager.getInstance().currentRecipe;
        // set displays
        recipeTitle.setText(currentRecipe.title);
        // setup text to be displayed
        recipeText1.setText("Ingredients");
        recipeText2.setText(String.join("\n", currentRecipe.ingredients));
        recipeText3.setText("Instructions");
        recipeText4.setText("✨ " + String.join("\n\n✨ ", currentRecipe.instructions));

        Bitmap bitmap = getImageFromAssetsFile("Food Images/" + currentRecipe.imageName + ".jpg");
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            // Optionally set a default image if the image file is not found
            imageView.setImageResource(R.drawable.ic_launcher_background);
        }
        likeText.setText("Like: " + currentRecipe.like);
        viewText.setText("View: " + currentRecipe.view);

        if (UserManager.getInstance().hasLiked(currentRecipe)) {
            likeButton.setText("Unlike!");
        } else {
            likeButton.setText("Like!");
        }


        // set button callbacks
        likeButton.setOnClickListener(l -> {
            String location = UserLocationManager.getInstance().location;
            Recipe recipe = RecipeManager.getInstance().currentRecipe;
            boolean like = UserManager.getInstance().toggleLike(recipe, location);

            for (Recipe recipe1 : RecipeManager.getInstance().getRecipes()) {
                if (recipe1.rid == recipe.rid) {
                    recipe1.like = recipe.like;
                    likeText.setText("Like: " + recipe.like);
                    SingletonFactory.getInstance(RecipeAdapter.class).notifyDataSetChanged();
                    File file = new File(getFilesDir(), "recipe/recipe.json");
                    Utils.saveJson(file, RecipeManager.getInstance().getRecipes());
                    break;
                }
            }

            if (like) {
                likeButton.setText("Unlike!");
                Utils.showShortToast(this, "you like it successfully!");
            } else {
                likeButton.setText("Like!");
                Utils.showShortToast(this, "you cancel like successfully!");
            }
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
     *
     * @param event The event object from EventBus
     */
    @Subscribe
    public void onMessageEvent(ThemeUpdateEvent event) {
        // extract color and set theme
        String color = event.getColorValue();
        this.getWindow().getDecorView().setBackgroundColor(Color.parseColor(color));
    }
}