package com.example.myplanning.model.Llista;

public enum ErrorRegistre {
    CONTRASENYA_INCORRECTE("Contrasenya Incorrecte"),
    FORMAT_EMAIL("Format de l'email incorrecte"),
    FORMAT_EMAIL_C("Format de l'email correcte"),
    FORMAT_CONTRASENYA("Format de la contrasenya incorrecte"),
    REGISTRE_CORRECTE("Login correcte");

    private final String text;

    ErrorRegistre(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
