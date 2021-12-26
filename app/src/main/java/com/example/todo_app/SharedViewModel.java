package com.example.todo_app;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Todo> selectedItem = new MutableLiveData<>();


    public void selectItem(Todo todo){
        selectedItem.setValue(todo);
    }
    public LiveData<Todo> getSelectedItem(){
        return selectedItem;
    }
}
