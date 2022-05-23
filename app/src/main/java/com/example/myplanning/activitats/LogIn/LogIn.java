package com.example.myplanning.activitats.LogIn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myplanning.R;
import com.example.myplanning.activitats.CalendariUtiles;
import com.example.myplanning.activitats.Diari.CalendariDiari;
import com.example.myplanning.activitats.Mensual.CalendariMensual;
import com.example.myplanning.activitats.Registre.Registre;
import com.example.myplanning.db.fireBaseController;
import com.example.myplanning.model.Usuari.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LogIn extends AppCompatActivity {

    private Button btnRegistre;
    private Button btnLogin;
    private EditText txtUser;
    private Usuario usuario;
    private EditText txtPassword;
    private fireBaseController db;
    private LogInViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_layout);
        viewModel = new ViewModelProvider(this).get(LogInViewModel.class);
        initWidgets();

        final Observer<String> observer = new Observer<String>() {
            @Override
            public void onChanged(String string) {
                Toast toastsuma =
                        Toast.makeText(getApplicationContext(), string.toString(), Toast.LENGTH_SHORT);
                toastsuma.show();
            }
        };

        this.db = new fireBaseController();

        viewModel.getRespuesta().observe(this, observer);
    }

    private void initWidgets() {
        btnRegistre = findViewById(R.id.button_registre);
        txtPassword = findViewById(R.id.edit_text_password);
        txtUser = findViewById(R.id.edit_text_username);
        btnLogin = findViewById(R.id.button_signin);
    }

    public void registreAccio(View view){
        startActivity(new Intent(this, Registre.class));
    }

    public void loginAccio(View view){
        String user = txtUser.getText().toString();
        String passUser = txtPassword.getText().toString();

        if(user.equals("") || passUser.equals("")){
            viewModel.campBuit();
        }else {
            Integer resultat = db.userExist(user,passUser);

            if(resultat == 0){
                viewModel.logInCorrecte();
                this.usuario = Usuario.getInstance(user);
                startActivity(new Intent(getApplicationContext(), CalendariMensual.class));
            }else if(resultat == 1){
                //Contrasenya diferent
                viewModel.contrasenyaDiferent();
            }else if(resultat == 2) {
                //error no document asociat
                viewModel.usuariInexistent();
            }else {
                //fall de descarrega
                viewModel.usuariInexistent();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}