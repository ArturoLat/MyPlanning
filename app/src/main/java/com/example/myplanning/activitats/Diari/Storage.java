package com.example.myplanning.activitats.Diari;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;

import com.example.myplanning.db.db_Sqlite;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Storage {

    private FirebaseStorage storage;
    private StorageReference storageReference;
    public static Storage instance;

    public Storage(){
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        instance = this;
    }

    public static Storage getInstance() {
        return instance;
    }

    public void selectImage(ComponentActivity activity){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(intent, 1);
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

    public void uploadPictureHapiness(View view, String email, Context context, Uri imageUrl, String tipo, String day) {
        final ProgressDialog pd = new ProgressDialog(view.getContext());
        pd.setTitle("Uploading Image...");
        pd.show();
        final String profileKey = day;
        StorageReference riversRef = storageReference.child("image/" + email + "/"+  tipo + "/" + profileKey);

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

}
