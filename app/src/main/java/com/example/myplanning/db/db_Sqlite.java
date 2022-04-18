package com.example.myplanning.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class db_Sqlite extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_MYPLANNING = "My_Planning.db";
    private static final String TABLE_YEARS = "t_years";
    private static final String TABLE_MONTH = "t_month";
    private static final String TABLE_DAY = "t_day";
    private static final String TABLE_HORARI = "t_horari";
    private static final String TABLE_TODO = "t_todo";
    private static final String TABLE_HOMEWORK = "t_homework";
    private static final String TABLE_NOTA = "t_nota";

    public db_Sqlite(@Nullable Context context) {
        super(context, DATABASE_MYPLANNING, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_YEARS + "(" +
                "id_año INTEGER PRIMARY KEY AUTOINCREMENT," +
                "año INTEGER NOT NULL)");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_MONTH + "(" +
                "id_mes INTEGER PRIMARY KEY AUTOINCREMENT," +
                "mes INTEGER NOT NULL," +
                "mes_nombre TEXT NOT NULL," +
                "id_año INTEGER NOT NULL," +
                "FOREIGN KEY(id_año) REFERENCES " + TABLE_YEARS +"(id_año))");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_DAY + "(" +
                "id_day INTEGER PRIMARY KEY AUTOINCREMENT," +
                "dia TEXT NOT NULL," +
                "day_happines INTEGER NOT NULL," +
                "id_mes INTEGER NOT NULL," +
                "FOREIGN KEY(id_mes) REFERENCES " + TABLE_MONTH +"(id_mes))");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_HORARI + "(" +
                "id_horari INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tasc TEXT NOT NULL," +
                "time INTEGER NOT NULL," +
                "id_day INTEGER NOT NULL," +
                "FOREIGN KEY(id_day) REFERENCES " + TABLE_DAY +"(id_day))");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_TODO + "(" +
                "id_todo INTEGER PRIMARY KEY AUTOINCREMENT," +
                "todo TEXT NOT NULL," +
                "time INTEGER NOT NULL," +
                "id_day INTEGER NOT NULL," +
                "FOREIGN KEY(id_day) REFERENCES " + TABLE_DAY +"(id_day))");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_HOMEWORK + "(" +
                "id_homework INTEGER PRIMARY KEY AUTOINCREMENT," +
                "homework TEXT NOT NULL," +
                "time INTEGER NOT NULL," +
                "id_day INTEGER NOT NULL," +
                "FOREIGN KEY(id_day) REFERENCES " + TABLE_DAY +"(id_day))");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NOTA + "(" +
                "id_nota INTEGER PRIMARY KEY AUTOINCREMENT," +
                "link TEXT NOT NULL," +
                "id_day INTEGER NOT NULL," +
                "FOREIGN KEY(id_day) REFERENCES " + TABLE_DAY +"(id_day))");

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
