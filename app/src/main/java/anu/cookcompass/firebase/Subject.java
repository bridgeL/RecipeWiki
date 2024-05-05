package anu.cookcompass.firebase;

import java.util.List;

public interface Subject<T> {
    List<Listener<T>> getListeners();
    default void notifyAllListeners(T data){
        for (Listener<T> listener : getListeners()) {
            listener.onDataChanged(data);
        }
    }
    default void addListener(Listener<T> listener){
        getListeners().add(listener);
    }

    default void removeListener(Listener<T> listener){
        getListeners().remove(listener);
    }
}
