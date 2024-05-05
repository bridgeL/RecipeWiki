package anu.cookcompass.gps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.Manifest;
import android.provider.Settings;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import anu.cookcompass.MainActivity;
import anu.cookcompass.R;
import anu.cookcompass.model.ThemeConfig;


//This is the Data-GPS feature

//Interface LocationService defines a method to get the user's location.
public interface LocationService {

    //Nested interface LocationCallback contains the currentLocation method to receive the retrieved location
    interface LocationCallback {
        void locationRetrieved(Location location); //This method receives the retrieved location
    }

    //This method provides a way for the method caller (usually Activity) to be notified when the location has been retrieved
    public void getLocation(Context context, LocationCallback callback);
}

