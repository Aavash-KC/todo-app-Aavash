package com.example.todo_app;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.sql.Date;

@Database(entities = {Todo.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public  abstract class AppDatabase extends RoomDatabase {


    public static final String DATABASE_NAME = "todo_db";

    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context){
        if(sInstance == null){
            sInstance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, AppDatabase.DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return sInstance;
    }
    public abstract TodoDao todoDao();
}