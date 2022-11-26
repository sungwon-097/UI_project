package com.example.user.plalarm.config;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.user.plalarm.model.Event;
import com.example.user.plalarm.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class FirebaseConfig{

    // userData 저장
    public static void putUserData(@NonNull String path, @NonNull User user){
        Map<String, Object> userEntity = new HashMap<>();
        userEntity.put("Email", user.getEmail());
        userEntity.put("Nickname", user.getNickname());
        userEntity.put("Password", user.getPassword());
        FirebaseFirestore.getInstance().collection(path).document(user.getEmail()).set(userEntity);
    }

    // eventData 를 일정의 이름과 시작시간을 합친 문서로 저장(Ex event2022-11-08)
    public static void putEventData(@NonNull String path, Event event){
        Map<String, Object> eventEntity = new HashMap<>();
        eventEntity.put("title", event.getTitle());
        eventEntity.put("content", event.getContent());
        eventEntity.put("startTime", event.getStartTime());
        eventEntity.put("endTime", event.getEndTime());
        eventEntity.put("intentApp", event.getIntentApp());
        FirebaseFirestore.getInstance().collection(path).document(event.getTitle()+event.getStartTime()).set(eventEntity);
    }

    // collection 을 통해 서버에 저장된 정보를 가져옴
    public static Map<String, Object> getData(@NonNull String collectionPath){

        Map<String, Object> data = new HashMap<>();
        FirebaseFirestore.getInstance().collection(collectionPath)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                data.put("title", document.getData().get("title"));
//                                event[0] = new Event((String) data.get("title"), (String) data.get("content"), (LocalDateTime)data.get("startTime"), (LocalDateTime) data.get("endTime"),(String) data.get("intentApp"));
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
        return data;
    }

    // 컬렉션 내부의 문서 이름을 인자로 정보 삭제
    public static void deleteData(@NonNull String collectionPath, @NonNull String documentPath){
        FirebaseFirestore.getInstance().collection(collectionPath).document(documentPath)
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
