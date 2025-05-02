package com.example.taskmanager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

public class ProfileFragment extends Fragment {
    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_THEME = "theme";

    private TextInputEditText nameInput, emailInput;
    private SwitchMaterial themeSwitch;
    private Button saveButton;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, 0);

        nameInput = view.findViewById(R.id.nameInput);
        emailInput = view.findViewById(R.id.emailInput);
        themeSwitch = view.findViewById(R.id.themeSwitch);
        saveButton = view.findViewById(R.id.saveButton);

        loadUserData();
        setupThemeSwitch();
        setupSaveButton();

        return view;
    }

    private void loadUserData() {
        nameInput.setText(sharedPreferences.getString(KEY_NAME, ""));
        emailInput.setText(sharedPreferences.getString(KEY_EMAIL, ""));
        themeSwitch.setChecked(sharedPreferences.getBoolean(KEY_THEME, false));
    }

    private void setupThemeSwitch() {
        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });
    }

    private void setupSaveButton() {
        saveButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString().trim();
            String email = emailInput.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty()) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_NAME, name);
            editor.putString(KEY_EMAIL, email);
            editor.putBoolean(KEY_THEME, themeSwitch.isChecked());
            editor.apply();

            Toast.makeText(getContext(), "Profile saved successfully", Toast.LENGTH_SHORT).show();
        });
    }
} 