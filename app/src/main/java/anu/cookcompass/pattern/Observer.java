package anu.cookcompass.pattern;


/**
 * @author u7760022, Xinyang Li
 * The class is a Observer (design pattern)
 */
public interface Observer<T> {
    void onDataChange(T data);
}
