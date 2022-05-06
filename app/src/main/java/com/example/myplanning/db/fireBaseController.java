package com.example.myplanning.db;


import androidx.annotation.NonNull;

import com.example.myplanning.activitats.Diari.CalendariDiari;
import com.example.myplanning.activitats.observer.llistArrayObserver;
import com.example.myplanning.model.Item.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class fireBaseController  implements llistArrayObserver{

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
                        notificarHomeWork(result);
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
                                result.put(document.getId(),document.getData());
                            }
                        }
                        notificarSchedule(result);

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
                        notificarToDo(result);

                    }
                });

    }

    public void setCollectUserTodo (LocalDateTime time, String user, String act){
        Dades dato = new Dades(act,false, time);

        db.collection(user).document("todo")
                .collection(String.valueOf(time.getYear()+"-"+time.getMonthValue()+"-"+time.getDayOfMonth())).document(act).set(dato.toMap());

    }
    public void setCollectUserHomework (LocalDateTime time, String user, String act){
        Dades dato = new Dades(act,false, time);

        db.collection(user).document("homework")
                .collection(String.valueOf(time.getYear()+"-"+time.getMonthValue()+"-"+time.getDayOfMonth())).document(act).set(dato.toMap());

    }

    public void setCollectUserSchedule(LocalDateTime time, String user, String act){

        Dades dato = new Dades(act,false, time);

        db.collection(user).document("schedule")
                .collection(String.valueOf(time.getYear()+"-"+time.getMonthValue()+"-"+time.getDayOfMonth())).document(act).set(dato.toMap());

    }

    @Override
    public void notificarSchedule(Map<String, Object> dada){
        ArrayList<Object> camps = new ArrayList<>();
        String act ="";
        Boolean done = false;
        String dateTime = "";

        if(!dada.isEmpty()){
            for (Object o: dada.values()){
                camps.add(o);

            }
            for (int i = 0; i < camps.size(); i++) {
                HashMap<String,Object> o = (HashMap<String, Object>) camps.get(i);
                int cont = 0;
                for(Object atri: o.values()){
                    if(cont == 0){
                        dateTime = (String) atri;

                    }else if(cont == 1){
                        done = (Boolean) atri;

                    }else{
                        act = (String) atri;

                    }
                    cont++;
                }
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                LocalDateTime dataFinal = LocalDateTime.parse(dateTime, formatter);
                CalendariDiari.listDatosShedule.add(new Dades(act,done,dataFinal));
                cont = 0;
            }
        }
        CalendariDiari.updateSchedule();
    }

    @Override
    public void notificarToDo(Map<String, Object> dada) {
        ArrayList<Object> camps = new ArrayList<>();
        String act ="";
        Boolean done = false;
        String dateTime = "";

        if(!dada.isEmpty()){
            for (Object o: dada.values()){
                camps.add(o);

            }
            for (int i = 0; i < camps.size(); i++) {
                HashMap<String,Object> o = (HashMap<String, Object>) camps.get(i);
                int cont = 0;
                for(Object atri: o.values()){
                    if(cont == 0){
                        dateTime = (String) atri;

                    }else if(cont == 1){
                        done = (Boolean) atri;

                    }else{
                        act = (String) atri;

                    }
                    cont++;
                }
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                LocalDateTime dataFinal = LocalDateTime.parse(dateTime, formatter);
                CalendariDiari.listDatostoDo.add(new Dades(act,done,dataFinal));
                cont = 0;
            }
        }
        CalendariDiari.updateToDo();
    }

    @Override
    public void notificarHomeWork(Map<String, Object> dada) {
        ArrayList<Object> camps = new ArrayList<>();
        String act ="";
        Boolean done = false;
        String dateTime = "";

        if(!dada.isEmpty()){
            for (Object o: dada.values()){
                camps.add(o);

            }
            for (int i = 0; i < camps.size(); i++) {
                HashMap<String,Object> o = (HashMap<String, Object>) camps.get(i);
                int cont = 0;
                for(Object atri: o.values()){
                    if(cont == 0){
                        dateTime = (String) atri;

                    }else if(cont == 1){
                        done = (Boolean) atri;

                    }else{
                        act = (String) atri;

                    }
                    cont++;
                }
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                LocalDateTime dataFinal = LocalDateTime.parse(dateTime, formatter);
                CalendariDiari.listDatosHomeWork.add(new Dades(act,done,dataFinal));
                cont = 0;
            }
        }
        CalendariDiari.updateHomeWork();
    }
}
