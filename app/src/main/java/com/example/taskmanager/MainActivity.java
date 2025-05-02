package com.example.taskmanager;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter adapter;
    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_THEME = "theme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Apply theme before calling super.onCreate and setContentView
        applyTheme();
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText(R.string.schedule);
                            break;
                        case 1:
                            tab.setText(R.string.past);
                            break;
                        case 2:
                            tab.setText(R.string.notifications);
                            break;
                        case 3:
                            tab.setText(R.string.profile);
                            break;
                    }
                }).attach();
    }

    private void applyTheme() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isDarkMode = sharedPreferences.getBoolean(KEY_THEME, false);
        int mode = isDarkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
        AppCompatDelegate.setDefaultNightMode(mode);
    }
} 