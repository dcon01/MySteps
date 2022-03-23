package com.example.mysteps;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private Steps steps;
    private TextView displayGoal;
    private TextView showSteps;
    private TextView info;
    private String goalSteps;
    private SensorManager sensorManager;
    private Sensor sensor;
    int myGoal;
    boolean running = false;
    int startupCount = 0;
    private final ActivityResultLauncher<String> requestPermissionsLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(),
            okay -> {
                if (okay) {
                    setupSensor();
        }
                else {
                    //TODO add toast
                    System.out.println("This app will not work without sensor permission");
                }
    });

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_GRANTED){
            setupSensor();
        } else {
            requestPermissionsLauncher.launch(Manifest.permission.ACTIVITY_RECOGNITION);
        }
        info = findViewById(R.id.info);
        displayGoal = findViewById(R.id.show_goal);
        showSteps = findViewById(R.id.show_steps);
        steps = new Steps();
        System.out.println("OnCreate called: ");
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            goalSteps = extras.getString("goal");
            displayGoal.setText(goalSteps);
            System.out.println("MainActivity: goal is = "+ goalSteps);
        }
        else{
            System.out.println("probably null");
        }

        if(goalSteps != null) {
            myGoal = Integer.parseInt(goalSteps);
            System.out.println("onCreate: goalsteps = " + goalSteps);
        }

    }
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        System.out.println("onResume Called");
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        System.out.println("onPause Called");
    }

    protected void onDestroy(){
        super.onDestroy();
        System.out.println("onDestroy Called");
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        System.out.println("onSensorChanged: " + sensorEvent.values[0]);
        if(!running){
            startupCount = (int) sensorEvent.values[0];
            System.out.println("onSensorChanged: startupCount is - " + startupCount);
            running = true;
        }
        int currentCount = (int) sensorEvent.values[0];
        System.out.println("onSensorChanged: currentCount is - " + currentCount);
        int mySteps = (currentCount - startupCount);
        System.out.println("onSensorChanged: mySteps is - " + mySteps);


        showSteps.setText(Integer.toString(mySteps));

        appendInfo(Integer.toString(mySteps));
        if(goalSteps != null) {
            if (steps.checkGoal(mySteps, myGoal)){
                System.out.println("onSensorChanged: Call to step.check returned true");
                Toast.makeText(this, "Congratulations you hit your goal!", Toast.LENGTH_SHORT).show();
            }
            System.out.println("OnSensorChange calling steps.check goal with mysteps = "+ mySteps + " & mygoal = " + myGoal);
        }
        //TODO saved instance on


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        System.out.println("onAccuracyChanged: " + sensor);

    }

    private void appendInfo(int resID) { appendInfo(getResources().getString(resID)); }

    private void appendInfo(String message) { info.append("\n" + message); }

    private void setupSensor() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        System.out.println("[sensor manager]: " + sensorManager);
        System.out.println("[sensor]: " + sensor);

        if(sensor == null) {
            //TODO create toast
            System.out.println("[setupSensor]: no sensor found");
            //hasSensor = false;
        }else {
            //TODO create toast
            System.out.println("[setupSensor]: sensor found");

        }
    }

    public void settingsClicked(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

}