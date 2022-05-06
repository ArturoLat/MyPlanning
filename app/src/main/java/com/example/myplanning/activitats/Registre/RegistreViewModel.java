package com.example.myplanning.activitats.Registre;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myplanning.model.Usuari.ErrorRegistre;

public class RegistreViewModel extends ViewModel {

    private MutableLiveData<String> respuesta;

    public RegistreViewModel(){
        respuesta = new MutableLiveData<>();
    }


    public LiveData<String> getRespuesta(){
        return respuesta;
    }

    public void registreCorrecte(){
        respuesta.setValue(ErrorRegistre.REGISTRE_CORRECTE.toString());
    }

    public void emailIncorrecte() { respuesta.setValue(ErrorRegistre.FORMAT_EMAIL.toString());}

    public void contrasenyaIncorrecte() { respuesta.setValue(ErrorRegistre.FORMAT_CONTRASENYA.toString());}

    public void usuariJaCreat() { respuesta.setValue(ErrorRegistre.REGISTRE_ANTERIOR.toString());}
}

