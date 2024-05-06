package anu.cookcompass.model;

public class PopMsg {
    private String username;
    private int rid;
    private String title;
    private String location;

    // constructor
    public PopMsg(String username, int rid, String title, String location) {
        this.username = username;
        this.rid = rid;
        this.title = title;
        this.location = location;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}