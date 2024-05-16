package anu.cookcompass.gps;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import anu.cookcompass.pattern.SingletonFactory;


/**
 * @author u7754676, Tashia Tamara
 * @feature Data-GPS
 * The class is a GPS manager
 */
public class UserLocationManager implements LocationService {
    LocationManager locationManager;
    LocationListener locationListener;
    public String location = "unknown";
    private String lastKnownLocation;

    public static UserLocationManager getInstance() {
        return SingletonFactory.getInstance(UserLocationManager.class);
    }

    //Initialize the instance variable locationManager (this.locationManager) with the provided LocationManager instance
    public void init(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

    //Private constructor of UserLocationManager class
    private UserLocationManager() {
    }

    @Override
    public void getLocation(Context context, LocationCallback callback) {

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location1) {
                //Passes a Location object to the locationRetrieved method and lets the caller receive and handle the updated location
                callback.locationRetrieved(location1);
                //Stop updates after the initial location is retrieved
                locationManager.removeUpdates(locationListener);
                String address =decodeLocation(context,location1);
                if (!address.isEmpty()){
                    location=address;
                    lastKnownLocation =address;
                }
            }

            //If location services are disabled, launch the location settings so the user can enable them
            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        };

        //Check for permissions
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {

            //Request permissions if any of the permissions have not been granted
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}, 10);
            return; //Return statement causes early exit from the getLocation method to prevent the rest of the code from executing
        }

        //Try getting location updates using GPS
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
        }
        //Try getting location updates using network
        else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
        }
        //If both fail, get last known location
        else {
            location = lastKnownLocation;
        }
    }

    //Convert longitude and latitude to country and area names
    public static String decodeLocation(Context context, Location location) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            assert addresses != null;
            if (!addresses.isEmpty()) {
                String countryName = addresses.get(0).getCountryName();
                String adminArea = addresses.get(0).getAdminArea();
                return String.format("%1$s, %2$s", countryName, adminArea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
