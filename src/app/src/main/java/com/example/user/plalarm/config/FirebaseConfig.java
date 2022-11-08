package com.example.user.plalarm.config;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import com.example.user.plalarm.model.Event;
import com.example.user.plalarm.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class FirebaseConfig{

    private final FirebaseFirestore db;

    public FirebaseConfig(){
        this.db = FirebaseFirestore.getInstance();
    }

    public FirebaseFirestore getDb(){
        return db;
    }

    // userData 저장
    public static void putUserData(FirebaseFirestore db, @NonNull String path, @NonNull User user){
        Map<String, Object> userEntity = new HashMap<>();
        userEntity.put("Email", user.getEmail());
        userEntity.put("Nickname", user.getNickname());
        userEntity.put("Password", user.getPassword());
        db.collection(path).document(user.getEmail()).set(userEntity);
    }

    // eventData 를 일정의 이름과 시작시간을 합친 문서로 저장(Ex event2022-11-08)
    public static void putEventData(FirebaseFirestore db, @NonNull String path, @NonNull Event event){
        Map<String, Object> eventEntity = new HashMap<>();
        eventEntity.put("Title", event.getTitle());
        eventEntity.put("Content", event.getContent());
        eventEntity.put("StartTime", event.getStartTime());
        eventEntity.put("EndTime", event.getEndTime());
        eventEntity.put("IntentApp", event.getIntentApp());
        db.collection(path).document(event.getTitle()+event.getStartTime()).set(eventEntity);
    }

    // collection 을 통해 서버에 저장된 정보를 가져옴
    public static void getData(FirebaseFirestore db, @NonNull String collectionPath){
        db.collection(collectionPath)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    // 컬렉션 내부의 문서 이름을 인자로 정보 삭제
    public static void deleteData(FirebaseFirestore db, @NonNull String collectionPath, @NonNull String documentPath){
        db.collection(collectionPath).document(documentPath)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }
}
