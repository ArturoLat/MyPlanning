package com.example.myplanning.model.Usuari;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComprobarDades {

    public String isSecure(String password, String correu){
        if(!isPasswordSegur(password)){
            return ErrorRegistre.FORMAT_CONTRASENYA.toString();
        }else if(!isMail(correu)){
            return ErrorRegistre.FORMAT_EMAIL.toString();
        }else{
            return ErrorRegistre.REGISTRE_CORRECTE.toString();
        }
    }

    private boolean isPasswordSegur(String password) {
        Pattern pattern = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }
    private boolean isMail(String correu) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(correu);
        return matcher.find();
    }
}
