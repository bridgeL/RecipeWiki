package anu.cookcompass.datastream;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import anu.cookcompass.Utils;
import anu.cookcompass.pattern.SingletonFactory;
import anu.cookcompass.popmsg.PopMsg;
import anu.cookcompass.popmsg.PopMsgType;
import anu.cookcompass.popmsg.PopMsgManager;

public class UserSimulator {
    public static UserSimulator getInstance() {
        return SingletonFactory.getInstance(UserSimulator.class);
    }

    public boolean started = false;

    private UserSimulator() {
        Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                if (!started) return;

                PopMsg popMsg = new PopMsg();
                popMsg.uid = "data stream";
                popMsg.username = "Bernardo";
                popMsg.rid = Utils.randInt(1, 2000);
                popMsg.type = PopMsgType.LIKE;
                popMsg.title = String.valueOf(popMsg.rid);
                popMsg.location = "comp6442";
                popMsg.timestamp = Utils.getTimestamp();

                PopMsgManager.getInstance().pushPopMsg(popMsg);
            }
        };
        t.schedule(tt, new Date(), 10000);
    }

    public void toggleStart() {
        started = !started;
    }
}
