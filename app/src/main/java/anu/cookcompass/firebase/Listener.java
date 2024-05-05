package anu.cookcompass.firebase;


public interface Listener<T> {
    void onDataChanged(T data);
}
