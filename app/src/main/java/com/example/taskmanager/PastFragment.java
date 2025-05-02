package com.example.taskmanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class PastFragment extends Fragment {
    private DatabaseHelper dbHelper;
    private List<Task> pastTaskList;
    private TaskAdapter taskAdapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_past, container, false);

        dbHelper = new DatabaseHelper(getContext());
        pastTaskList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.pastTaskRecyclerView);

        taskAdapter = new TaskAdapter(pastTaskList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(taskAdapter);

        loadPastTasks();

        return view;
    }

    private void loadPastTasks() {
        pastTaskList.clear();
        List<Task> allTasks = dbHelper.getAllTasks();
        long currentTime = System.currentTimeMillis();

        for (Task task : allTasks) {
            // Assuming dateTime is in format "YYYY-MM-DD HH:MM"
            String[] dateTimeParts = task.getDateTime().split(" ");
            String[] dateParts = dateTimeParts[0].split("-");
            String[] timeParts = dateTimeParts[1].split(":");

            long taskTime = Long.parseLong(dateParts[0]) * 10000000000L +
                    Long.parseLong(dateParts[1]) * 100000000L +
                    Long.parseLong(dateParts[2]) * 1000000L +
                    Long.parseLong(timeParts[0]) * 10000L +
                    Long.parseLong(timeParts[1]) * 100L;

            if (taskTime < currentTime) {
                pastTaskList.add(task);
            }
        }

        taskAdapter.notifyDataSetChanged();
    }
} 