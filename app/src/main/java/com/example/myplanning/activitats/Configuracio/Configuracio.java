package com.example.myplanning.activitats.Configuracio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import com.example.myplanning.R;
import com.example.myplanning.activitats.Seleccio.Seleccio;

public class Configuracio extends AppCompatActivity {

    private Button btnConfig;
    private SwitchCompat switchCompatNightMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_layout);
        initWidgets();
    }

    private void initWidgets(){
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
                    getApplicationContext().getTheme().applyStyle(AppCompatDelegate.MODE_NIGHT_YES, true);
                }else{
                    getApplicationContext().getTheme().applyStyle(AppCompatDelegate.MODE_NIGHT_NO, false);
                }
            }


        });


    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
