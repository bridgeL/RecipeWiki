package anu.cookcompass.pattern;


public interface Observer<T> {
    void onDataChange(T data);
}
