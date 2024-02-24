package com.internal.nysxl.NysxlUtilities.StructureManager;

import com.internal.nysxl.NysxlUtilities.Timers.DelayTimer;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class CustomStructure {

    private ArrayList<Blocks> structureBlocks = new ArrayList<>();
    private String name;
    private DelayTimer timer;

    public CustomStructure(String name){
        this.name = name;
    }

    public CustomStructure withStructureBlocks(ArrayList<Blocks> structureBlocks){
        this.structureBlocks = structureBlocks;
        return this;
    }

    public void generateStructure(){
        for(Blocks b: structureBlocks){
            b.location().getBlock().setType(b.block().getType());
        }
    }

    public void removeStructure(){
        for(Blocks b: structureBlocks){
            b.location().getBlock().setType(Material.AIR);
        }
    }

    public boolean delayedGeneration(JavaPlugin plugin, int delayedTime){
        if(this.timer != null && this.timer.getSecondsLeft() > 0) return false;
        this.timer = new DelayTimer(plugin, this::generateStructure, null, delayedTime);
        return true;
    }

    public boolean delayedRemove(JavaPlugin plugin, int delayedTime){
        if(this.timer != null && this.timer.getSecondsLeft() > 0) return false;
        this.timer = new DelayTimer(plugin, this::removeStructure, null, delayedTime);
        return true;
    }

    public boolean isTimerRunning(){
        return (timer != null && timer.getSecondsLeft() > 0);
    }

}
