package com.example.myplanning.activitats.Diari;



import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.bumptech.glide.Glide;
import com.example.myplanning.activitats.CalendariUtiles;
import com.example.myplanning.R;
import com.example.myplanning.activitats.Seleccio.Seleccio;
import com.example.myplanning.activitats.observer.imgLogin;
import com.example.myplanning.activitats.observer.imgObserver;
import com.example.myplanning.db.Storage;
import com.example.myplanning.db.db_Sqlite;
import com.example.myplanning.db.fireBaseController;
import com.example.myplanning.model.Item.*;
import com.example.myplanning.model.Usuari.Usuario;

public class CalendariDiari extends AppCompatActivity implements imgObserver {

    private Button diaSetmanaTV;
    private TextView hapiness;
    private ImageView imageHapiness;
    private static RecyclerView scheduleRecycleView;
    private static RecyclerView toDoRecycleView;
    private static RecyclerView tasksRecycleView;
    private RatingBar nota;
    public Uri imageUrl;
    private Context parentContext;
    private DiariViewModel viewModel;
    private LocalDateTime diaActual = CalendariUtiles.selectedDate.atStartOfDay();
    int color;
    private Storage storageClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(DiariViewModel.class);
        parentContext = this.getBaseContext();
        setContentView(R.layout.activity_calendari_diari);
        initWidgets();
        initListeners();
        setLiveDataObservers();
        this.initRating();
        this.initHappines();

        imageHapiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImage();
            }
        });
    }
    private void initListeners() {
        if(viewModel.existUser()){
            fireBaseController.setListenerImg(this);

        }else{
            db_Sqlite.setListenerImg(this);
        }
    }
    private void initHappines() {
        if(viewModel.existUser()){
            viewModel.getImgHappinessFire(diaActual);

        }else{
            viewModel.getImgHappinessSql(diaActual);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(viewModel.existUser()){
            SharedPreferences prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
            String email = prefs.getString("email", null);
            if(requestCode == 1 && resultCode == RESULT_OK && data!=null && data.getData()!= null){
                imageUrl = data.getData();
                imageHapiness.setImageURI(imageUrl);
                storageClass.uploadPictureHapiness(findViewById(android.R.id.content), email, this, imageUrl, "Hapiness", diaActual);
                this.viewModel.setImgHappiness(imageUrl,diaActual);
            }
        }else{
            if(requestCode == 1 && resultCode == RESULT_OK && data.getData()!= null) {
                imageUrl = data.getData();
                String x = getPath(this, imageUrl);
                viewModel.setImgHappiness(Uri.parse(x), diaActual);

            }
        }

    }

    public static String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

            }
                // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id)
                );
                return getDataColumn(context, contentUri, null, null);
            }
                // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;

                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                }
                else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                }
                else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
            // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null); }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs){
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst())
            { final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return null;
    }


    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private void getImage(){
        storageClass.selectImage(this);

    }

    private void initWidgets() {
        hapiness = findViewById(R.id.txtDayHapiness);
        imageHapiness = findViewById(R.id.imageHapiness);
        storageClass = Storage.getInstance();
        storageClass = new Storage();
        this.diaSetmanaTV = findViewById(R.id.btnDia);
        this.nota = findViewById(R.id.notaDia);
        scheduleRecycleView = findViewById(R.id.scheduleRecycleView);
        toDoRecycleView = findViewById(R.id.toDoRecycleView);
        tasksRecycleView = findViewById(R.id.tasksRecycleView);
        scheduleRecycleView.setLayoutManager(new LinearLayoutManager(this));
        toDoRecycleView.setLayoutManager(new LinearLayoutManager(this));
        tasksRecycleView.setLayoutManager(new LinearLayoutManager(this));

    }

    public void initImgHappiness(LocalDateTime time){
        if(viewModel.existUser()){
            viewModel.getImgHappinessFire(time);
        }else{
            viewModel.getImgHappinessSql(time);
        }

    }

    public void initRating(){
        if(!viewModel.existUser()){
            //verifica si hi ha registre de valoracio
            if(!viewModel.emptyRegVal(diaActual)){
                //si no existeix s'inicialitza el valor de val a 0
                nota.setRating(0);
                //s'insereix registre a la base de dades (valor inicial a 0)
                viewModel.insertValoracio(diaActual);

            }
        }
        //get valoracio
        Valoracio val = viewModel.getValoracioDia(diaActual);
        if(val != null){
            nota.setRating(val.getValoracio());

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        setDiaView();

    }

    public void diaSeleccioAccio(View view){
        startActivity(new Intent(this, Seleccio.class));
    }

    private void setDiaView() {
        String diaSetmana = CalendariUtiles.selectedDate.format(DateTimeFormatter
                .ofLocalizedDate(FormatStyle.LONG));
        diaSetmanaTV.setText(diaSetmana);
        diaActual = CalendariUtiles.selectedDate.atStartOfDay();
    }

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable
        viewModel.initDades(diaActual);

        nota.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ratingBar.setRating(v);
                viewModel.updateValoracio(diaActual,v);

            }
        });

        final Observer<ArrayList<Valoracio>> observer0 = new Observer<ArrayList<Valoracio>>() {
            @Override
            public void onChanged(ArrayList<Valoracio> ac) {
                nota.setRating(ac.get(0).getValoracio());
            }
        };

        final Observer<ArrayList<Dades>> observer = new Observer<ArrayList<Dades>>() {
            @Override
            public void onChanged(ArrayList<Dades> ac) {
                ArrayList<Dades> ordenada = ordenarLlista(ac);
                ScheduleAdapter newAdapter = new ScheduleAdapter(ordenada, "Schedule");
                scheduleRecycleView.swapAdapter(newAdapter, false);
                newAdapter.notifyDataSetChanged();
            }
        };

        final Observer<ArrayList<Dades>> observer1 = new Observer<ArrayList<Dades>>() {
            @Override
            public void onChanged(ArrayList<Dades> ac) {
                ArrayList<Dades> ordenada = ordenarLlista(ac);
                ScheduleAdapter newAdapter = new ScheduleAdapter(ordenada,"To-Do");
                toDoRecycleView.swapAdapter(newAdapter, false);
                newAdapter.notifyDataSetChanged();
            }
        };

        final Observer<ArrayList<Dades>> observer2 = new Observer<ArrayList<Dades>>() {
            @Override
            public void onChanged(ArrayList<Dades> ac) {
                ArrayList<Dades> ordenada = ordenarLlista(ac);
                ScheduleAdapter newAdapter = new ScheduleAdapter(ordenada, "Tasks");
                tasksRecycleView.swapAdapter(newAdapter, false);
                newAdapter.notifyDataSetChanged();
            }
        };

        viewModel.getListDatosHomeWork().observe(this, observer2);
        viewModel.getListDatosShedule().observe(this, observer);
        viewModel.getListDatostoDo().observe(this,observer1);
        viewModel.getListValoracio().observe(this,observer0);
    }

    public void nextDayAction(View view){
        CalendariUtiles.selectedDate = CalendariUtiles.selectedDate.plusDays(1);
        setDiaView();
        viewModel.initDades(diaActual);
        this.initRating();
        this.initImgHappiness(diaActual);

    }

    public void prevDayAction(View view){
        CalendariUtiles.selectedDate = CalendariUtiles.selectedDate.minusDays(1);
        setDiaView();
        viewModel.initDades(diaActual);
        this.initRating();
        this.initImgHappiness(diaActual);

    }

    public void notaAction(View view){
        Toast toast = Toast.makeText(this,"Nota", Toast.LENGTH_SHORT);
        toast.show();

    }

    public void toDoAction(View view){
        startActivity(new Intent(this, Tarea.class));

    }

    public void happiAction(View view){
        Toast toast = Toast.makeText(this,"Happy", Toast.LENGTH_SHORT);
        toast.show();

    }

    public void taskAction(View view){
        startActivity(new Intent(this, Tarea.class));
    }

    public void scheduleAction(View view){
        startActivity(new Intent(this, Tarea.class));
    }

    public ArrayList<Dades> ordenarLlista(ArrayList<Dades> llistaEvents) {
        Collections.sort(llistaEvents, Comparator.comparing(e -> e.getDate()));
        return llistaEvents;
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }

    @Override
    public void notificarImatgeHappiness(Object url) {
        if(url != null){
            if(url.getClass().isInstance(String.class)){
                Glide.with(CalendariDiari.this)
                        .load(url)
                        .centerCrop()
                        .into(imageHapiness);
            }else{
                imageHapiness.setImageBitmap((Bitmap) url);

            }
        }else{
            imageHapiness.setImageResource(0);
        }
    }
}