package com.example.myplanning.model.Llista;

public enum ErrorRegistre {
    FORMAT_EMAIL("Format de l'email incorrecte"),
    FORMAT_CONTRASENYA("Format de la contrasenya incorrecte"),
    REGISTRE_CORRECTE("Registre correcte");

    private final String text;

    ErrorRegistre(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
