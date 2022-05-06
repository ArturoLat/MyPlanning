package com.example.myplanning.model.Item;

public class DayRating {
    String descripcio;
    Integer nota;
    Integer id;

    public DayRating(String descripcio, Integer nota) {
        this.descripcio = descripcio;
        this.nota = nota;
        this.id = null;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String toString() {
        return descripcio + " :" + nota;
    }
}
