package com.example.todo_app;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.sql.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Todo.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public  abstract class AppDatabase extends RoomDatabase {



    public static final String DATABASE_NAME = "todo_db";

    private static AppDatabase sInstance;
    private static final Object LOCK = new Object();

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(4);

    public static final RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    databaseWriteExecutor.execute(()->{
                        TodoDao todoDao = sInstance.todoDao();
                        todoDao.deleteAll();

                        //writing to database
                    });
                }
            };

    public static AppDatabase getInstance(Context context){
        if(sInstance == null){
            synchronized (LOCK) {

                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .addCallback(sRoomDatabaseCallback)
                        .build();
            }
        }

        return sInstance;
    }
    public abstract TodoDao todoDao();
}
