package com.example.todo_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TodosListFragment#} factory method to
 * create an instance of this fragment.
 */
public class TodosListFragment extends Fragment implements OnTodoClickListener{


    private RecyclerView recyclerView;
    private TodoAdapter adapter;
    private MainViewModel viewModel;
    //private List<Todo> todos;
    private FloatingActionButton fabButton;
    private static final String TAG="Item";
    private SharedViewModel sharedViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_todo_list, container, false);

        fabButton = view.findViewById(R.id.fab_button);
        recyclerView = view.findViewById(R.id.todos_list);
        recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //viewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        fabButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sharedViewModel.selectItem(null);
                sharedViewModel.isEdit(false);
                AddTodoFragment fragment = AddTodoFragment.newInstance();
                FragmentManager fm  = getParentFragmentManager();
                fm.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();

            }
        });

        viewModel.getAllTodos().observe(getViewLifecycleOwner(), todos ->  {
            adapter = new TodoAdapter(todos,this);
            recyclerView.setAdapter(adapter);

        });



        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onTodoClick(Todo todo) {
        Log.d("item","onTodoClick: "+ todo.getTitle());
        sharedViewModel.selectItem(todo);
        sharedViewModel.isEdit(true);
        AddTodoFragment fragment = AddTodoFragment.newInstance();
        FragmentManager fm  = getParentFragmentManager();
        fm.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();

    }

    @Override
    public void onTodoRadioButtonClick(Todo todo) {
        Log.d("item","onTodoClick: "+ todo.getTitle());

        viewModel.delete(todo);

        adapter.notifyDataSetChanged();
    }
}