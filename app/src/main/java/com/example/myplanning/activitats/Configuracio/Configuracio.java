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
    private String email;
    private FirebaseStorage storage;
    private StorageReference storageReference;
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

        email = providerSetUp.getMail();
        String proveidor = providerSetUp.getString();
        usuari = providerSetUp.getUser();
        System.out.println(email);
        System.out.println(proveidor);
        setup(email, proveidor, usuari);
        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
    }

    private void initWidgets(){
        providerSetUp = ProviderSetUp.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        imageProfile = findViewById(R.id.imgProfile);
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


    private void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data.getData() == imageUrl){

        }
        if(requestCode == 1 && resultCode == RESULT_OK && data!=null && data.getData()!= null){
            imageUrl = data.getData();
            imageProfile.setImageURI(imageUrl);
            uploadPicture();
        }
    }

    private void uploadPicture() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image...");
        pd.show();
        final String profileKey = "ProfileKey";
        StorageReference riversRef = storageReference.child("image/" + email + "/profilepicture/" + profileKey);

        riversRef.putFile(imageUrl)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                Snackbar.make(findViewById(android.R.id.content), "Image Uploaded.", Snackbar.LENGTH_LONG).show();
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "Failed to Upload", Toast.LENGTH_LONG).show();
            }
        })
        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                pd.setMessage("Percentatge: " + (int) progressPercent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
