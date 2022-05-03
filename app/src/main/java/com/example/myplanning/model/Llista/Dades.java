package com.example.myplanning.model.Llista;

import java.time.LocalDateTime;

public class Dades {

    private String activitat;
    private Boolean done;
    private LocalDateTime date;

    public Dades(String activitat, Boolean done, LocalDateTime date) {
        this.activitat = activitat;
        this.done = done;
        this.date = date;

    }

    public String getActivitat() {
        return activitat;
    }

    public void setActivitat(String activitat) {
        this.activitat = activitat;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return activitat + " ";


    }
}
