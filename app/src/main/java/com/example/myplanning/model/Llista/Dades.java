package com.example.myplanning.model.Llista;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Dades {

    private String activitat;
    private Boolean done;
    private LocalDateTime date;

    public Dades(String activitat, Boolean done, LocalDateTime date) {
        this.activitat = activitat;
        this.done = done;
        this.date = date;

    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("activitat", this.activitat);
        result.put("done", this.done);
        result.put("Localdate", date.toString());

        return result;
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
        return activitat + "  " + date;


    }
}
