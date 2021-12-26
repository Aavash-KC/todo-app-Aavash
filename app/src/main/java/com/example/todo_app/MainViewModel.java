package com.example.todo_app;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    public final Repository repository;
    public final LiveData<List<Todo>> allTodos;


    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);
        allTodos = repository.getAllTodos();

    }

    public LiveData<List<Todo>> getAllTodos(){
        return repository.getAllTodos();
    }

    public void insert(Todo todo){
        repository.addTodo(todo);
    }

    public LiveData<Todo> get(int id){
        return repository.get(id);
    }

    public void update(Todo todo){
        repository.update(todo);
    }

    public  void delete (Todo todo){
        repository.delete(todo);
    }

}