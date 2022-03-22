package com.example.mysteps;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;


public class SettingsActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    private Button save;
    private Button home;
    private EditText weight;
    private EditText height;
    private EditText goal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }

    public void saveClicked(View view){
        EditText weightString = (EditText) findViewById(R.id.weight);

        int height = 0;
        int goal = 0;

    }
}