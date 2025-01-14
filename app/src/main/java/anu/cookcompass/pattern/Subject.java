package anu.cookcompass.pattern;

import android.util.Log;

import java.util.List;

/**
 * @author u7760022, Xinyang Li
 * The class is a Subject (design pattern)
 */
public interface Subject<T> {
    List<Observer<T>> getObservers();

    default void notifyAllObservers(T data) {
        for (Observer<T> observer : getObservers()) {
            try {
                observer.onDataChange(data);
            } catch (Exception e) {
                Log.e("Subject", e.getMessage());
            }
        }
    }

    default void addObserver(Observer<T> observer) {
        getObservers().add(observer);
    }

    default void removeObserver(Observer<T> observer) {
        getObservers().remove(observer);
    }
}
