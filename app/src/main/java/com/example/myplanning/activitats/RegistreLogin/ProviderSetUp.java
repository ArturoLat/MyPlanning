package com.example.myplanning.activitats.RegistreLogin;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myplanning.R;
import com.example.myplanning.activitats.Configuracio.ProviderType;

public class ProviderSetUp {
    String mail;
    String user;
    ProviderType providerType;
    public static ProviderSetUp instance;

    public ProviderSetUp(String mail, ProviderType providerType, String user){
        this.mail = mail;
        this.providerType = providerType;
        this.user = user;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}
