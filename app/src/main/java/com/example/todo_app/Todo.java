package com.example.todo_app;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.UUID;

@Entity(tableName = "todo")
public class Todo {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private Priority priority;
    @ColumnInfo(name="due_date")
    private Date dueDate;
    @ColumnInfo(name = "updated_at")
    private Date updatedAt;
    private boolean isDone;



    public Todo(int id, String title, String description, Priority priority,Date dueDate ,Date updatedAt,boolean isDone) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.updatedAt = updatedAt;
        this.isDone =isDone;
    }




    @Ignore
    public Todo(String title, String description, Priority priority,Date dueDate, Date updatedAt,boolean isDone) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate =dueDate;
        this.updatedAt = updatedAt;
        this.isDone =isDone;
    }





    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Date getDueDate() {
        return dueDate;
    }
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }


    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", dueDate=" + dueDate +
                ", updatedAt=" + updatedAt +
                ", isDone=" + isDone +
                '}';
    }
}
