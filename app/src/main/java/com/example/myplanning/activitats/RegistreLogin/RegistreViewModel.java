package com.example.myplanning.activitats.RegistreLogin;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myplanning.model.Usuari.ErrorRegistre;

public class RegistreViewModel extends AndroidViewModel {

    private MutableLiveData<String> respuesta;

    public RegistreViewModel(Application application){
        super(application);
        respuesta = new MutableLiveData<>();
    }

    public String getRespuestaString(){
        return respuesta.getValue().toString();
    }


    public LiveData<String> getRespuesta(){
        return respuesta;
    }

    public void registreCorrecte(){
        respuesta.setValue(ErrorRegistre.REGISTRE_CORRECTE.toString());
    }

    public void emailIncorrecte() {
        respuesta.setValue(ErrorRegistre.FORMAT_EMAIL.toString());
    }

    public void registreAnterior(){
        respuesta.setValue(ErrorRegistre.REGISTRE_ANTERIOR.toString());

    }

    public void contrasenyaIncorrecte() { respuesta.setValue(ErrorRegistre.FORMAT_CONTRASENYA.toString());}

    public void usuariJaCreat() { respuesta.setValue(ErrorRegistre.REGISTRE_ANTERIOR.toString());}
}

