package com.internal.nysxl.NysxlUtilities.TeamManager;

import org.bukkit.Color;

public class Team{

    private final String teamName;
    private final Color teamColor;

    private boolean AllowDamage = true;
    private boolean AllowTeamDamage = false;

    public Team(String teamName, Color teamColor){
        this.teamName = teamName;
        this.teamColor = teamColor;
    }

    public boolean isTeam(Team team){
        return this.teamName.equalsIgnoreCase(team.getTeamName());
    }

    public boolean isTeam(String teamName){
        return this.teamName.equalsIgnoreCase(teamName);
    }

    public String getTeamName() {
        return teamName;
    }

    public Color getTeamColor() {
        return teamColor;
    }

    public boolean isAllowDamage() {
        return AllowDamage;
    }

    public void setAllowDamage(boolean allowDamage) {
        AllowDamage = allowDamage;
    }

    public boolean isAllowTeamDamage() {
        return AllowTeamDamage;
    }

    public void setAllowTeamDamage(boolean allowTeamDamage) {
        AllowTeamDamage = allowTeamDamage;
    }
}
