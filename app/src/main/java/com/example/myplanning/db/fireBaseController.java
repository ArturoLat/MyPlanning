package com.example.myplanning.db;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myplanning.activitats.Diari.CalendariDiari;
import com.example.myplanning.model.Llista.HomeWork;
import com.example.myplanning.model.Llista.Schedule;
import com.example.myplanning.model.Llista.ToDo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

public class fireBaseController{

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static Map<String, Object> result = new HashMap<>();
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

    public void getCollectUserHomeWork(String user, LocalDateTime time){

        result.clear();
        String dataInfo = String.valueOf(time.getYear()+"-"+time.getMonthValue()+"-"+time.getDayOfMonth());

        db.collection(user).document("homework").collection(dataInfo).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                result.put(document.getId(),document.getData());
                            }
                        }
                        //MOMENT DE TRANSFERIR DADES
                    }
                });

    }

    public void getCollectUserSchedule(String user, LocalDateTime time){

        result.clear();
        String dataInfo = String.valueOf(time.getYear()+"-"+time.getMonthValue()+"-"+time.getDayOfMonth());

        db.collection(user).document("schedule").collection(dataInfo).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                result.put(document.getId(), document.getData());
                            }
                        }
                        //MOMENT DE TRANSFERIR DADES
                    }
                });

    }


    public void getCollectUserTodo(String user, LocalDateTime time){

        result.clear();
        String dataInfo = String.valueOf(time.getYear()+"-"+time.getMonthValue()+"-"+time.getDayOfMonth());

        db.collection(user).document("todo").collection(dataInfo).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                result.put(document.getId(),document.getData());
                            }
                        }
                        //MOMENT DE TRANSFERIR DADES
                    }
                });

    }

    public void setCollectUserTodo (LocalDateTime time, String user, String act){

        Map<String,Object> data = new HashMap<>();
        ToDo object = new ToDo(act,false, time);

        data.put(act,object);

        db.collection(user).document("todo")
                .collection(String.valueOf(time.getYear()+"-"+time.getMonthValue()+"-"+time.getDayOfMonth())).document(act).set(data);

    }
    public void setCollectUserHomework (LocalDateTime time, String user, String act){

        Map<String,Object> data = new HashMap<>();
        HomeWork object = new HomeWork(act,false, time);

        data.put(act,object);
        db.collection(user).document("homework")
                .collection(String.valueOf(time.getYear()+"-"+time.getMonthValue()+"-"+time.getDayOfMonth())).document(act).set(data);

    }

    public void setCollectUserScheduleDay(LocalDateTime time, String user, String act){

        Map<String,Object> data = new HashMap<>();
        Schedule object = new Schedule(act,false, time);

        data.put(act,object);

        db.collection(user).document("schedule")
                .collection(String.valueOf(time.getYear()+"-"+time.getMonthValue()+"-"+time.getDayOfMonth())).document(act).set(data);

    }

}
