package com.internal.nysxl.NysxlUtilities.Timers;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Manages timed events within a Minecraft plugin, executing specific actions at predetermined intervals.
 * This timer class utilizes a functional programming approach to schedule and execute tasks based on elapsed time in seconds.
 */
public class CountTimer extends BukkitRunnable {

    private final JavaPlugin plugin;
    private long totalSeconds;

    // A map where keys are elapsed time in seconds and values are lists of tasks (Runnables) to be executed at that time.
    private final HashMap<Integer, List<Runnable>> functionsToRunAtTimes = new HashMap<>();

    /**
     * Constructs a new CountTimer instance associated with a specific plugin. This association allows
     * the timer to schedule tasks within the plugin's context.
     *
     * @param plugin The {@link JavaPlugin} instance this timer is associated with.
     */
    public CountTimer(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Registers a {@link Runnable} task to be executed once a specific amount of time has passed.
     *
     * @param time     The time in seconds after which the task should be executed.
     * @param runnable The task to execute, encapsulated in a {@link Runnable}.
     * @return true if the task is successfully added, indicating the operation was successful.
     */
    public boolean addRunnable(int time, Runnable runnable) {
        functionsToRunAtTimes.computeIfAbsent(time, k -> new ArrayList<>()).add(runnable);
        return true;
    }

    /**
     * Executed periodically by the Bukkit scheduler. This method increments the internal second counter
     * and checks if there are any tasks scheduled to run at the current time, executing them if found.
     */
    @Override
    public void run() {
        totalSeconds++;
        runTasksAt((int) totalSeconds);
        // Schedule the next execution of this task 1 second later, asynchronously.
        this.runTaskLaterAsynchronously(plugin, 20L);
    }

    /**
     * Executes all tasks scheduled for the given time.
     *
     * @param time The current time in seconds to check for scheduled tasks.
     * @return true if there were tasks scheduled and executed at the given time; false otherwise.
     */
    private boolean runTasksAt(int time) {
        if (!functionsToRunAtTimes.containsKey(time)) return false;
        functionsToRunAtTimes.get(time).forEach(Runnable::run);
        return true;
    }

    /**
     * Starts the timer, scheduling it to run asynchronously with an initial delay of 1 second.
     */
    public void startTime() {
        this.runTaskLaterAsynchronously(plugin, 20L); // 20 ticks = 1 second.
    }

    /**
     * Computes and returns the elapsed time since the timer started in a structured format.
     *
     * @return An array representing the elapsed time, broken down as follows:
     *         [0] seconds, [1] minutes, [2] hours, [3] days, [4] weeks, [5] months.
     */
    public long[] getCurrentTime() {
        long seconds = totalSeconds % 60;
        long totalMinutes = totalSeconds / 60;
        long minutes = totalMinutes % 60;
        long totalHours = totalMinutes / 60;
        long hours = totalHours % 24;
        long totalDays = totalHours / 24;
        long days = totalDays % 7;
        long weeks = totalDays / 7;
        long months = weeks / 4; // Approximation, as the actual length of a month varies.

        return new long[]{seconds, minutes, hours, days, weeks, months};
    }

    /**
     * Cancels this timer, stopping its execution. Overrides {@link BukkitRunnable}'s cancel method to ensure
     * that this task is properly cancelled within the Bukkit scheduler.
     */
    @Override
    public void cancel() throws IllegalStateException {
        super.cancel(); // Ensure proper cancellation of this task.
    }
}
