package com.example.myplanning.db;


import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myplanning.model.Llista.HomeWork;
import com.example.myplanning.model.Llista.Schedule;
import com.example.myplanning.model.Llista.ToDo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

public class fireBaseController {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Map<String, Object> result = new HashMap<>();
    private static fireBaseController instance;
    private int resultat = -1;


    public static fireBaseController getInstance(){
        if(instance == null){
            instance = new fireBaseController();

        }
        return instance;

    }

    public Integer userExist(String user, String passUser){

        DocumentReference docRef = db.collection("users").document(user);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String pass = (String) document.getData().get("password");
                        if (pass.equals(passUser)) {
                            //Login Correcte
                            resultat = 0;

                        } else {
                            //Contrasenya diferent
                            resultat = 1;
                        }
                    } else {
                        //error no document asociat
                        resultat = 2;
                    }
                }
            }
        });

        return resultat;
    }

    public String getCampCollectUser(String camp, String user){
        String var = "";

        DocumentReference docRef = db.collection("users").document(user);
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

    public Map<String, HomeWork> getCollectUserHomeWorkDay(long time, String user){

        Map<String, HomeWork> resultat = new HashMap<>();

        OffsetDateTime dateUTC = OffsetDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneOffset.UTC);

        String date = String.valueOf(dateUTC.getDayOfMonth()+dateUTC.getMonthValue()+dateUTC.getYear());

        db.collection(user).document("task").collection(date)
                .whereNotEqualTo("hora",0)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //resultat.put()

                            }
                        } else {
                            //error de descarga
                        }
                    }
                });

        return resultat;

    }

    public Map<String, ToDo> getCollectUserTodoDay(long time, String user){

        Map<String, ToDo> resultat = new HashMap<>();

        OffsetDateTime dateUTC = OffsetDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneOffset.UTC);

        String date = String.valueOf(dateUTC.getDayOfMonth()+dateUTC.getMonthValue()+dateUTC.getYear());

        db.collection(user).document("todo").collection(date)
                .whereNotEqualTo("hora",0)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //resultat.put();

                            }
                        } else {
                            //error de descarga
                        }
                    }
                });

        return resultat;

    }

    public Map<String, Schedule> getCollectUserScheduleDay(long time, String user){

        Map<String, Schedule> resultat = new HashMap<>();

        OffsetDateTime dateUTC = OffsetDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneOffset.UTC);

        String date = String.valueOf(dateUTC.getDayOfMonth()+dateUTC.getMonthValue()+dateUTC.getYear());

        db.collection(user).document("schedule").collection(date)
                .whereNotEqualTo("hora",0)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                //resultat.put(document.getId(),document.getData());

                            }
                        } else {
                            //error de descarga
                        }
                    }
                });

        return resultat;

    }

    public Map<String, Object> getCollectUserTodo(String camp, String user){

        DocumentReference docRef = db.collection(user).document("todo");
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

    public void setCollectUserTodo (LocalDateTime time, String user, String act){

        Map<String,Object> data = new HashMap<>();
        ToDo object = new ToDo(act,false, time);

        data.put(object.getActivitat(),object);

        db.collection(user).document("todo")
                .collection(time.toString()).document(act).set(data);

    }
    public void setCollectUserHomework (LocalDateTime time, String user, String act){

        Map<String,Object> data = new HashMap<>();
        HomeWork object = new HomeWork(act,false, time);

        data.put(object.getActivitat(),object);
        db.collection(user).document("homework")
                .collection(time.toString()).document(act).set(data);

    }

    public void setCollectUserScheduleDay(LocalDateTime time, String user, String act){

        Map<String,Object> data = new HashMap<>();
        Schedule object = new Schedule(act,false, time);

        data.put(object.getActivitat(),object);

        db.collection(user).document("schedule")
                .collection(time.toString()).document(act).set(data);

    }

}
