package anu.cookcompass.gps;

import android.content.Context;
import android.location.Location;


//This is the Data-GPS feature

//Interface LocationService defines a method to get the user's location.

/**
 * @author u7754676, Tashia Tamara
 * @feature Data-GPS
 * The class is a GPS interface
 */
public interface LocationService {

    //Nested interface LocationCallback contains the currentLocation method to receive the retrieved location
    interface LocationCallback {
        void locationRetrieved(Location location); //This method receives the retrieved location
    }

    //This method provides a way for the method caller (usually Activity) to be notified when the location has been retrieved
    public void getLocation(Context context, LocationCallback callback);
}

