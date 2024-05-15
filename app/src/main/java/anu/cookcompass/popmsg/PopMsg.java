package anu.cookcompass.popmsg;

public class PopMsg {
    public String uid = "uid";
    public String username = "username";
    public int rid = 1;
    public String title = "title";
    public String location = "location";
    public PopMsgType type = PopMsgType.LIKE;
    public int timestamp = 0;

    // don't delete
    public PopMsg() {
    }

    public PopMsg(String uid, String username, int rid, String title, String location, PopMsgType type, int timestamp) {
        this.uid = uid;
        this.username = username;
        this.rid = rid;
        this.title = title;
        this.location = location;
        this.type = type;
        this.timestamp = timestamp;
    }
}