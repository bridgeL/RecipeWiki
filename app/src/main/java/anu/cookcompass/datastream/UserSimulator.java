package anu.cookcompass.datastream;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import anu.cookcompass.Utils;
import anu.cookcompass.model.PopMsg;
import anu.cookcompass.model.PopMsgType;
import anu.cookcompass.popmsg.PopMsgManager;

public class UserSimulator {
    public static void start() {
        Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                PopMsg popMsg = new PopMsg();
                popMsg.uid = "data stream";
                popMsg.username = "Bernardo Nunes";
                popMsg.rid = Utils.randInt(1, 2000);
                popMsg.type = PopMsgType.LIKE;
                popMsg.title = String.valueOf(popMsg.rid);
                popMsg.location = "ESCAPE ROOM";

                PopMsgManager.getInstance().pushPopMsg(popMsg);
//                popMsgManager.popMsgs.add(popMsg);
//                popMsgManager.notifyAllObservers(popMsgManager.popMsgs);
            }
        };
        t.schedule(tt, new Date(), 10000);
    }
}
