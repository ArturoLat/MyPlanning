package com.example.myplanning.activitats.Configuracio;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import com.example.myplanning.R;
import com.example.myplanning.activitats.Diari.Storage;
import com.example.myplanning.activitats.RegistreLogin.AuthActivity;
import com.example.myplanning.activitats.RegistreLogin.ProviderSetUp;
import com.example.myplanning.activitats.Seleccio.Seleccio;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.UUID;

public class Configuracio extends AppCompatActivity {

    private Button btnConfig;
    private ImageView imageProfile;
    public Uri imageUrl;
    private String usuari;
    private String proveidor;
    private String email;
    private SwitchCompat switchCompatNightMode;
    private TextView emailtxt;
    private ProviderSetUp providerSetUp;
    private TextView usuaritxt;
    private TextView proveidortxt;
    private Button tancarSessioBtn;
    private Storage storageClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_layout);
        init();
        System.out.println(email);
        System.out.println(proveidor);
        setup(email, proveidor, usuari);
        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImage();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data!=null && data.getData()!= null){
            imageUrl = data.getData();
            imageProfile.setImageURI(imageUrl);
            storageClass.uploadPicture(findViewById(android.R.id.content), email, this, imageUrl, "ProfileImage");
        }
    }

    private void getImage(){
        storageClass.selectImage(this);
    }

    private void init(){
        storageClass = Storage.getInstance();
        storageClass = new Storage();
        providerSetUp = ProviderSetUp.getInstance();
        imageProfile = findViewById(R.id.imgProfile);
        emailtxt = findViewById(R.id.txtCorreuAuth);
        proveidortxt = findViewById(R.id.txtProveidorAuth);
        tancarSessioBtn = findViewById(R.id.btnTancarSessio);
        usuaritxt = findViewById(R.id.txtUsuariAuth);
        btnConfig = findViewById(R.id.btnConfig);
        switchCompatNightMode = findViewById(R.id.switchNightMode);
        email = providerSetUp.getMail();
        proveidor = providerSetUp.getString();
        usuari = providerSetUp.getUser();
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
