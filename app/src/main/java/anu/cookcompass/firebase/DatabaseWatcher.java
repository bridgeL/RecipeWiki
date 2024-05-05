package anu.cookcompass.firebase;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DatabaseWatcher {
//    String TAG = getClass().getSimpleName();
//    String cloudPath;
//    public DatabaseReference ref;
//    List<Observer> observers;
//    ValueEventListener listener;
//
//    public DatabaseWatcher() {
//        cloudPath = "";
//        ref = null;
//        observers = new ArrayList<>();
//        DatabaseWatcher that = this;
//        listener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.d(TAG, "notice: data from \"" + that.cloudPath + "\" has been updated!");
//                for (Observer observer : observers) {
//                    observer.notify(dataSnapshot);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Getting Post failed, log a message
//                Log.e(TAG, "load " + that.cloudPath + " Cancelled", databaseError.toException());
//            }
//        };
//    }
//
//    public DatabaseWatcher(String cloudPath) {
//        this();
//        setCloudPath(cloudPath);
//    }
//
//    public DatabaseWatcher(String cloudPath, Observer observer) {
//        this();
//        setCloudPath(cloudPath);
//        attach(observer);
//    }
//
//    public void clearCloudPath() {
//        if (ref != null) ref.removeEventListener(listener);
//        cloudPath = "";
//        ref = null;
//    }
//
//    public void setCloudPath(String cloudPath) {
//        clearCloudPath();
//        this.cloudPath = cloudPath;
//        ref = FirebaseDatabase.getInstance().getReference().child(cloudPath);
//        ref.addValueEventListener(listener);
//    }
//
//    public void attach(Observer observer) {
//        observers.add((observer));
//    }
//
//    public void detach(Observer observer) {
//        observers.remove((observer));
//    }
}
