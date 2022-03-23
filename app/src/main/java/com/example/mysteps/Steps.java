package com.example.mysteps;
import android.widget.Toast;
import androidx.annotation.NonNull;

class Steps {
    private int goalSteps;
    int steps = 0;

    public Steps() {

    }

    public boolean checkGoal(int steps, int goal){
        System.out.println("Called Steps.CheckSteps - steps: " + steps);
        System.out.println("Called Steps.CheckGoal - goal: " + goal);
        if(steps > goal){
            System.out.println("steps.checkGoal: You have reached your goal");
            return true;
        }
        else {
            return false;
        }
    }
}




