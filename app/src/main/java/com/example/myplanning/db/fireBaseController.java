package com.example.myplanning.db;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myplanning.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Observer;

public class fireBaseController {

    private String user;
    private FirebaseFirestore db;
    private Map<String, Object> result = new HashMap<>();

    public fireBaseController(String user, FirebaseFirestore db){

        this.user = user;
        this.db = db;

    }

    public String getCampCollectUser(String camp){
        String var = "";

        DocumentReference docRef = db.collection("users").document(this.user);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String var = (String) document.getData().get(camp);

                    } else {

                    }
                } else {
                    //fall de descarrega
                }
            }
        });

        return var;
    }

    public Map<String, Object> getCollectUserTodo(String camp){

        DocumentReference docRef = db.collection(this.user).document("todo");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        result =  document.getData();

                    } else {
                        //error no document asociat
                    }
                } else {
                    //fall de descarrega
                }
            }
        });

        return result;
    }

    public Map<String, Object> getCollectUserHomeWork (String camp){

        DocumentReference docRef = db.collection(this.user).document("homework");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        result =  document.getData();

                    } else {
                        //error no document asociat
                    }
                } else {
                    //fall de descarrega
                }
            }
        });

        return result;
    }

    public void setCollectUserTodo (String time, String act, boolean check){

        Map<String,Object> data = new HashMap<>();
        data.put("done",check);
        db.collection(user).document("todo").collection(time).document(act).set(data);

    }
    public void setCollectUserHomework (String time, String act, boolean check){

        Map<String,Object> data = new HashMap<>();
        data.put("done",check);
        db.collection(user).document("homework").collection(time).document(act).set(data);

    }





}
