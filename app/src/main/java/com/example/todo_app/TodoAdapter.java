package com.example.todo_app;


import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;

import java.util.List;
import java.util.zip.Inflater;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder>{

    private final List<Todo> data;

    private final OnTodoClickListener todoClickListener;

    public TodoAdapter(List<Todo> todos, OnTodoClickListener todoClickListener) {
        this.data = todos;
        this.todoClickListener = todoClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }


    public void setData(List<Todo> todos){

        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Todo todo = data.get(position);
        String formatted = Utils.formatDate(todo.getDueDate());

        ColorStateList colorStateList= new ColorStateList(new int[][]{
                new int[]{-android.R.attr.state_enabled},
                new int[]{android.R.attr.state_enabled}
        },new int[]{
                Color.LTGRAY, //Disabled
                Utils.priorityColor(todo)
        });

        holder.titleTextView.setText(todo.getTitle());
        holder.todayChip.setText(formatted);
        holder.todayChip.setTextColor(Utils.priorityColor(todo));
        holder.todayChip.setChipIconTint(colorStateList);
        holder.radioButton.setButtonTintList(colorStateList);

    }

    @Override
    public int getItemCount() {
        if(data == null)
            return 0;

        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public AppCompatTextView titleTextView;
        public AppCompatRadioButton radioButton;
        public Chip todayChip;

        OnTodoClickListener onTodoClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.todo_radio_button);
            titleTextView = itemView.findViewById(R.id.todo_row_todo);
            todayChip = itemView.findViewById(R.id.todo_row_chip);
            this.onTodoClickListener = todoClickListener;

            itemView.setOnClickListener(this);

            radioButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            Todo currTodo= data.get(getAdapterPosition());
            if (id == R.id.todo_row_layout){
                onTodoClickListener.onTodoClick(currTodo);
            }
            else if(id == R.id.todo_radio_button){
                onTodoClickListener.onTodoRadioButtonClick(currTodo);
            }

        }
    }
}
