package com.example.myplanning.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myplanning.R;
import com.example.myplanning.activitats.observer.llistArrayObserver;
import com.example.myplanning.model.Item.Dades;
import com.example.myplanning.model.Item.DayRating;
import com.example.myplanning.model.Item.HomeWork;
import com.example.myplanning.model.Item.Schedule;
import com.example.myplanning.model.Item.ToDo;
import com.example.myplanning.model.Item.Valoracio;
import com.thebluealliance.spectrum.SpectrumPalette;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class db_Sqlite extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_MYPLANNING = "My_Planning.db";
    private static final String TABLE_HORARI = "t_horari";
    private static final String TABLE_TODO = "t_todo";
    private static final String TABLE_HOMEWORK = "t_homework";
    private static final String TABLE_VALORACIO = "t_valoracio";

    public static llistArrayObserver listener;
    public static db_Sqlite instance;
    public static SQLiteDatabase database;


    public db_Sqlite(@Nullable Context context) {
        super(context, DATABASE_MYPLANNING, null, DATABASE_VERSION);
        this.database = this.getWritableDatabase();
        this.instance = this;
    }

    public static SQLiteDatabase getDatabase() {
        return database;
    }

    public static void setListener(llistArrayObserver listener) {
        db_Sqlite.listener = listener;
    }

    public static db_Sqlite getInstance() {
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_HORARI + "(" +
                "id_horari INTEGER PRIMARY KEY AUTOINCREMENT," +
                "done INTEGER NOT NULL," +
                "task TEXT NOT NULL," +
                "time TEXT NOT NULL," +
                "color INTEGER NOT NULL)");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_TODO + "(" +
                "id_todo INTEGER PRIMARY KEY AUTOINCREMENT," +
                "done INTEGER NOT NULL," +
                "todo TEXT NOT NULL," +
                "time TEXT NOT NULL,"+
                "color INTEGER NOT NULL)");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_HOMEWORK + "(" +
                "id_homework INTEGER PRIMARY KEY AUTOINCREMENT," +
                "done INTEGER NOT NULL," +
                "homework TEXT NOT NULL," +
                "time TEXT NOT NULL," +
                "color INTEGER NOT NULL)");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_VALORACIO + "(" +
                "id_nota INTEGER PRIMARY KEY AUTOINCREMENT," +
                "date TEXT NOT NULL," +
                "nota REAL NOT NULL)");

    }

    public void onCreate(SQLiteDatabase sqLiteDatabase, String creacio) {
        sqLiteDatabase.execSQL(creacio);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_HORARI);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_TODO);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_HOMEWORK);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_VALORACIO);
        onCreate(sqLiteDatabase);

    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1, String nou_code) {
        sqLiteDatabase.execSQL(nou_code);
        onCreate(sqLiteDatabase, nou_code);
    }

    public void insertSchedule(SQLiteDatabase sqLiteDatabase, String task, String time, int color) {
        ContentValues values = new ContentValues();
        values.put("done", 0);
        values.put("task", task);
        values.put("time", time);
        values.put("color", color);
        sqLiteDatabase.insert(TABLE_HORARI, null, values);
    }

    public void insertToDo(SQLiteDatabase sqLiteDatabase, String todo, String time, int color) {
        ContentValues values = new ContentValues();
        values.put("done", 0);
        values.put("todo", todo);
        values.put("time", time);
        values.put("color", color);
        sqLiteDatabase.insert(TABLE_TODO, null, values);
    }

    public void insertTask(SQLiteDatabase sqLiteDatabase, String homework, String time, int color) {
        ContentValues values = new ContentValues();
        values.put("done", 0);
        values.put("homework", homework);
        values.put("time", time);
        values.put("color", color);
        sqLiteDatabase.insert(TABLE_HOMEWORK, null, values);
    }

    public void insertValoracio(SQLiteDatabase sqLiteDatabase, String date, Float nota) {
        ContentValues values = new ContentValues();
        values.put("date", date);
        values.put("nota", nota);
        sqLiteDatabase.insert(TABLE_VALORACIO, null, values);
    }

    @SuppressLint("Range")
    public Map<String, Dades> readSchedule(LocalDateTime dia) {
        Map<String, Dades> mapSchedule = new HashMap<>();
        Cursor cursorSchedule;
        cursorSchedule = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_HORARI, null);
        boolean done = false;

        if (cursorSchedule.moveToFirst()) {
            do {
                Integer id = Integer.parseInt(cursorSchedule.getString(cursorSchedule.getColumnIndex("id_horari")));
                Integer doneHandle = Integer.parseInt(cursorSchedule.getString(cursorSchedule.getColumnIndex("done")));
                if (doneHandle == 1) {
                    done = true;
                }
                String task = cursorSchedule.getString(cursorSchedule.getColumnIndex("task"));
                String dateTime = cursorSchedule.getString(cursorSchedule.getColumnIndex("time"));

                int color = Integer.parseInt(cursorSchedule.getString(cursorSchedule.getColumnIndex("color")));

                Dades schedule = new Dades(task, done, dateTime, color);
                schedule.setId(id);
                mapSchedule.put(task, schedule);
            } while (cursorSchedule.moveToNext());
        }
        cursorSchedule.close();
        if(!mapSchedule.isEmpty()){
            Map<String, Dades> resultat = new HashMap<>();
            //busquem les dades segons el dia seleccionat
            for(Dades dade : mapSchedule.values()){
                if(dade.compareDate(dia)){
                    resultat.put(dade.getActivitat(),dade);

                }
            }

            return resultat;

        }
        return null;
    }

    @SuppressLint("Range")
    public Map<String, Dades> readToDo(LocalDateTime dia) {
        Map<String, Dades> mapToDo = new HashMap<>();
        Cursor cursorToDo;
        cursorToDo = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_TODO, null);
        boolean done = false;
        if (cursorToDo.moveToFirst()) {
            do {
                Integer id = Integer.parseInt(cursorToDo.getString(cursorToDo.getColumnIndex("id_todo")));
                Integer doneHandle = Integer.parseInt(cursorToDo.getString(cursorToDo.getColumnIndex("done")));
                if (doneHandle == 1) {
                    done = true;
                }
                String task = cursorToDo.getString(cursorToDo.getColumnIndex("todo"));

                String dateTime = cursorToDo.getString(cursorToDo.getColumnIndex("time"));

                int color = Integer.parseInt(cursorToDo.getString(cursorToDo.getColumnIndex("color")));

                Dades todo = new Dades(task, done, dateTime,color);
                todo.setId(id);
                mapToDo.put(task, todo);
            } while (cursorToDo.moveToNext());
        }
        cursorToDo.close();
        if(!mapToDo.isEmpty()){
            Map<String, Dades> resultat = new HashMap<>();
            //busquem les dades segons el dia seleccionat
            for(Dades dade : mapToDo.values()){
                if(dade.compareDate(dia)){
                    resultat.put(dade.getActivitat(),dade);

                }
            }
            return resultat;

        }
        return null;
    }

    @SuppressLint("Range")
    public Map<String, Dades> readTask(LocalDateTime dia) {
        Map<String, Dades> mapHomework = new HashMap<>();
        Cursor cursorHomework;
        cursorHomework = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_HOMEWORK, null);
        boolean done = false;

        if (cursorHomework.moveToFirst()) {
            do {
                Integer id = Integer.parseInt(cursorHomework.getString(cursorHomework.getColumnIndex("id_homework")));
                Integer doneHandle = Integer.parseInt(cursorHomework.getString(cursorHomework.getColumnIndex("done")));
                if (doneHandle == 1) {
                    done = true;
                }
                String task = cursorHomework.getString(cursorHomework.getColumnIndex("homework"));
                String dateTime = cursorHomework.getString(cursorHomework.getColumnIndex("time"));

                int color = Integer.parseInt(cursorHomework.getString(cursorHomework.getColumnIndex("color")));

                Dades homework = new Dades(task, done, dateTime, color);
                homework.setId(id);
                mapHomework.put(task, homework);
            } while (cursorHomework.moveToNext());
        }
        cursorHomework.close();
        if(!mapHomework.isEmpty()){
            Map<String, Dades> resultat = new HashMap<>();
            //busquem les dades segons el dia seleccionat
            for(Dades dade : mapHomework.values()){
                if(dade.compareDate(dia)){
                    resultat.put(dade.getActivitat(),dade);

                }
            }
            return resultat;

        }
        return null;
    }

    @SuppressLint("Range")
    public boolean existValoracio(LocalDateTime dia) {
        Cursor cursorDayRating;
        String month = "";
        String day = "";
        if(dia.getMonthValue() < 10){
            month = "0"+dia.getMonthValue();

        }else{
            month = String.valueOf(dia.getMonthValue());
        }
        if(dia.getDayOfMonth() < 10){
            day = "0"+dia.getDayOfMonth();

        }else{
            day = String.valueOf(dia.getDayOfMonth());
        }
        String search = dia.getYear()+"-"+month+"-"+day+"%";
        cursorDayRating = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_VALORACIO + " WHERE date LIKE " + "'" + search + "'", null);

        if (cursorDayRating.moveToFirst()) {
            return true;
        }
        cursorDayRating.close();

        return false;
    }

    @SuppressLint("Range")
    public float readValoracio(LocalDateTime dia) {
        float resultat = 0;
        Cursor cursorDayRating;
        String month = "";
        String day = "";
        if(dia.getMonthValue() < 10){
            month = "0"+dia.getMonthValue();

        }else{
            month = String.valueOf(dia.getMonthValue());
        }
        if(dia.getDayOfMonth() < 10){
            day = "0"+dia.getDayOfMonth();

        }else{
            day = String.valueOf(dia.getDayOfMonth());
        }
        String search = dia.getYear()+"-"+month+"-"+day+"%";
        cursorDayRating = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_VALORACIO + " WHERE date LIKE " + "'" + search + "'", null);

        if (cursorDayRating.moveToFirst()) {

            Integer id = Integer.parseInt(cursorDayRating.getString(cursorDayRating.getColumnIndex("id_nota")));
            String date = cursorDayRating.getString(cursorDayRating.getColumnIndex("date"));
            resultat = Float.parseFloat(cursorDayRating.getString(cursorDayRating.getColumnIndex("nota")));
        }
        cursorDayRating.close();

        return resultat;
    }

    @SuppressLint("Range")
    public void updateValoracio(LocalDateTime dia, float nota) {
        float resultat = 0;
        Cursor cursorDayRating;
        String month = "";
        String day = "";
        if(dia.getMonthValue() < 10){
            month = "0"+dia.getMonthValue();

        }else{
            month = String.valueOf(dia.getMonthValue());
        }
        if(dia.getDayOfMonth() < 10){
            day = "0"+dia.getDayOfMonth();

        }else{
            day = String.valueOf(dia.getDayOfMonth());
        }
        String search = dia.getYear()+"-"+month+"-"+day+"%";
        this.database.execSQL("UPDATE "+ TABLE_VALORACIO +" SET nota" + " = " + nota + " WHERE date LIKE " + "'" + search + "'");

    }

    public Boolean deleteSchedule(SQLiteDatabase sqLiteDatabase, Integer id_horari) {
        Cursor cursorSchedule;
        cursorSchedule = getWritableDatabase().rawQuery("Select * from TABLE_HORARI where id_horari = ?",null);
        if(cursorSchedule.getCount()>0) {
            long result = sqLiteDatabase.delete(TABLE_HORARI,"id_horari=?",null);
            return result != -1;
        }else{
            return false;
        }
    }

    public Boolean deleteToDo(SQLiteDatabase sqLiteDatabase, Integer id_todo) {
        Cursor cursorTodo;
        cursorTodo = getWritableDatabase().rawQuery("Select * from TABLE_TODO where id_todo = ?",null);
        if(cursorTodo.getCount()>0) {
            long result = sqLiteDatabase.delete(TABLE_TODO,"id_todo=?",null);
            return result != -1;
        }else{
            return false;
        }
    }

    public Boolean deleteHomework(SQLiteDatabase sqLiteDatabase, Integer id_homework) {
        Cursor cursorHomeWork;
        cursorHomeWork = getWritableDatabase().rawQuery("Select * from TABLE_HOMEWORK where id_homework = ?",null);
        if(cursorHomeWork.getCount()>0) {
            long result = sqLiteDatabase.delete(TABLE_HOMEWORK,"id_homework=?",null);
            return result != -1;
        }else{
            return false;
        }
    }

    public Boolean deleteHappiness(SQLiteDatabase sqLiteDatabase, Integer id_nota) {
        Cursor cursorDayRating = null;
        cursorDayRating = getWritableDatabase().rawQuery("Select * from TABLE_NOTA where id_nota = ?",null);
        if(cursorDayRating.getCount()>0) {
            long result = sqLiteDatabase.delete(TABLE_VALORACIO,"id_nota=?",null);
            return result != -1;
        }else{
            return false;
        }
    }

}
