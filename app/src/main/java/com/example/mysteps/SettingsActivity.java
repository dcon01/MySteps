package com.example.mysteps;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;




public class SettingsActivity extends AppCompatActivity {
    int weight;
    int height;
    int goal;
    String speed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }

    public void saveClicked(View view) {

        EditText weightInput = findViewById(R.id.weight);
        EditText heightInput = findViewById(R.id.height);
        EditText goalInput = findViewById(R.id.steps_goal);
        String weightStr = weightInput.getText().toString();
        String heightStr = heightInput.getText().toString();
        String goalStr = goalInput.getText().toString();
        RadioGroup speedRadio = findViewById(R.id.speedRadio);
        int speedID = speedRadio.getCheckedRadioButtonId();
        RadioButton selectedSpeed = findViewById(speedID);
        speed = selectedSpeed.getText().toString();

        if (validateUserInput(weightStr, heightStr, goalStr)) {
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
            weight = Integer.parseInt(weightStr);
            height = Integer.parseInt(heightStr);
            goal = Integer.parseInt(goalStr);
            Intent intent = new Intent(this, Steps.class);
            intent.putExtra("goal" , goal);
//            intent.putExtra("weight", weight);
//            intent.putExtra("height" , height);
//            intent.putExtra("speed" , speed);
            setResult(RESULT_OK, intent);
            startActivity(intent);
            //TODO if an input is entered and then deleted the input is seen as good
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
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("value", goal);
        outState.putInt("value", weight);
        outState.putInt("value", height);
        outState.putString("value", speed);
    }

}