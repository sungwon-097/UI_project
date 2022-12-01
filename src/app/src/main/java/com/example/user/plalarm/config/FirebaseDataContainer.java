package com.example.user.plalarm.config;

import static android.content.ContentValues.TAG;

import android.util.Log;
import androidx.annotation.NonNull;
import com.example.user.plalarm.model.Event;
import com.example.user.plalarm.model.EventList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class FirebaseDataContainer {

    public static EventList container = new EventList();

    public FirebaseDataContainer(EventList container) {
        FirebaseDataContainer.container = container;
    }

    public EventList getContainer(){
        return container;
    }

    public FirebaseDataContainer(@NonNull String collectionPath){

        FirebaseFirestore.getInstance().collection(collectionPath)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (container != null)
                                FirebaseDataContainer.container.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                FirebaseDataContainer.container.add(document.toObject(Event.class));
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}
