package com.example.myplanning.model.Usuari;

public enum ErrorLogIn {
        USUARI_INEXISTENT("Usuari inexistent"),
        CONTRASSENYA_INCORRECTA("Contrassenya incorrecta"),
        CAMP_INCORRECTE("Falta un camp o varis per omplir"),
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

