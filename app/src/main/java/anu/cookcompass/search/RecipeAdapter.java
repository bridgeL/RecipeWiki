package anu.cookcompass.search;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import anu.cookcompass.R;
import anu.cookcompass.recipe.Recipe;

public class RecipeAdapter extends ArrayAdapter<Recipe> {
    public RecipeAdapter(Context context, List<Recipe> recipes) {
        super(context, R.layout.list_item, recipes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView listName = convertView.findViewById(R.id.listName);
        ImageView listImage = convertView.findViewById(R.id.listImage);

        Recipe recipe = getItem(position);
        listName.setText(recipe.title);

        Bitmap bitmap = getImageFromAssetsFile("Food Images/" + recipe.imageName + ".jpg");
        if (bitmap != null) {
            listImage.setImageBitmap(bitmap);
        } else {
            // Optionally set a default image if the image file is not found
            listImage.setImageResource(R.drawable.ic_launcher_background);
        }

        return convertView;
    }

    private Bitmap getImageFromAssetsFile(String filePath) {
        try (InputStream is = getContext().getAssets().open(filePath)) {
            return BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}