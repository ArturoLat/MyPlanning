package com.example.myplanning.model.Llista;

public enum ErrorLogIn {
        USUARI_INEXISTENT("Usuari inexistent"),
        CONTRASSENYA_INCORRECTA("Contrassenya incorrecta"),
        LOGIN_CORRECTE("Login correcte");


        private final String text;

        ErrorLogIn(final String text) {
        this.text = text;
    }

        @Override
        public String toString() {
        return text;
    }
}

