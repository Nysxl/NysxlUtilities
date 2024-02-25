package com.internal.nysxl.NysxlUtilities.StructureManager;

import com.internal.nysxl.NysxlUtilities.Timers.DelayTimer;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

/**
 * Represents a custom structure that can be dynamically generated and removed in the game world.
 * Supports delayed operations for both generation and removal.
 */
public class CustomStructure {

    private ArrayList<Blocks> structureBlocks = new ArrayList<>();
    private String name;
    private DelayTimer timer;

    /**
     * Constructs a CustomStructure with a specified name.
     *
     * @param name The name of the custom structure.
     */
    public CustomStructure(String name){
        this.name = name;
    }

    /**
     * Specifies the blocks that make up this structure.
     *
     * @param structureBlocks An ArrayList of Blocks defining the structure.
     * @return The current CustomStructure instance for chaining.
     */
    public CustomStructure withStructureBlocks(ArrayList<Blocks> structureBlocks){
        this.structureBlocks = structureBlocks;
        return this;
    }

    /**
     * Generates the structure in the world by setting the blocks at their specified locations.
     */
    public void generateStructure(){
        for(Blocks b: structureBlocks){
            b.location().getBlock().setType(b.block().getType());
        }
    }

    /**
     * Removes the structure from the world by setting the locations of its blocks to AIR.
     */
    public void removeStructure(){
        for(Blocks b: structureBlocks){
            b.location().getBlock().setType(Material.AIR);
        }
    }

    /**
     * Initiates a delayed generation of the structure.
     *
     * @param plugin The plugin instance.
     * @param delayedTime The delay in seconds before the structure is generated.
     * @return true if the operation was successfully scheduled, false if a timer is already running.
     */
    public boolean delayedGeneration(JavaPlugin plugin, int delayedTime){
        if(this.timer != null && this.timer.getSecondsLeft() > 0) return false;
        this.timer = new DelayTimer(plugin, this::generateStructure, null, delayedTime);
        return true;
    }

    /**
     * Initiates a delayed removal of the structure.
     *
     * @param plugin The plugin instance.
     * @param delayedTime The delay in seconds before the structure is removed.
     * @return true if the operation was successfully scheduled, false if a timer is already running.
     */
    public boolean delayedRemove(JavaPlugin plugin, int delayedTime){
        if(this.timer != null && this.timer.getSecondsLeft() > 0) return false;
        this.timer = new DelayTimer(plugin, this::removeStructure, null, delayedTime);
        return true;
    }

    /**
     * Checks if there is an ongoing delayed operation for this structure.
     *
     * @return true if a timer is currently running, false otherwise.
     */
    public boolean isTimerRunning(){
        return (timer != null && timer.getSecondsLeft() > 0);
    }

}
