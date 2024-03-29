package edu.paintOnline.game;


public class Timer {
    private double currentTimeInMillisec;
    private double startTimeInMillisec;
    Runnable toExecute;

    public Timer(double startTimerTime) {
        restart(startTimerTime);
    }

    public Timer() {
        restart();
    }

    public void tick(double deltaTime) {
        currentTimeInMillisec -= deltaTime;
        if(isReady()&& toExecute!=null){
            toExecute.run();
            toExecute=null;
        }
    }

    public boolean isReady() {
        if (isOff) {
            return false;
        }
        return currentTimeInMillisec <= 0;
    }

    public void restart(double startTime) {
        isOff = false;
        currentTimeInMillisec = startTime;
        startTimeInMillisec = startTime;
    }

    public void restart() {
        isOff = false;
        currentTimeInMillisec = startTimeInMillisec;
    }

    public void restart(Runnable toExecute) {
        isOff = false;
        currentTimeInMillisec = startTimeInMillisec;
        this.toExecute = toExecute;
    }

    public double getPercent() {
        if (currentTimeInMillisec <= 0) {
            return 0;
        }
        return currentTimeInMillisec * 1. / startTimeInMillisec;
    }

    private boolean isOff = true;

    public void turnOff() {
        isOff = true;
    }

    public boolean isOff() {
        return isOff;
    }
}
