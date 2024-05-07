package anu.cookcompass.popmsg;

public enum PopMsgType {
    UNLIKE("unlike"),
    LIKE("like");

    public final String value;

    // Constructor for enum which sets the string value
    PopMsgType(String value) {
        this.value = value;
    }
}
