package com.example.myplanning.model.Usuari;

import com.example.myplanning.db.fireBaseController;

public class Usuario {

    private String correu;
    private String username;
    public static Usuario instance;

    public Usuario(String nom, String user){
        this.correu = nom;
        this.username = user;
        instance = this;
    }
    public static Usuario getInstance(){
        return instance;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNom() {
        return correu;
    }

    public void setNom(String nom) {
        this.correu = nom;
    }


}
