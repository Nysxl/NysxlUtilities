package com.internal.nysxl.NysxlUtilities.MinigameManager;

import com.internal.nysxl.NysxlUtilities.TeamManager.PlayerTeamManager;
import com.internal.nysxl.NysxlUtilities.Timers.DelayTimer;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.logging.Level;

public class MinigameManager implements GamestateInterface{

    private ArrayList<Gamestate> gameStates;
    private Gamestate currentGameState;
    private PlayerTeamManager teamManager;

    private final JavaPlugin plugin;
    private DelayTimer timer;

    private int timeTillNextGameState;

    public MinigameManager(JavaPlugin plugin, ArrayList<Gamestate> gameStates){
        this.plugin = plugin;
        this.gameStates = gameStates;
        this.teamManager = new PlayerTeamManager(plugin);
        this.timer = new DelayTimer(plugin); // Corrected to assign to the class field
    }

    public Gamestate nextGameState(){
        currentGameState = currentGameState.getNextGameState();
        return currentGameState;
    }

    public void nextGameStateIn(int delay){
        timer.StartTimer(this::nextGameState, this::updateTimeTillNextGameState, delay);
    }

    public void updateTimeTillNextGameState(){
        this.timeTillNextGameState = timer.getSecondsLeft();
    }

    public PlayerTeamManager getTeamManager() {
        return teamManager;
    }

    public ArrayList<Gamestate> getGameStates() {
        return gameStates;
    }

    public Gamestate getCurrentGameState() {
        return currentGameState;
    }

    public int getTimeTillNextGameState() {
        return timeTillNextGameState;
    }

    public Runnable setGameState(Gamestate gameState){
        if(this.gameStates.contains(gameState)) {
            this.currentGameState = gameState;
            return null;
        }
        plugin.getLogger().log(Level.WARNING, "Gamestate: " + gameState.getGameState() + " isnt valid for this gamemode");
        return null;
    }

    public void setGameStateIn(int delay, Gamestate gamestate){
        timer.StartTimer(this.setGameState(gamestate), this::updateTimeTillNextGameState, delay);
    }
}
