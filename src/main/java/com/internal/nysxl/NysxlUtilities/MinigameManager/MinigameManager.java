package com.internal.nysxl.NysxlUtilities.MinigameManager;

import com.internal.nysxl.NysxlUtilities.TeamManager.PlayerTeamManager;
import com.internal.nysxl.NysxlUtilities.Timers.DelayTimer;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Manages the lifecycle and states of a minigame, including transitioning between different game states and managing teams.
 */
public class MinigameManager implements GamestateInterface {

    private final ArrayList<Gamestate> gameStates;
    private Gamestate currentGameState;
    private final PlayerTeamManager teamManager;

    private final JavaPlugin plugin;
    private final DelayTimer timer;

    private int timeTillNextGameState;

    /**
     * Constructs a new MinigameManager with a predefined set of game states.
     *
     * @param plugin     The plugin instance this manager is associated with.
     * @param gameStates An ArrayList of Gamestate objects representing the possible states of the minigame.
     */
    public MinigameManager(JavaPlugin plugin, ArrayList<Gamestate> gameStates) {
        this.plugin = plugin;
        this.gameStates = gameStates;
        this.teamManager = new PlayerTeamManager(plugin);
        this.timer = new DelayTimer(plugin);
    }

    /**
     * Advances the minigame to its next state based on the current state's predefined next state.
     *
     * @return The new current game state.
     */
    public Gamestate nextGameState() {
        currentGameState = currentGameState.getNextGameState();
        return currentGameState;
    }

    /**
     * Schedules the transition to the next game state after a specified delay.
     *
     * @param delay The delay in seconds before transitioning to the next game state.
     */
    public void nextGameStateIn(int delay) {
        timer.StartTimer(this::nextGameState, this::updateTimeTillNextGameState, delay);
    }

    /**
     * Updates the remaining time until the next game state transition.
     */
    public void updateTimeTillNextGameState() {
        this.timeTillNextGameState = timer.getSecondsLeft();
    }

    /**
     * Gets the team manager associated with this minigame.
     *
     * @return The PlayerTeamManager instance.
     */
    public PlayerTeamManager getTeamManager() {
        return teamManager;
    }

    /**
     * Retrieves the list of game states for this minigame.
     *
     * @return An ArrayList of Gamestate objects.
     */
    public ArrayList<Gamestate> getGameStates() {
        return gameStates;
    }

    /**
     * Gets the current game state of the minigame.
     *
     * @return The current Gamestate object.
     */
    public Gamestate getCurrentGameState() {
        return currentGameState;
    }

    /**
     * Retrieves the time in seconds until the minigame transitions to the next game state.
     *
     * @return The time in seconds until the next state transition.
     */
    public int getTimeTillNextGameState() {
        return timeTillNextGameState;
    }

    /**
     * Attempts to set the game state immediately to the specified state if it is valid for this minigame.
     *
     * @param gameState The Gamestate to transition to.
     * @return null, indicating no direct runnable action is performed.
     */
    public Runnable setGameState(Gamestate gameState) {
        if (this.gameStates.contains(gameState)) {
            this.currentGameState = gameState;
            return null;
        }
        plugin.getLogger().log(Level.WARNING, "Gamestate: " + gameState.getGameState() + " isn't valid for this gamemode");
        return null;
    }

    /**
     * Schedules setting the game state to a specified state after a delay.
     *
     * @param delay     The delay in seconds before setting the new game state.
     * @param gamestate The Gamestate to set after the delay.
     */
    public void setGameStateIn(int delay, Gamestate gamestate) {
        timer.StartTimer(() -> this.setGameState(gamestate), this::updateTimeTillNextGameState, delay);
    }
}
