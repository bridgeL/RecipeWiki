package anu.cookcompass;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import anu.cookcompass.model.Global;
import anu.cookcompass.model.Recipe;

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

        // search recipes
        List<Recipe> recipes = global.database.searchRecipes("test...");
        System.out.println(recipes.get(0).title);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword("comp6442@anu.edu.au", "comp6442").addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.e("Jis", ">>>     jsifjsdlkf <<<", exception);
            }
        }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                Log.w("warning","df successfully!");
                try {

                    int i = 0;
                    Recipe r = global.database.getRecipeById(i);
                    InputStream inputStream = getAssets().open("Food Images/" + r.imageName + ".jpg");
                    System.out.println(i + " " + r.imageName);
                    CompletableFuture<String> future = global.storage.uploadStream("/recipe_images/" + r.imageName + ".jpg", inputStream);
                } catch (Exception e) {
                }
            }
        });


    }
}