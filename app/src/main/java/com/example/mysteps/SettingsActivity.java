package com.example.mysteps;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;




public class SettingsActivity extends AppCompatActivity {
    int goal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }

    public void saveClicked(View view) {

        EditText goalInput = findViewById(R.id.steps_goal);
        String goal = goalInput.getText().toString();


        if (validateUserInput(goal)) {
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("goal" , goal);
            setResult(RESULT_OK, intent);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Please check your inputs", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean validateUserInput(String... strings) {
        boolean isCorrect = false;
        for (String userInput : strings) {
            isCorrect = !userInput.isEmpty();
        }
        return isCorrect;
    }
    @Override
    public void onBackPressed() {
        saveClicked(null);
    }

    public void homeClicked(View view) {
        saveClicked(null);
    }
//    @Override
//    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putInt("value", goal);
//    }

}