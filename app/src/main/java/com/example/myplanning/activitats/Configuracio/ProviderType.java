package com.example.myplanning.activitats.Configuracio;

public enum ProviderType {
    EMAIL("EMAIL"),
    GOOGLE("GOOGLE"),
    OFFLINE("OFFLINE");


    private final String text;

    ProviderType(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}