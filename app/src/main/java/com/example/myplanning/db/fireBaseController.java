package com.example.myplanning.db;


import androidx.annotation.NonNull;

import com.example.myplanning.R;
import com.example.myplanning.activitats.Diari.CalendariDiari;
import com.example.myplanning.activitats.observer.llistArrayObserver;
import com.example.myplanning.model.Item.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class fireBaseController{

    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseStorage storage = FirebaseStorage.getInstance();
    private static Map<String, Object> result = new HashMap<>();
    private static fireBaseController instance;
    public static llistArrayObserver listener;
    private int resultat = -1;

    public fireBaseController(llistArrayObserver listener){
        this.listener = listener;
        this.instance = this;

    }
    public fireBaseController(){
        this.instance = this;

    }

    public static void setListener(llistArrayObserver listener) {
        fireBaseController.listener = listener;
    }

    public static fireBaseController getInstance() {
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
                            ArrayList<Dades> llista_homework = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                llista_homework.add(new Dades(document.getString("activitat"),
                                        Boolean.valueOf(document.getBoolean("done")),
                                        document.getString("Localdate"),
                                        document.getLong("color").intValue()));
                            }
                            listener.notificarHomeWork(llista_homework);
                        }

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
                            ArrayList<Dades> llista_schedule = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                llista_schedule.add(new Dades(document.getString("activitat"),
                                        Boolean.valueOf(document.getBoolean("done")),
                                        document.getString("Localdate"),
                                        document.getLong("color").intValue()));
                            }
                            listener.notificarSchedule(llista_schedule);
                        }
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
                            ArrayList<Dades> llista_todo = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                llista_todo.add(new Dades(document.getString("activitat"),
                                        Boolean.valueOf(document.getBoolean("done")),
                                        document.getString("Localdate"),
                                        document.getLong("color").intValue()));
                            }
                            listener.notificarToDo(llista_todo);
                        }
                    }
                });

    }

    public void setCollectUserTodo (LocalDateTime time, String user, String act){

        String date = time.toString();

        Map<String, Object> object = new HashMap<>();
        object.put("Localdate", date);
        object.put("activitat", act);
        object.put("done", false);

        db.collection(user).document("todo")
                .collection(String.valueOf(time.getYear()+"-"+time.getMonthValue()+"-"+time.getDayOfMonth())).document(act).set(object);

    }
    public void setCollectUserHomework (LocalDateTime time, String user, String act){
        Map<String, Object> object = new HashMap<>();

        String date = time.toString();

        object.put("Localdate", date);
        object.put("activitat", act);
        object.put("done", false);

        db.collection(user).document("homework")
                .collection(String.valueOf(time.getYear()+"-"+time.getMonthValue()+"-"+time.getDayOfMonth())).document(act).set(object);

    }

    public void setCollectUserSchedule(LocalDateTime time, String user, String act){
        Map<String, Object> object = new HashMap<>();

        String date = time.toString();

        object.put("Localdate", date);
        object.put("activitat", act);
        object.put("done", false);

        db.collection(user).document("schedule")
                .collection(String.valueOf(time.getYear()+"-"+time.getMonthValue()+"-"+time.getDayOfMonth())).document(act).set(object);

    }

}
