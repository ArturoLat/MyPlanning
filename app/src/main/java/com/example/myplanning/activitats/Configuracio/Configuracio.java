package com.example.myplanning.activitats.Configuracio;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import com.example.myplanning.R;
import com.example.myplanning.activitats.RegistreLogin.AuthActivity;
import com.example.myplanning.activitats.RegistreLogin.ProviderSetUp;
import com.example.myplanning.activitats.Seleccio.Seleccio;
import com.google.firebase.auth.FirebaseAuth;

public class Configuracio extends AppCompatActivity {

    private Button btnConfig;
    private SwitchCompat switchCompatNightMode;
    private TextView emailtxt;
    private ProviderSetUp providerSetUp;
    private TextView usuaritxt;
    private TextView proveidortxt;
    private Button tancarSessioBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_layout);
        initWidgets();
        providerSetUp = ProviderSetUp.getInstance();

        String email = providerSetUp.getMail();
        String proveidor = providerSetUp.getString();
        String usuari = providerSetUp.getUser();
        System.out.println(email);
        System.out.println(proveidor);
        setup(email, proveidor, usuari);
    }

    private void initWidgets(){
        emailtxt = findViewById(R.id.txtCorreuAuth);
        proveidortxt = findViewById(R.id.txtProveidorAuth);
        tancarSessioBtn = findViewById(R.id.btnTancarSessio);
        usuaritxt = findViewById(R.id.txtUsuariAuth);
        btnConfig = findViewById(R.id.btnConfig);
        switchCompatNightMode = findViewById(R.id.switchNightMode);
    }

    public void configuracioAccio(View view){
        startActivity(new Intent(this, Seleccio.class));
    }

    public void nightModeAccio(View view){
        if(switchCompatNightMode.isChecked()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void setup(String email, String proveidor, String usuari){
        emailtxt.setText(email);
        proveidortxt.setText(proveidor);
        usuaritxt.setText(usuari);
    }

    public void tancarSessioAccio(View view){
        SharedPreferences prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, AuthActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
