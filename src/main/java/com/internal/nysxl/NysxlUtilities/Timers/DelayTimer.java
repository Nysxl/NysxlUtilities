package com.internal.nysxl.NysxlUtilities.Timers;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * A class for creating and managing delay timers within a Bukkit plugin.
 * Allows for the execution of actions both periodically and upon completion of the timer.
 */
public class DelayTimer extends BukkitRunnable{

    private JavaPlugin plugin;
    private Runnable onCompletion;
    private Runnable onTick;
    private int secondsLeft;

    /**
     * Constructs a DelayTimer with a specified plugin.
     *
     * @param plugin The plugin instance associated with this timer.
     */
    public DelayTimer(JavaPlugin plugin){
        this.plugin = plugin;
    }

    /**
     * Constructs a DelayTimer with specified actions and duration.
     *
     * @param plugin The plugin instance associated with this timer.
     * @param onCompletion The action to execute upon completion of the timer.
     * @param onTick The action to execute on each tick.
     * @param duration The duration of the timer in seconds.
     */
    public DelayTimer(JavaPlugin plugin, Runnable onCompletion, Runnable onTick, int duration){
        this.plugin = plugin;
        this.onCompletion = onCompletion;
        this.onTick = onTick;
        this.secondsLeft = duration;
        tick();
    }

    /**
     * The main timer logic executed on each tick.
     */
    @Override
    public void run() {
        if(secondsLeft <= 0) {
            scheduledRun(onCompletion);
        }
        tick();
    }

    /**
     * Executes actions on each tick, and schedules the next tick.
     */
    private void tick(){
        if(this.secondsLeft <= 0){
            this.cancel();
            return;
        }
        scheduledRun(onTick);
        this.runTaskTimerAsynchronously(plugin, 0L, 20L);
    }

    /**
     * Schedules the execution of a runnable action.
     *
     * @param runnable The action to be executed.
     */
    private void scheduledRun(Runnable runnable){
        if(runnable == null) return;
        new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTask(plugin);
    }

    /**
     * Starts or restarts the timer with specified actions and duration.
     *
     * @param onCompletion The action to execute upon completion of the timer.
     * @param onTick The action to execute on each tick.
     * @param duration The duration of the timer in seconds.
     */
    public void StartTimer(Runnable onCompletion, Runnable onTick, int duration){
        this.secondsLeft = duration;
        this.onCompletion = onCompletion;
        this.onTick = onTick;
        tick();
    }

    /**
     * Cancels the timer and resets the seconds left to 0.
     */
    public void cancelTimer(){
        this.cancel();
        this.secondsLeft = 0;
    }

    /**
     * Gets the remaining seconds left in the timer.
     *
     * @return The seconds left until the timer completes.
     */
    public int getSecondsLeft() {
        return secondsLeft;
    }
}
