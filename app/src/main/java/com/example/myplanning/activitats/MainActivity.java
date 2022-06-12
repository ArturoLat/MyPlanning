package com.example.myplanning.activitats;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;
import com.example.myplanning.activitats.CalendariUtiles;

import com.example.myplanning.activitats.RegistreLogin.AuthActivity;
import com.example.myplanning.databinding.MainActivityLayoutBinding;

import java.time.LocalDate;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private MainActivityLayoutBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CalendariUtiles.selectedDate = LocalDate.now();
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, AuthActivity.class));

    }


}