package com.example.todo_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddTodoFragment#} factory method to
 * create an instance of this fragment.
 */
public class AddTodoFragment extends Fragment implements    View.OnClickListener{

    private EditText titleEditText;
    private EditText descriptionEditText;
    private ImageButton calenderButton;
    private ImageButton priorityButton;
    private RadioGroup priorityRadioGroup;
    private RadioButton selectedRadioButton;
    private int selectedButtonId;
    private boolean isEdit;

    private Priority priority;

    private ImageButton addButton;
    private CalendarView calendarView;
    private Group calenderGroup;

    private Date dueDate;
    Calendar calendar = Calendar.getInstance();

    private MainViewModel viewModel;
    private SharedViewModel sharedViewModel;


    public static AddTodoFragment newInstance(){
        AddTodoFragment fragment = new AddTodoFragment();
        return  fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view  = inflater.inflate(R.layout.fragment_add_todo, container, false);
        titleEditText = view.findViewById(R.id.enter_title_et);
        descriptionEditText = view.findViewById(R.id.enter_discription_et);
        calenderGroup = view.findViewById(R.id.calendar_group);
        calendarView = view.findViewById(R.id.calendar_view);
        calenderButton = view.findViewById(R.id.today_calendar_button);
        priorityButton =  view.findViewById(R.id.priority_todo_button);
        priorityRadioGroup = view.findViewById(R.id.radioGroup_priority);

        Chip todayChip= view.findViewById(R.id.today_chip);
        todayChip.setOnClickListener(this);
        Chip tomorrowChip= view.findViewById(R.id.tomorrow_chip);
        tomorrowChip.setOnClickListener(this);
        Chip nextWeekChip= view.findViewById(R.id.next_week_chip);
        nextWeekChip.setOnClickListener(this);



        addButton = view.findViewById(R.id.save_todo_button);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(sharedViewModel.getSelectedItem().getValue() != null){
            Todo todo = sharedViewModel.getSelectedItem().getValue();
            isEdit = sharedViewModel.getIsEdit();

            titleEditText.setText(todo.getTitle());
            descriptionEditText.setText(todo.getDescription());
            priority = todo.getPriority();

        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                if(!TextUtils.isEmpty(title) && dueDate != null && priority!=null){
                    Todo newTodo = new Todo(title, description, priority,dueDate, Calendar.getInstance().getTime(),false);
                    if(isEdit == true){
                        Todo updateTodo = sharedViewModel.getSelectedItem().getValue();
                        updateTodo.setTitle(title);
                        updateTodo.setDescription(description);
                        updateTodo.setUpdatedAt(Calendar.getInstance().getTime());
                        updateTodo.setDueDate(dueDate);
                        updateTodo.setPriority(priority);
                        viewModel.update(updateTodo);

                        sharedViewModel.isEdit(false);
                        isEdit=false;
                    }
                    else {
                        viewModel.insert(newTodo);
                    }
                    titleEditText.setText("");
                    descriptionEditText.setText("");
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack();
                }
                else{
                    Snackbar.make(addButton,R.string.empty_field,Snackbar.LENGTH_LONG).show();
                }


            }
        });

        calenderButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                calenderGroup.setVisibility(calenderGroup.getVisibility() == View.GONE? View.VISIBLE:View.GONE);
                Utils.hideSoftKeyboard(view);
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                calendar.clear();
                calendar.set(year,month,dayOfMonth);
                dueDate = calendar.getTime();
            }
        });

        priorityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideSoftKeyboard(view);
                priorityRadioGroup.setVisibility(priorityRadioGroup.getVisibility()==View.GONE? View.VISIBLE:View.GONE);
                priorityRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if(priorityRadioGroup.getVisibility()==View.VISIBLE){
                            selectedButtonId = checkedId;
                            selectedRadioButton = view.findViewById(selectedButtonId);
                            if(selectedRadioButton.getId()== R.id.radioButton_high){
                                priority = Priority.HIGH;
                            }
                            else if(selectedRadioButton.getId()== R.id.radioButton_med){
                                priority = Priority.MEDIUM;
                            }
                            else if(selectedRadioButton.getId()== R.id.radioButton_low){
                                priority = Priority.LOW;
                            }
                            else {
                                priority = Priority.LOW;
                            }
                        }else{
                            priority = Priority.LOW;
                        }
                    }
                });
            }
        });


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        //set due date from chips
        if (id == R.id.today_chip){
            calendar.add(Calendar.DAY_OF_YEAR,0);
            dueDate = calendar.getTime();
            Log.d("Time","onClick: "+dueDate.toString());
        }
        else if(id == R.id.tomorrow_chip){
            calendar.add(Calendar.DAY_OF_YEAR,1);
            dueDate = calendar.getTime();
            Log.d("Time","onClick: "+dueDate.toString());
        }
        else if(id == R.id.next_week_chip){
            calendar.add(Calendar.DAY_OF_YEAR,7);
            dueDate = calendar.getTime();
            Log.d("Time","onClick: "+dueDate.toString());
        }
    }
}