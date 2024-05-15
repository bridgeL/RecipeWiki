package anu.cookcompass.popmsg;

/**
 * @author u7760022, Xinyang Li
 * The class is PopMsgType
 */
public enum PopMsgType {
    UNLIKE("unlike"),
    LIKE("like");

    public final String value;

    // Constructor for enum which sets the string value
    PopMsgType(String value) {
        this.value = value;
    }
}
