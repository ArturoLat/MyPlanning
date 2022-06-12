package com.example.myplanning.activitats.RegistreLogin;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myplanning.R;
import com.example.myplanning.activitats.Configuracio.ProviderType;

public class ProviderSetUp {
    private String mail;
    private String user;
    private ProviderType providerType;
    public static ProviderSetUp instance;
    private String pass;

    public ProviderSetUp(String mail, ProviderType providerType, String user, String pass){
        this.mail = mail;
        this.providerType = providerType;
        this.user = user;
        instance = this;
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
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

