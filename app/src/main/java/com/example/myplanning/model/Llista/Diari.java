package com.example.myplanning.model.Llista;

import java.util.ArrayList;
import java.util.List;

public abstract class Diari {

    private List<String> llista;

    public Diari() {
        this.llista = new ArrayList<>();
    }

    public List<String> getLlista() {
        return this.llista;
    }

    public void setLlista(List<String> llista) {
        this.llista = llista;
    }

    public void add(String elemento) {
        this.llista.add(elemento);
    }

}