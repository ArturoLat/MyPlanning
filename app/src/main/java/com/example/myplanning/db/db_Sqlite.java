package com.example.myplanning.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class db_Sqlite extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_MYPLANNING = "My_Planning.db";
    private static final String TABLE_HORARI = "t_horari";
    private static final String TABLE_TODO = "t_todo";
    private static final String TABLE_HOMEWORK = "t_homework";
    private static final String TABLE_NOTA = "t_nota";

    public db_Sqlite(@Nullable Context context) {
        super(context, DATABASE_MYPLANNING, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_HORARI + "(" +
                "id_horari INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tasc TEXT NOT NULL," +
                "time INTEGER NOT NULL)");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_TODO + "(" +
                "id_todo INTEGER PRIMARY KEY AUTOINCREMENT," +
                "todo TEXT NOT NULL," +
                "time INTEGER NOT NULL)");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_HOMEWORK + "(" +
                "id_homework INTEGER PRIMARY KEY AUTOINCREMENT," +
                "homework TEXT NOT NULL," +
                "time INTEGER NOT NULL)");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NOTA + "(" +
                "id_nota INTEGER PRIMARY KEY AUTOINCREMENT," +
                "link TEXT NOT NULL)");

    }

    public void onCreate(SQLiteDatabase sqLiteDatabase, String creacio) {
        sqLiteDatabase.execSQL(creacio);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String exemple = "hola";
        sqLiteDatabase.execSQL(exemple);
        onCreate(sqLiteDatabase);

    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1, String nou_code) {
        String exemple = "hola";
        sqLiteDatabase.execSQL(exemple);
        onCreate(sqLiteDatabase,nou_code);

    }
}
