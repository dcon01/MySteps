package com.example.mysteps;

class Steps {
    public Steps() {

    }

    //receive parameters from main activity and check if goal has been met
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




