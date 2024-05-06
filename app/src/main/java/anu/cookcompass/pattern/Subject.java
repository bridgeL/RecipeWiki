package anu.cookcompass.pattern;

import java.util.List;

public interface Subject<T> {
    List<Observer<T>> getObservers();

    default void notifyAllObservers(T data) {
        for (Observer<T> observer : getObservers()) {
            observer.onDataChange(data);
        }
    }

    default void addObserver(Observer<T> observer) {
        getObservers().add(observer);
    }

    default void removeObserver(Observer<T> observer) {
        getObservers().remove(observer);
    }
}
