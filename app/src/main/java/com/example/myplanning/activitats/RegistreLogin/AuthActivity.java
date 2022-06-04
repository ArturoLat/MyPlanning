package com.example.myplanning.activitats.RegistreLogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myplanning.R;
import com.example.myplanning.activitats.Configuracio.ProviderType;
import com.example.myplanning.activitats.Mensual.CalendariMensual;
import com.example.myplanning.db.fireBaseController;
import com.example.myplanning.model.Usuari.ComprobarDades;
import com.example.myplanning.model.Usuari.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AuthActivity extends AppCompatActivity {

    private Button btnRegistre;
    private EditText txtEmail;
    private EditText txtUser;
    private EditText txtPassword;
    private Button btnLogin;

    private fireBaseController db;
    private FirebaseFirestore dbRegistre = FirebaseFirestore.getInstance();
    private Usuario usuarioOnline;
    private ComprobarDades comprobarDades;
    private RegistreViewModel registreViewModel;
    private LogInViewModel logInViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_layout);
        comprobarDades = new ComprobarDades();
        this.db = new fireBaseController();
        registreViewModel = new ViewModelProvider(this).get(RegistreViewModel.class);
        logInViewModel = new ViewModelProvider(this).get(LogInViewModel.class);
        initWidgets();



    }

    private void initWidgets(){
        btnRegistre = findViewById(R.id.btnRegistreAuth);
        btnLogin = findViewById(R.id.btnLoginAuth);
        txtEmail = findViewById(R.id.emailAuth);
        txtUser = findViewById(R.id.userAuth);
        txtPassword = findViewById(R.id.passAuth);
    }

    public void registrarseAccio(View view){
        String pass = txtPassword.getText().toString();
        String mail = txtEmail.getText().toString();
        String user = txtUser.getText().toString();

        String respuesta = comprobarDades.isSecure(pass, mail);
        if(respuesta.equals("Format de l'email incorrecte")){

            registreViewModel.emailIncorrecte();
        }else if(respuesta.equals("Format de la contrasenya incorrecte")){
            registreViewModel.contrasenyaIncorrecte();
        }else{
            usuarioOnline = new Usuario(mail);
            Map<String, Object> userData = new HashMap<>();
            userData.put("mail", mail);
            userData.put("password", pass);

            dbRegistre.collection("users").document(user).set(userData);
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(mail, pass);
            registreViewModel.registreCorrecte();
            registreViewModel.getRespuesta().observe(this, observer);
            showHome(mail, ProviderType.BASIC);
            startActivity(new Intent(this, CalendariMensual.class));

        }
        registreViewModel.getRespuesta().observe(this, observer);
    }

    public void loguearseAccio(View view){
        String pass = txtPassword.getText().toString();
        String mail = txtEmail.getText().toString();
        String user = txtUser.getText().toString();

        if(user.equals("") || pass.equals("")){
            logInViewModel.campBuit();
        }else {
            Integer resultat = db.userExist(user,pass);

            if(resultat == 0){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(mail, pass);
                logInViewModel.logInCorrecte();
                this.usuarioOnline = new Usuario(user);
                showHome(mail, ProviderType.BASIC);
                startActivity(new Intent(this, CalendariMensual.class));
            }else if(resultat == 1){
                //Contrasenya diferent
                logInViewModel.contrasenyaDiferent();
            }else if(resultat == 2) {
                //error no document asociat
                logInViewModel.usuariInexistent();
            }else {
                //fall de descarrega
                logInViewModel.usuariInexistent();
            }
        }
    }

    private final Observer<String> observer = new Observer<String>() {
        @Override
        public void onChanged(String string) {
            Toast toastRegistre =
                    Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT);
            toastRegistre.show();
        }
    };

    private void showHome(String email, ProviderType provider){
        ProviderSetUp providerSetUp = new ProviderSetUp(email, provider);
    }

}