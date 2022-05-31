package com.example.myplanning.model.Usuari;

import com.example.myplanning.db.fireBaseController;

public class Usuario {

    private String nom;
    public static Usuario instance;

    public Usuario(String nom){
        this.nom = nom;
        instance = this;
    }
    public static Usuario getInstance(){
        return instance;

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


}
