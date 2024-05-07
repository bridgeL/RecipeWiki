package anu.cookcompass.popmsg;

import android.util.Log;

import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.List;

import anu.cookcompass.firebase.CloudData;
import anu.cookcompass.pattern.Observer;
import anu.cookcompass.pattern.Subject;

public class PopMsgManager implements Subject<List<PopMsg>> {
    String TAG = "PopMsgManager";
    static PopMsgManager instance = null;

    public List<PopMsg> popMsgs = new ArrayList<>();
    CloudData<List<PopMsg>> cloudPopmsgsRef;
    List<Observer<List<PopMsg>>> observers = new ArrayList<>();

    @Override
    public List<Observer<List<PopMsg>>> getObservers() {
        return observers;
    }

    private PopMsgManager() {
        cloudPopmsgsRef = new CloudData<>(
                "v3/popmsg",
                new GenericTypeIndicator<List<PopMsg>>() {
                }
        );

        cloudPopmsgsRef.addObserver(cloudPopmsgs->{
            Log.d(TAG, "synchronize popmsgs successfully!");
            popMsgs = cloudPopmsgs;
            notifyAllObservers(popMsgs);
        });
    }

    public static PopMsgManager getInstance() {
        if (instance == null) instance = new PopMsgManager();
        return instance;
    }

    /**
     * add a pop message and upload it to cloud
     * @param popMsg pop message
     */
    public void pushPopMsg(PopMsg popMsg){
        popMsgs.add(0, popMsg);
        cloudPopmsgsRef.setValue(popMsgs);
    }
}
