package com.example.myplanning.model.Llista;

public class Usuario {
    String nom;
    Boolean usuariCreat;

    public Usuario(String nom){
        this.nom = nom;
        usuariCreat = false;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Boolean getUsuariCreat() {
        return usuariCreat;
    }

    public void setUsuariCreat(Boolean usuariCreat) {
        this.usuariCreat = usuariCreat;
    }
}
