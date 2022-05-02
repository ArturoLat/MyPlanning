package com.example.myplanning.model.Llista;

public class Dades {

    private String activitat;
    private Boolean done;
    private int hora;
    private int minutos;

    public Dades(String activitat, Boolean done, int hora, int minutos) {
        this.activitat = activitat;
        this.done = done;
        this.hora = hora;
        this.minutos = minutos;

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

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return activitat + " " +
                hora + ":" +
                minutos;

    }
}
