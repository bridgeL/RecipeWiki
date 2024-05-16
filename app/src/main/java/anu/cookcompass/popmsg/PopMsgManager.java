package anu.cookcompass.popmsg;

import android.util.Log;

import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.List;

import anu.cookcompass.firebase.CloudData;
import anu.cookcompass.pattern.Observer;
import anu.cookcompass.pattern.SingletonFactory;
import anu.cookcompass.pattern.Subject;

/**
 * Manages pop-up messages for the notification page.
 * Implements the Subject pattern to notify observers of changes to the pop-up messages.
 *
 * @autor u7760022, Xinyang Li
 */
public class PopMsgManager implements Subject<List<PopMsg>> {
    private static final String TAG = "PopMsgManager";

    // List of pop-up messages
    public List<PopMsg> popMsgs = new ArrayList<>();

    // Reference to pop-up messages in the cloud
    private CloudData<List<PopMsg>> cloudPopmsgsRef;

    // List of observers to notify on pop-up message changes
    private List<Observer<List<PopMsg>>> observers = new ArrayList<>();

    @Override
    public List<Observer<List<PopMsg>>> getObservers() {
        return observers;
    }

    /**
     * Private constructor to prevent instantiation.
     * Initializes the CloudData reference for pop-up messages and sets up synchronization.
     */
    private PopMsgManager() {
        cloudPopmsgsRef = new CloudData<>(
                "v3/popmsg",
                new GenericTypeIndicator<List<PopMsg>>() {}
        );

        cloudPopmsgsRef.addObserver(cloudPopmsgs -> {
            Log.d(TAG, "Synchronize popmsgs successfully!");
            popMsgs = cloudPopmsgs;
            notifyAllObservers(popMsgs);
        });
    }

    /**
     * Returns the singleton instance of PopMsgManager.
     * If the instance is null, a new instance is created.
     *
     * @return the singleton instance of PopMsgManager
     */
    public static PopMsgManager getInstance() {
        return SingletonFactory.getInstance(PopMsgManager.class);
    }

    /**
     * Adds a pop-up message to the list and uploads it to the cloud.
     *
     * @param popMsg the pop-up message to add
     */
    public void pushPopMsg(PopMsg popMsg) {
        popMsgs.add(0, popMsg); // Add the new message at the beginning of the list
        cloudPopmsgsRef.setValue(popMsgs); // Upload the updated list to the cloud
    }
}
