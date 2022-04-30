package com.example.myplanning.activitats.Registre;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myplanning.R;
import com.example.myplanning.activitats.LogIn.LogIn;
import com.example.myplanning.activitats.LogIn.LogInViewModel;
import com.example.myplanning.model.Llista.ComprobarDades;
import com.example.myplanning.model.Llista.Usuario;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registre extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RegistreViewModel viewModel;
    private Usuario usuarioOnline;
    private ComprobarDades comprobarDades;
    private Button btnRegistrarse;
    private EditText txtMail;
    private EditText txtUser;
    private EditText txtPassword;
    private EditText txtPassword2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registre_layout);
        viewModel = new ViewModelProvider(this).get(RegistreViewModel.class);
        initWidgets();
        comprobarDades = new ComprobarDades();
        viewModel.getRespuesta().observe(this, observer);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initWidgets(){
        btnRegistrarse = findViewById(R.id.button_register);
        txtMail = findViewById(R.id.register_mail);
        txtUser = findViewById(R.id.user_register);
        txtPassword = findViewById(R.id.user_pass_register);
        txtPassword2 = findViewById(R.id.user_pass_register2);
    }

    private final Observer<String> observer = new Observer<String>() {
        @Override
        public void onChanged(String string) {
            Toast toastsuma =
                    Toast.makeText(getApplicationContext(), string.toString(), Toast.LENGTH_SHORT);
            toastsuma.show();
        }
    };

    public void registrarseAccio(View view){
        String mail = txtMail.getText().toString();
        String user = txtUser.getText().toString();
        String pass = txtPassword.getText().toString();
        String pass1 = txtPassword2.getText().toString();
                /*byte [] hash = new byte[] {};
                byte [] hash1 = new byte[] {};

                try {
                    MessageDigest md = MessageDigest.getInstance("SHA-256");
                    hash = md.digest(pass.getBytes(StandardCharsets.UTF_8));
                    hash1 = md.digest(pass1.getBytes(StandardCharsets.UTF_8));

                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                if(Arrays.equals(hash, hash1)){
                    Map<String,Object> userData = new HashMap<>();
                    userData.put("mail",mail);
                    userData.put("password",hash.toString());
                    db.collection("users").document(user).set(userData);

                }*/

        if(pass.equals(pass1)){
            String respuesta = comprobarDades.isSecure(pass, mail);
            if(respuesta.equals("Format de l'email incorrecte")){
                viewModel.emailIncorrecte();
            }else if(respuesta.equals("Format de la contrasenya incorrecte")){
                viewModel.contrasenyaIncorrecte();
            }else{
                usuarioOnline = new Usuario(mail);
                usuarioOnline.setUsuariCreat(true);
                Map<String, Object> userData = new HashMap<>();
                userData.put("mail", mail);
                userData.put("password", pass);
                db.collection("users").document(user).set(userData);
                viewModel.registreCorrecte();
                viewModel.getRespuesta().observe(this, observer);

                startActivity(new Intent(this, LogIn.class));

            }
        }
        viewModel.getRespuesta().observe(this, observer);
    }
}
