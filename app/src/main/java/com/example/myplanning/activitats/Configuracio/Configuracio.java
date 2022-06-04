package com.example.myplanning.activitats.Configuracio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

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
        System.out.println(email);
        System.out.println(proveidor);
        setup(email, proveidor);
    }

    private void initWidgets(){
        emailtxt = findViewById(R.id.txtCorreuAuth);
        proveidortxt = findViewById(R.id.txtProveidorAuth);
        tancarSessioBtn = findViewById(R.id.btnTancarSessio);
        btnConfig = findViewById(R.id.btnConfig);
        switchCompatNightMode = findViewById(R.id.switchNightMode);
    }

    public void configuracioAccio(View view){
        startActivity(new Intent(this, Seleccio.class));
    }

    public void nightModeAccio(View view){
        switchCompatNightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }

                if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
                    getTheme().applyStyle(AppCompatDelegate.MODE_NIGHT_YES, true);
                }else{
                    getTheme().applyStyle(AppCompatDelegate.MODE_NIGHT_NO, false);
                }
            }
        });
    }

    private void setup(String email, String proveidor){
        emailtxt.setText(email);
        proveidortxt.setText(proveidor);
    }

    public void tancarSessioAccio(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, AuthActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
