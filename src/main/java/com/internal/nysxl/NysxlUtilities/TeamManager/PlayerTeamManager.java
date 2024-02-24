package com.internal.nysxl.NysxlUtilities.TeamManager;

import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class PlayerTeamManager{

    private final HashMap<Team, ArrayList<Player>> teams = new HashMap<>();
    private final JavaPlugin plugin;
    private int maxTeamSize = 5;
    private int maxTeams = 2;

    public PlayerTeamManager(JavaPlugin plugin){
        this.plugin = plugin;
    }

    public boolean addTeam(Team team){
        if(teams.keySet().size() >= maxTeams){
            plugin.getLogger().log(Level.WARNING, "Max team size reached");
            return false;
        }

        if(teams.keySet().parallelStream().noneMatch(s->s.isTeam(team))){
            teams.put(team,new ArrayList<>());
            return true;
        }

        plugin.getLogger().log(Level.WARNING, "Team already exists");
        return false;
    }

    public boolean addPlayerToTeam(Player player, String teamName){
        Team team = getTeam(teamName);

        if(teams.get(team).size() >= maxTeamSize){
            plugin.getLogger().log(Level.WARNING,"Team already at max limit");
            return false;
        }

        if(team != null){
            teams.get(team).add(player);
            return true;
        }

        plugin.getLogger().log(Level.WARNING,"No team with name: " + teamName + " was found");
        return false;
    }

    public Team getTeam(String teamName){
        return teams.keySet().parallelStream().filter(s->s.isTeam(teamName)).findFirst().orElse(null);
    }

    public int getMaxTeamSize() {
        return maxTeamSize;
    }

    public void setMaxTeamSize(int maxTeamSize) {
        this.maxTeamSize = maxTeamSize;
    }

    public int getMaxTeams() {
        return maxTeams;
    }

    public void setMaxTeams(int maxTeams) {
        this.maxTeams = maxTeams;
    }

    public ArrayList<Player> splitPlayerIntoTeams(List<Player> allPlayers, boolean mustBeEven){
        ArrayList<Player> playersNotAbleToJoin = new ArrayList<>();
        int totalPlayers = allPlayers.size();
        int teamsCount = teams.size();
        int idealTeamSize = totalPlayers / teamsCount;

        if (mustBeEven && idealTeamSize > maxTeamSize) {
            idealTeamSize = maxTeamSize;
        }

        int distributablePlayers = idealTeamSize * teamsCount;
        int extraPlayers = totalPlayers - distributablePlayers;

        if (extraPlayers > 0 && mustBeEven) {
            playersNotAbleToJoin.addAll(allPlayers.subList(distributablePlayers, totalPlayers));
            allPlayers = allPlayers.subList(0, distributablePlayers);
        }

        int playerIndex = 0;
        for (Team team : teams.keySet()) {
            ArrayList<Player> teamPlayers = teams.get(team);
            while (teamPlayers.size() < idealTeamSize && playerIndex < allPlayers.size()) {
                Player player = allPlayers.get(playerIndex++);
                if (!addPlayerToTeam(player, team.getTeamName())) {
                    playersNotAbleToJoin.add(player);
                }
            }
        }

        if (playerIndex < allPlayers.size()) {
            playersNotAbleToJoin.addAll(allPlayers.subList(playerIndex, allPlayers.size()));
        }

        return playersNotAbleToJoin;
    }
}
