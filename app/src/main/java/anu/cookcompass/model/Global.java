package anu.cookcompass.model;


/**
 * Design Pattern: Singleton
 * <p>
 * Global Instances
 * </p>
 */
public class Global {
    public User currentUser = null;  // the user who login this app
    private static Global instance = null;

    private Global() {
    }

    /**
     * @return Global instance
     * <p>
     * this function can only be called after init()
     * </p>
     */
    public static Global getInstance() {
        if (instance == null) {
            instance = new Global();
        }
        return instance;
    }
}
