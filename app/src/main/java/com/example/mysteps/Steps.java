package com.example.mysteps;

class Steps {
    private int running = 1;
    private int firstStep = 0;

    public Steps() {
    }

    //Setting Steps String representation
    Steps(String representation) {
        String[] elements = representation.split(":");
        this.running = Integer.parseInt(elements[0]);
        this.firstStep = Integer.parseInt(elements[1]);
    }

    //receive parameters from main activity and check if goal has been met
    public boolean checkGoal(int steps, int goal){
        if(steps > goal){
            System.out.println("steps.checkGoal: You have reached your goal");
            return true;
        }
        else {
            return false;
        }
    }

    //receive parameters from main activity and count the steps going from android steps since restart
    public int countSteps(int androidSteps){
        int mySteps;
        //if first time running set first step
        if(running == 1){
            firstStep = androidSteps;
            System.out.println("system initialised");
            System.out.println("Steps: set first step " + firstStep);
            running = 0;
            return 0;
        }
        else{
            mySteps = (androidSteps - firstStep);
            System.out.println("system already running");
        }
        return mySteps;
    }

    //receive parameters from main activity and calculate percentage
    public int returnPercentage(int goal, int steps) {
        if (steps != 0 && goal != 0) {
            System.out.println(goal + steps);
            return ((100 / goal) * steps);
        }
        return 0;
    }
}




