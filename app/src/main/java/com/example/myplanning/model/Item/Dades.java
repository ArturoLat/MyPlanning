package com.example.myplanning.model.Item;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Dades {

    private String activitat;
    private Boolean done;
    private LocalDateTime date;
    private Integer id;

    public Dades(String activitat, Boolean done, String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dataFinal = LocalDateTime.parse(date, formatter);

        this.activitat = activitat;
        this.done = done;
        this.date = dataFinal;
        this.id = null;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
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
