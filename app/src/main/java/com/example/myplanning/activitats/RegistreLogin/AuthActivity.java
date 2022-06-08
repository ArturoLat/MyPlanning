package com.example.myplanning.activitats.RegistreLogin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myplanning.R;
import com.example.myplanning.activitats.Configuracio.ProviderType;
import com.example.myplanning.activitats.Mensual.CalendariMensual;
import com.example.myplanning.db.fireBaseController;
import com.example.myplanning.model.Usuari.ComprobarDades;
import com.example.myplanning.model.Usuari.Usuario;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class AuthActivity extends AppCompatActivity {

    private final static int GOOGLE_SIGN_IN = 100;
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
        session();
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

    private void session(){
        SharedPreferences prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
        String email = prefs.getString("email", null);
        String proveidor = prefs.getString("proveidor", null);
        String user = prefs.getString("user", null);
        //Si el email i el proveidor no son nulls, la sessio ja esta logueada
        if(email != null && proveidor != null){
            this.usuarioOnline = new Usuario(email);
            showHome(email, ProviderType.valueOf(proveidor), user);
            startActivity(new Intent(this, CalendariMensual.class));
        }

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
            Integer resultat = db.userExist(mail,pass);
            System.out.println(resultat);
            if(resultat == 3){
                registreViewModel.usuariJaCreat();
            }else{
                Map<String, Object> userData = new HashMap<>();
                userData.put("mail", mail);
                userData.put("password", pass);
                dbRegistre.collection("users").document(mail).set(userData);
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(mail, pass);
                registreViewModel.registreCorrecte();
                registreViewModel.getRespuesta().observe(this, observer);
                this.usuarioOnline = new Usuario(mail);
                showHome(mail, ProviderType.EMAIL, user);
                //Afegim a les preferencies la conta registrada
                afegirAPreferencies();
                startActivity(new Intent(this, CalendariMensual.class));
            }
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
            Integer resultat = db.userExist(mail,pass);

            if(resultat == 0){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(mail, pass);
                logInViewModel.logInCorrecte();
                this.usuarioOnline = new Usuario(mail);
                showHome(mail, ProviderType.EMAIL, user);
                //Afegim la conta a les preferencies
                afegirAPreferencies();
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



    public void loginGoogle(View view){
        GoogleSignInOptions googleConf = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id_string))
                .requestEmail()
        .build();

        GoogleSignInClient googleClient = GoogleSignIn.getClient(this, googleConf);
        googleClient.signOut();
        startActivityForResult(googleClient.getSignInIntent(), GOOGLE_SIGN_IN);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == GOOGLE_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);
                System.out.println(account.getEmail());

                if(account != null){
                    AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                    FirebaseAuth.getInstance().signInWithCredential(credential);
                    showHome(account.getEmail(), ProviderType.GOOGLE, account.getGivenName());
                    Integer resultat = db.userExist(account.getEmail(),account.getId());

                    //Afegim a la base de dades en cas que no hi hagui un usuari existent sino loguem
                    if(resultat == 0){
                        this.usuarioOnline = new Usuario(account.getEmail());
                    }else{
                        Map<String, Object> userData = new HashMap<>();
                        userData.put("mail", account.getEmail());
                        userData.put("password", account.getId());

                        dbRegistre.collection("users").document(account.getEmail()).set(userData);
                        this.usuarioOnline = new Usuario(account.getEmail());
                    }

                    //Cridem al metode de les preferencies
                    afegirAPreferencies();
                    //Comencem la activitat
                    startActivity(new Intent(this, CalendariMensual.class));
                }
            }catch(ApiException e){
                System.out.println("Error: " + e.toString());
                System.out.println("error");
            }


        }
    }



    private void afegirAPreferencies(){
        //Afegim a les preferencies la conta de google, per aixi mantenir la sessio en un futur
        SharedPreferences prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        ProviderSetUp providerSetUp = ProviderSetUp.getInstance();
        String email = providerSetUp.getMail();
        String user = providerSetUp.getUser();
        String proveidor = providerSetUp.getString();
        editor.putString("email", email);
        editor.putString("user", user);
        editor.putString("proveidor", proveidor);
        editor.apply();
    }
    private final Observer<String> observer = new Observer<String>() {
        @Override
        public void onChanged(String string) {
            Toast toastRegistre =
                    Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT);
            toastRegistre.show();
        }
    };

    private void showHome(String email, ProviderType provider, String user){
        ProviderSetUp providerSetUp = new ProviderSetUp(email, provider, user);
    }

}