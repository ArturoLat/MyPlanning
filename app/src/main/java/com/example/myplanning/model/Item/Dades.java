package com.example.myplanning.model.Item;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Dades {

    private String activitat;
    private Boolean done;
    private LocalDateTime date;
    private Integer id;

    public Dades(String activitat, Boolean done, LocalDateTime date) {
        this.activitat = activitat;
        this.done = done;
        this.date = date;
        this.id = null;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return activitat + "  " + date;
    }
}
