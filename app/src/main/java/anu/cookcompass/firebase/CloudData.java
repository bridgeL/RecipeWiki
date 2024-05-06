package anu.cookcompass.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import anu.cookcompass.pattern.Observer;
import anu.cookcompass.pattern.Subject;

public class CloudData<T> implements Subject<T> {
    String TAG = "CloudData";
    String path;
    GenericTypeIndicator<T> type;
    DatabaseReference ref;
    ValueEventListener valueEventListener;
    List<Observer<T>> observers = new ArrayList<>();
    boolean isListening = false;

    @Override
    public List<Observer<T>> getObservers() {
        return observers;
    }

    /**
     * upload data to cloud
     * @param data new value
     */
    public void upload(T data){
        ref.setValue(data).addOnSuccessListener(unused -> {
            Log.d(TAG, "upload data into \"" + path + "\" successfully!");
        }).addOnFailureListener(e->Log.e(TAG, e.getMessage()));
    }

    /**
     * add a listener when this ref call get() return data from cloud, only get data once
     *
     * @param observer callback function
     */
    public void getOnce(Observer<T> observer) {
        ref.get().addOnSuccessListener(dataSnapshot -> {
            T data = dataSnapshot.getValue(type);

            // don't get data
            if (data == null) {
                Log.w(TAG, "no data exists at \"" + path + "\"");
                return;
            }

            // notify the listener
            Log.d(TAG, "download data from \"" + path + "\" successfully! data: "
                    + data.toString().substring(0, 100));

            observer.onDataChange(data);
        }).addOnFailureListener(e -> Log.e(TAG, e.getMessage()));
    }

    /**
     * start persistent listening on this ref, every time the data changed, listeners will be notified
     */
    public void startListen() {
        if (isListening) return;
        isListening = true;
        ref.addValueEventListener(valueEventListener);
    }

    /**
     * stop persistent listening on this ref to release memory (avoid memory leak)
     */
    public void stopListen() {
        if (!isListening) return;
        isListening = false;
        ref.removeEventListener(valueEventListener);
    }

    public CloudData(String cloudPath, GenericTypeIndicator<T> cloudDataType, boolean startPersistentListen) {
        path = cloudPath;
        type = cloudDataType;
        ref = FirebaseDatabase.getInstance().getReference().child(path);
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                T data = dataSnapshot.getValue(type);

                // don't get data
                if (data == null) {
                    Log.e(TAG, "no data exists at \"" + path + "\"");
                    return;
                }

                // notify the listener
                Log.d(TAG, "find a cloud data change at \"" + path + "\"");

                // show new value
                String dataString = data.toString();
                dataString = dataString.length() > 100 ? dataString.substring(0, 100) : dataString;
                Log.d(TAG, dataString);
                notifyAllObservers(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, error.getMessage());
            }
        };
        if (startPersistentListen) startListen();
    }

    /**
     * create a cloud data reference to manage data synchronize
     * @param cloudPath data path
     * @param cloudDataType data type
     */
    public CloudData(String cloudPath, GenericTypeIndicator<T> cloudDataType) {
        this(cloudPath, cloudDataType, true);
    }
}
