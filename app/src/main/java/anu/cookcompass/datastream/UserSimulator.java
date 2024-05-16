package anu.cookcompass.datastream;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import anu.cookcompass.Utils;
import anu.cookcompass.pattern.SingletonFactory;
import anu.cookcompass.popmsg.PopMsg;
import anu.cookcompass.popmsg.PopMsgType;
import anu.cookcompass.popmsg.PopMsgManager;

/**
 * @author u7760022, Xinyang Li
 * @feature Data-stream
 * The class is a UserSimulator to simulate a user liking a recipe
 */
public class UserSimulator {

    /**
     * Gets the singleton instance of UserSimulator.
     * @return the singleton instance of UserSimulator
     */
    public static UserSimulator getInstance() {
        return SingletonFactory.getInstance(UserSimulator.class);
    }

    // Boolean flag to indicate if the simulator is started
    public boolean started = false;

    /**
     * Private constructor to initialize the UserSimulator.
     * Sets up a Timer to periodically simulate a user liking a recipe.
     */
    private UserSimulator() {
        Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                // If the simulator is not started, do nothing
                if (!started) return;

                // Create a new PopMsg to simulate a user liking a recipe
                PopMsg popMsg = new PopMsg(
                        "data stream",              // Source of the message
                        "Bernardo",                 // User name
                        Utils.randInt(1, 2000),     // Recipe ID (randomly generated)
                        "Escape room",              // Recipe title
                        "Barry Drive",              // Location
                        PopMsgType.LIKE,            // Type of the message (LIKE)
                        Utils.getTimestamp()        // Timestamp of the message
                );

                // Push the PopMsg to the PopMsgManager
                PopMsgManager.getInstance().pushPopMsg(popMsg);
            }
        };
        // Schedule the TimerTask to run every 10 seconds, starting from now
        t.schedule(tt, new Date(), 10000);
    }

    /**
     * Toggles the started state of the simulator.
     * If started, stops the simulator; if stopped, starts the simulator.
     */
    public void toggleStart() {
        started = !started;
    }
}
