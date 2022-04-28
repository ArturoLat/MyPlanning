package com.example.myplanning.activitats;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myplanning.model.Llista.ErrorLogIn;
import com.example.myplanning.model.Llista.ErrorRegistre;

public class LogInViewModel extends ViewModel {

    private MutableLiveData<String> respuesta;

    public LogInViewModel(){
        respuesta = new MutableLiveData<>();
    }


    public LiveData<String> getRespuesta(){
        return respuesta;
    }

    public void logInCorrecte(){
        respuesta.setValue(ErrorLogIn.LOGIN_CORRECTE.toString());
    }

    public void contrasenyaDiferent() { respuesta.setValue( ErrorLogIn.CONTRASSENYA_INCORRECTA.toString());}

    public void campBuit(){respuesta.setValue(ErrorLogIn.CAMP_INCORRECTE.toString());}

    public void usuariInexistent() { respuesta.setValue(ErrorLogIn.USUARI_INEXISTENT.toString());}
}
