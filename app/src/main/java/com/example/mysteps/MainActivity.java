package com.example.mysteps;
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
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private Steps steps;
    private SensorManager sensorManager;
    private Sensor sensor;
    private TextView showSteps;
    private TextView displayGoal;
    private TextView percentageView;
    private CircularProgressBar circularProgressBar;
    private int currentGoal = 0;
    int mySteps = 0;
    public static final int MAIN_REQUEST = 2;

    //check for or ask for permission to use sensors
    private final ActivityResultLauncher<String> requestPermissionsLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(),
            okay -> {
                if (okay) {
                    setupSensor();
        }
                else {
                    Toast.makeText(this, "No sensor found", Toast.LENGTH_SHORT).show();
                }
    });

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("onCreate Called");

        percentageView = findViewById(R.id.percentage);
        circularProgressBar = findViewById(R.id.yourCircularProgressbar);
        displayGoal = findViewById(R.id.show_goal);
        showSteps = findViewById(R.id.show_steps);
        if(savedInstanceState == null) {
            System.out.println("saved instance state is null");
            steps = new Steps();
        }
        else{
            System.out.println("saved instance not null: " + savedInstanceState) ;
//            steps = new Steps(savedInstanceState.getString("value"));
//            mySteps = savedInstanceState.getInt("mySteps");
//            currentGoal = savedInstanceState.getInt("currentGoal");
            }

        //check for or ask for permission to use sensors
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_GRANTED){
            setupSensor();
        } else {
            requestPermissionsLauncher.launch(Manifest.permission.ACTIVITY_RECOGNITION);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("OnActivity Called");
        super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK) {
                System.out.println("1");
                if (data != null) {
                    String goalFromSettings = data.getStringExtra("goal");
                    displayGoal.setText(goalFromSettings);
                    currentGoal = Integer.parseInt(goalFromSettings);
                    circularProgressBar.setProgressMax(currentGoal);
                }
            }
        }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("value", steps.toString());
        outState.putInt("mySteps", mySteps);
        outState.putInt("currentGoal", currentGoal);
    }

    private void setupSensor() {
        //setting up and error sensors
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(sensor == null) {
            Toast.makeText(this, "No sensor found", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        System.out.println("onResume Called: ");
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        System.out.println("onPause Called: ");
    }

    protected void onDestroy(){
        super.onDestroy();
        System.out.println("onDestroy Called: ");
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        System.out.println("onSensorChanged: " + sensorEvent.values[0]);

        //call Steps count steps to get current step
        mySteps = steps.countSteps((int) sensorEvent.values[0]);

        //show steps on screen
        showSteps.setText(Integer.toString(mySteps));

        //call steps to get percentage
        System.out.println(currentGoal + mySteps);
        int percentage = steps.returnPercentage(currentGoal, mySteps);

        //show percentage on screen
        percentageView.setText(percentage + "%");

        //show progress bar on screen
        circularProgressBar.setProgressWithAnimation(mySteps, 1000L);

        //checking if the goal is met
        if (steps.checkGoal(mySteps, currentGoal)){
            Toast.makeText(this, "Congratulations you hit your goal!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        System.out.println("onAccuracyChanged: " + sensor);
    }

    public void settingsClicked(View view) {
        final Intent intent = new Intent(this, SettingsActivity.class);
        startActivityForResult(intent, MAIN_REQUEST);
    }
}