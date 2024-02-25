package com.internal.nysxl.NysxlUtilities.Timers;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A basic timer class to count how much time has passed since it started,
 * using a simplified approach for time calculation based on the total seconds elapsed.
 */
public class CountTimer extends BukkitRunnable {

    private final JavaPlugin plugin;

    private long totalSeconds;
    private final HashMap<Integer, ArrayList<Runnable>> functiosToRunAtTimes = new HashMap<>();

    /**
     * Constructs a new CountTimer associated with a specific plugin.
     * @param plugin The plugin this timer is associated with, used for scheduling tasks.
     */
    public CountTimer(JavaPlugin plugin){
        this.plugin = plugin;
    }

    /**
     * adds runnables with a set time to the hashmap that will be used to run functions at a set time
     * @param time the amount of seconds that need to have passed for the function to run
     * @param runnable a runnable that needs to be executed at that time
     */
    public boolean addRunnable(int time, Runnable runnable) {
        functiosToRunAtTimes.computeIfAbsent(time, k -> new ArrayList<>()).add(runnable);
        return true;
    }

    /**
     * The run method is called each time the timer ticks, incrementing the total seconds count.
     * checks if there are any functions that need to be run at that given time.
     */
    @Override
    public void run() {
        totalSeconds++;
        runTasksAt((int) totalSeconds);
        this.runTaskLaterAsynchronously(plugin, 20L);
    }

    /**
     * runs the functions at the given time more of an internal method.
     * @param time time in seconds.
     * @return returns true if a function is available at the given time and false if there's not.
     */
    private boolean runTasksAt(int time){
        if(!this.functiosToRunAtTimes.containsKey(time)) return false;
        functiosToRunAtTimes.get(time).forEach(Runnable::run);
        return true;
    }

    /**
     * Starts the timer asynchronously.
     */
    public void startTime(){
        this.runTaskLaterAsynchronously(plugin, 20L); // Start the task with a 1 second delay.
    }

    /**
     * Calculates and returns the total time in a structured format.
     * @return return a long array. index values as follows [1]seconds [2]minutes [3]hours [4]days [5]weeks [6]months
     */
    public long[] getCurrentTime() {
        long[] time = new long[6];

        long seconds = totalSeconds % 60;
        long totalMinutes = totalSeconds / 60;
        long minutes = totalMinutes % 60;
        long totalHours = totalMinutes / 60;
        long hours = totalHours % 24;
        long totalDays = totalHours / 24;
        long days = totalDays % 7;
        long weeks = totalDays / 7;
        long months = weeks / 4;

        time[0] = seconds;
        time[1] = minutes;
        time[2] = hours;
        time[3] = days;
        time[4] = weeks;
        time[5] = months;

        return time;
    }

    /**
     * Overrides the cancel method to ensure that this task is properly cancelled.
     */
    @Override
    public void cancel() throws IllegalStateException {
        super.cancel(); // Call BukkitRunnable's cancel method to cancel this task.
    }
}
