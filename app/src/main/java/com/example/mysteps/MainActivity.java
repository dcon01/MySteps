package com.example.mysteps;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            System.out.println("no saved instance");
        } else {
            System.out.println(savedInstanceState.getString("height"));
            System.out.println(savedInstanceState.getString("weight"));
            System.out.println(savedInstanceState.getString("goal"));
            System.out.println(savedInstanceState.getString("speed"));
        }
    }
    public void settingsClicked(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}