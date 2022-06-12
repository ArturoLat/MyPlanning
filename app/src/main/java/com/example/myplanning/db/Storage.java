package com.example.myplanning.db;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;

import com.example.myplanning.model.Usuari.Usuario;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.time.LocalDateTime;

public class Storage {

    private FirebaseStorage storage;
    private fireBaseController firebase = fireBaseController.getInstance();;
    private db_Sqlite dbLite = db_Sqlite.getInstance();
    private StorageReference storageReference;
    public static Storage instance;
    private Uri urlDownload = null;

    public Storage(){
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        instance = this;
    }

    public static Storage getInstance() {
        return instance;
    }

    public void selectImage(ComponentActivity activity){
        if(Usuario.getInstance() != null){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            activity.startActivityForResult(intent, 1);
        }else{
            Intent intent = new Intent(Intent.ACTION_PICK,Uri.parse("content://media/internal/images/media"));
            activity.startActivityForResult(intent, 1);

        }

    }

    public void uploadPicture(View view, String email, Context context, Uri imageUrl, String tipo) {
        final ProgressDialog pd = new ProgressDialog(view.getContext());
        pd.setTitle("Uploading Image...");
        pd.show();
        final String profileKey = "ProfileKey";
        StorageReference riversRef = storageReference.child("image/" + email + "/"+ tipo + "/" + profileKey);

        riversRef.putFile(imageUrl)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Snackbar.make(view, "Image Uploaded.", Snackbar.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(context, "Failed to Upload", Toast.LENGTH_LONG).show();
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

    public void uploadPictureHapiness(View view, String email, Context context, Uri imageUrl, String tipo, LocalDateTime day) {

        final ProgressDialog pd = new ProgressDialog(view.getContext());
        pd.setTitle("Uploading Image...");
        pd.show();
        final String profileKey = day.toString();

        final StorageReference riversRef = storageReference.child("image/" + email + "/"+  tipo + "/" + profileKey);
        UploadTask uploadTask = riversRef.putFile(imageUrl);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return riversRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    urlDownload = task.getResult();
                    insertDbStorage(day);
                    pd.dismiss();
                    Snackbar.make(view, "Image Uploaded.", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void insertDbStorage(LocalDateTime day) {
        if(Usuario.getInstance() != null){
            fireBaseController.setStorageHappiness(Usuario.getInstance().getNom(), day, urlDownload.toString());
        }else{
            dbLite.insertImgHappiness(dbLite.getWritableDatabase(),day,urlDownload.toString());

        }

    }

}
