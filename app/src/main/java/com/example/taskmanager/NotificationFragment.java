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

public class NotificationFragment extends Fragment {
    private DatabaseHelper dbHelper;
    private List<Notification> notificationList;
    private NotificationAdapter notificationAdapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        dbHelper = new DatabaseHelper(getContext());
        notificationList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.notificationRecyclerView);

        notificationAdapter = new NotificationAdapter(notificationList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(notificationAdapter);

        loadNotifications();

        return view;
    }

    private void loadNotifications() {
        notificationList.clear();
        notificationList.addAll(dbHelper.getAllNotifications());
        notificationAdapter.notifyDataSetChanged();
    }
} 