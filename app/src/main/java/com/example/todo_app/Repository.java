package com.example.todo_app;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Repository {

    private  static Repository sInstance;

    private final TodoDao todoDao;
    private LiveData<List<Todo>> todos;

    private AppDatabase database;

    private  Repository(Context context) {
        database = AppDatabase.getInstance(context);

        todoDao = database.todoDao();
        todos = todoDao.getAllTodos();

    }

    public static Repository getInstance(Context context){

        if(sInstance == null){
            sInstance = new Repository(context);
        }
        return sInstance;
    }

    public LiveData<List<Todo>> getAllTodos(){
        return todos;
    }

    public LiveData<Todo>get(int id){
        return todoDao.get(id);
    }


    public void delete(Todo todo){
        AppDatabase.databaseWriteExecutor.execute(()->todoDao.delete(todo));
    }

    public void addTodo(Todo todo){
        AppDatabase.databaseWriteExecutor.execute(()->todoDao.insertTodo(todo));
    }

    public void update(Todo todo){
        AppDatabase.databaseWriteExecutor.execute(()->todoDao.update(todo));
    }


}