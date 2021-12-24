package com.example.todo_app;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private Repository repository;


    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);
    }

    public List<Todo> getTodos(){
        return repository.getAllTodos();
    }

    public void insert(Todo todo){
        repository.addTodo(todo);
    }
}