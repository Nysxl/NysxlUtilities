package com.internal.nysxl.NysxlUtilities.Timers;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class DelayTimer extends BukkitRunnable{

    private JavaPlugin plugin;

    private Runnable onCompletion;
    private Runnable onTick;

    private int secondsLeft;

    public DelayTimer(JavaPlugin plugin){
        this.plugin = plugin;
    }

    public DelayTimer(JavaPlugin plugin, Runnable onCompletion, Runnable onTick, int duration){
        this.plugin = plugin;
        this.onCompletion = onCompletion;
        this.onTick = onTick;
        this.secondsLeft = duration;
        tick();
    }

    @Override
    public void run() {
        if(secondsLeft <= 0) {
            scheduledRun(onCompletion);
        }

        tick();
    }

    private void tick(){
        if(this.secondsLeft <= 0){
            this.cancel();
            return;
        }

        scheduledRun(onTick);
        this.runTaskTimerAsynchronously(plugin, 0L, 20L);
    }

    private void scheduledRun(Runnable runnable){
        if(runnable == null) return;
        new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTask(plugin);
    }

    public void StartTimer(Runnable onCompletion, Runnable onTick, int duration){
        this.secondsLeft = duration;
        this.plugin = plugin;

        this.onCompletion = onCompletion;
        this.onTick = onTick;

        tick();
    }

    public void cancelTimer(){
        this.cancel();
        this.secondsLeft = 0;
    }

    public int getSecondsLeft() {
        return secondsLeft;
    }
}
