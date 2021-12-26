package com.example.todo_app;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TodoDao {

    @Insert
    void insertTodo(Todo todo);

    @Query("DELETE FROM todo")
    void deleteAll();

    @Query("SELECT * FROM todo")
    LiveData<List<Todo>> getAllTodos();

    @Query("SELECT * FROM todo WHERE id == :id")
    LiveData<Todo> get(int id);

    @Update
    void update(Todo todo);

    @Delete
    void delete(Todo todo);
}
