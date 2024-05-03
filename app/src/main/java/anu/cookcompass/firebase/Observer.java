package anu.cookcompass.firebase;

import com.google.firebase.database.DataSnapshot;

public interface Observer {
    void notify(DataSnapshot dataSnapshot);
}
