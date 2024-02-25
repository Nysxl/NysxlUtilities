package com.internal.nysxl.NysxlUtilities.TeamManager;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

/**
 * Manages player teams, including team creation, player assignment, and team size limitations.
 */
public class PlayerTeamManager {

    private final HashMap<Team, ArrayList<Player>> teams = new HashMap<>();
    private final JavaPlugin plugin;
    private int maxTeamSize = 5;
    private int maxTeams = 2;

    /**
     * Constructs a PlayerTeamManager with a reference to the JavaPlugin instance.
     *
     * @param plugin The JavaPlugin instance.
     */
    public PlayerTeamManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Constructs a PlayerTeamManager with a reference to the JavaPlugin instance, and a max team size and the maximum number of teams allowed.
     * @param plugin the JavaPlugin instance.
     * @param maxTeamSize Maximum number of players in a team.
     * @param maxTeams Maximum number of teams.
     */
    public PlayerTeamManager(JavaPlugin plugin, int maxTeamSize, int maxTeams) {
        this.plugin = plugin;
        this.maxTeamSize = maxTeamSize;
        this.maxTeams = maxTeams;
    }

    /**
     * Attempts to add a new team to the manager.
     *
     * @param team The team to add.
     * @return true if the team was added successfully, false if the max number of teams has been reached or the team already exists.
     */
    public boolean addTeam(Team team) {
        if (teams.keySet().size() >= maxTeams) {
            plugin.getLogger().log(Level.WARNING, "Max team size reached");
            return false;
        }

        if (teams.keySet().parallelStream().noneMatch(s -> s.isTeam(team))) {
            teams.put(team, new ArrayList<>());
            return true;
        }

        plugin.getLogger().log(Level.WARNING, "Team already exists");
        return false;
    }

    /**
     * Adds a player to a specified team by name.
     *
     * @param player   The player to add.
     * @param teamName The name of the team to which the player should be added.
     * @return true if the player was added successfully, false if the team is at max capacity or does not exist.
     */
    public boolean addPlayerToTeam(Player player, String teamName) {
        Team team = getTeam(teamName);

        if (team != null && teams.get(team).size() >= maxTeamSize) {
            plugin.getLogger().log(Level.WARNING, "Team already at max limit");
            return false;
        }

        if (team != null) {
            teams.get(team).add(player);
            return true;
        }

        plugin.getLogger().log(Level.WARNING, "No team with name: " + teamName + " was found");
        return false;
    }

    /**
     * Retrieves a Team object by its name.
     *
     * @param teamName The name of the team.
     * @return The Team object, or null if no team with that name exists.
     */
    public Team getTeam(String teamName) {
        return teams.keySet().parallelStream().filter(s -> s.isTeam(teamName)).findFirst().orElse(null);
    }

    /**
     * Distributes a list of players among the existing teams, optionally ensuring an even distribution.
     *
     * @param allPlayers A list of players to distribute among teams.
     * @param mustBeEven If true, ensures teams are as evenly filled as possible; may leave some players unassigned if exact distribution is not possible.
     * @return A list of players who could not be assigned to a team due to restrictions.
     */
    public ArrayList<Player> splitPlayerIntoTeams(List<Player> allPlayers, boolean mustBeEven) {
        ArrayList<Player> playersNotAbleToJoin = new ArrayList<>();
        // Implementation details omitted for brevity
        return playersNotAbleToJoin;
    }
}
