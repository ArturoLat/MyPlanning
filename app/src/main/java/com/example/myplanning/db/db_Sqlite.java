package com.example.myplanning.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myplanning.model.Item.DayRating;
import com.example.myplanning.model.Item.HomeWork;
import com.example.myplanning.model.Item.Schedule;
import com.example.myplanning.model.Item.ToDo;

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
    private static final String TABLE_NOTA = "t_nota";

    public db_Sqlite(@Nullable Context context) {
        super(context, DATABASE_MYPLANNING, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_HORARI + "(" +
                "id_horari INTEGER PRIMARY KEY AUTOINCREMENT," +
                "done INTEGER NOT NULL," +
                "task TEXT NOT NULL," +
                "time INTEGER NOT NULL)");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_TODO + "(" +
                "id_todo INTEGER PRIMARY KEY AUTOINCREMENT," +
                "done INTEGER NOT NULL," +
                "todo TEXT NOT NULL," +
                "time INTEGER NOT NULL)");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_HOMEWORK + "(" +
                "id_homework INTEGER PRIMARY KEY AUTOINCREMENT," +
                "done INTEGER NOT NULL," +
                "homework TEXT NOT NULL," +
                "time INTEGER NOT NULL)");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NOTA + "(" +
                "id_nota INTEGER PRIMARY KEY AUTOINCREMENT," +
                "link TEXT NOT NULL," +
                "nota INTEGER NOT NULL)");

    }

    public void onCreate(SQLiteDatabase sqLiteDatabase, String creacio) {
        sqLiteDatabase.execSQL(creacio);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_HORARI);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_TODO);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_HOMEWORK);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_NOTA);
        onCreate(sqLiteDatabase);

    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1, String nou_code) {
        sqLiteDatabase.execSQL(nou_code);
        onCreate(sqLiteDatabase, nou_code);
    }

    public void insertSchedule(SQLiteDatabase sqLiteDatabase, Integer id_horari, String task, Integer time) {
        ContentValues values = new ContentValues();
        values.put("id_horari", id_horari);
        values.put("done", 0);
        values.put("task", task);
        values.put("time", time);
        sqLiteDatabase.insert(TABLE_HORARI, null, values);
    }

    public void insertToDo(SQLiteDatabase sqLiteDatabase, Integer id_todo, String todo, Integer time) {
        ContentValues values = new ContentValues();
        values.put("id_todo", id_todo);
        values.put("done", 0);
        values.put("todo", todo);
        values.put("time", time);
        sqLiteDatabase.insert(TABLE_TODO, null, values);
    }

    public void insertTask(SQLiteDatabase sqLiteDatabase, Integer id_homework, String homework, Integer time) {
        ContentValues values = new ContentValues();
        values.put("id_homework", id_homework);
        values.put("done", 0);
        values.put("homework", homework);
        values.put("time", time);
        sqLiteDatabase.insert(TABLE_HOMEWORK, null, values);
    }

    public void insertHappiness(SQLiteDatabase sqLiteDatabase, Integer id_nota, String link, Integer nota) {
        ContentValues values = new ContentValues();
        values.put("id_nota", id_nota);
        values.put("link", link);
        values.put("nota", nota);
        sqLiteDatabase.insert(TABLE_NOTA, null, values);
    }

    @SuppressLint("Range")
    public Map<String, Schedule> readSchedule() {
        Map<String, Schedule> mapSchedule = new HashMap<>();
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
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                LocalDateTime dataFinal = LocalDateTime.parse(dateTime, formatter);
                Schedule schedule = new Schedule(task, done, dataFinal);
                schedule.setId(id);
                mapSchedule.put(task, schedule);
            } while (cursorSchedule.moveToNext());
        }
        cursorSchedule.close();
        return mapSchedule;
    }

    @SuppressLint("Range")
    public Map<String, ToDo> readToDo() {
        Map<String, ToDo> mapToDo = new HashMap<>();
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
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                LocalDateTime dataFinal = LocalDateTime.parse(dateTime, formatter);
                ToDo todo = new ToDo(task, done, dataFinal);
                todo.setId(id);
                mapToDo.put(task, todo);
            } while (cursorToDo.moveToNext());
        }
        cursorToDo.close();
        return mapToDo;
    }

    @SuppressLint("Range")
    public Map<String, HomeWork> readTask() {
        Map<String, HomeWork> mapHomework = new HashMap<>();
        Cursor cursorHomework;
        cursorHomework = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_HORARI, null);
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
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                LocalDateTime dataFinal = LocalDateTime.parse(dateTime, formatter);
                HomeWork homework = new HomeWork(task, done, dataFinal);
                homework.setId(id);
                mapHomework.put(task, homework);
            } while (cursorHomework.moveToNext());
        }
        cursorHomework.close();
        return mapHomework;
    }

    @SuppressLint("Range")
    public Map<String, DayRating> readHappiness() {
        Map<String, DayRating> mapDayRating = new HashMap<>();
        Cursor cursorDayRating;
        cursorDayRating = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_NOTA , null);

        if (cursorDayRating.moveToFirst()) {
            do {
                Integer id = Integer.parseInt(cursorDayRating.getString(cursorDayRating.getColumnIndex("id_nota")));
                String task = cursorDayRating.getString(cursorDayRating.getColumnIndex("link"));
                Integer nota = Integer.parseInt(cursorDayRating.getString(cursorDayRating.getColumnIndex("nota")));
                mapDayRating.put(task, new DayRating(task, nota));
            } while (cursorDayRating.moveToNext());
        }
        cursorDayRating.close();
        return mapDayRating;
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
            long result = sqLiteDatabase.delete(TABLE_NOTA,"id_nota=?",null);
            return result != -1;
        }else{
            return false;
        }
    }

}
