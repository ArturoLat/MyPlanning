package com.example.myplanning.activitats.RegistreLogin;

import com.example.myplanning.activitats.Configuracio.ProviderType;

public class ProviderSetUp {
    String mail;
    ProviderType providerType;
    public static ProviderSetUp instance;

    public ProviderSetUp(String mail, ProviderType providerType){
        this.mail = mail;
        this.providerType = providerType;
        instance = this;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setProviderType(ProviderType providerType) {
        this.providerType = providerType;
    }

    public static ProviderSetUp getInstance() {
        return instance;
    }

    public String getString(){
        return providerType.toString();
    }

    public String getMail() {
        return mail;
    }
}
