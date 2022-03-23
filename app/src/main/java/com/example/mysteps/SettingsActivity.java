package com.example.mysteps;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;




public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }

    public void saveClicked(View view) {
        EditText weightInput = findViewById(R.id.weight);
        EditText heightInput = findViewById(R.id.height);
        EditText goalInput = findViewById(R.id.steps_goal);
        String weight = weightInput.getText().toString();
        String height = heightInput.getText().toString();
        String goal = goalInput.getText().toString();
        RadioGroup speedRadio = findViewById(R.id.speedRadio);
        int speedID = speedRadio.getCheckedRadioButtonId();
        RadioButton selectedSpeed = findViewById(speedID);
        String speed = selectedSpeed.getText().toString();


        if (validateUserInput(weight, height, goal)) {
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("weight", weight);
            intent.putExtra("height" , height);
            intent.putExtra("goal" , goal);
            intent.putExtra("speed" , speed);
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
}