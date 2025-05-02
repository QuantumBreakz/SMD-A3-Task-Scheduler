package com.example.taskmanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.textfield.TextInputEditText;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ScheduleFragment extends Fragment {
    private DatabaseHelper dbHelper;
    private List<Task> taskList;
    private TaskAdapter taskAdapter;
    private RecyclerView recyclerView;
    private TextInputEditText titleInput, descriptionInput, dateTimeInput;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);

        dbHelper = new DatabaseHelper(getContext());
        taskList = new ArrayList<>();

        titleInput = view.findViewById(R.id.titleInput);
        descriptionInput = view.findViewById(R.id.descriptionInput);
        dateTimeInput = view.findViewById(R.id.dateTimeInput);
        Button addButton = view.findViewById(R.id.addButton);
        recyclerView = view.findViewById(R.id.taskRecyclerView);

        taskAdapter = new TaskAdapter(taskList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(taskAdapter);

        addButton.setOnClickListener(v -> addTask());

        loadTasks();

        return view;
    }

    private void addTask() {
        String title = titleInput.getText().toString().trim();
        String description = descriptionInput.getText().toString().trim();
        String dateTime = dateTimeInput.getText().toString().trim();

        if (title.isEmpty() || description.isEmpty() || dateTime.isEmpty()) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Task task = new Task(title, description, dateTime, "pending");
        dbHelper.addTask(task);
        loadTasks();

        titleInput.setText("");
        descriptionInput.setText("");
        dateTimeInput.setText("");
    }

    private void loadTasks() {
        taskList.clear();
        taskList.addAll(dbHelper.getAllTasks());
        taskAdapter.notifyDataSetChanged();
    }
} 