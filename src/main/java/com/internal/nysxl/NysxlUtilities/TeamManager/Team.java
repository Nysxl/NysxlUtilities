package com.internal.nysxl.NysxlUtilities.TeamManager;

import org.bukkit.Color;

public class Team{

    /**
     * teamName - the name of the team.
     * teamColor - the color associated with the team.
     */
    private final String teamName;
    private final Color teamColor;

    /**
     * AllowDamage - if players are allowed to deal damage.
     * AllowTeamDamage - if players are allowed to deal damage to their own team.
     */
    private boolean AllowDamage = true;
    private boolean AllowTeamDamage = false;

    /**
     * Constructs a Team with the team name and team color
     * @param teamName name of the team
     * @param teamColor colour of the team
     */
    public Team(String teamName, Color teamColor){
        this.teamName = teamName;
        this.teamColor = teamColor;
    }

    /**
     * Compares two teams to see if they're the same.
     * @param team a team to compare the current team to.
     * @return returns true if the current team is the same as the given team
     */
    public boolean isTeam(Team team){
        return this.teamName.equalsIgnoreCase(team.getTeamName());
    }

    /**
     * Compares two teams to see if they're the same.
     * @param teamName a team name to compare to the current team.
     * @return returns true if the current team is the same as the given team
     */
    public boolean isTeam(String teamName){
        return this.teamName.equalsIgnoreCase(teamName);
    }

    /**
     * @return returns the name of the current team
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * @return returns the color of the current team
     */
    public Color getTeamColor() {
        return teamColor;
    }

    /**
     * @return returns true if the current team is allowed to deal damage
     */
    public boolean isAllowDamage() {
        return AllowDamage;
    }

    /**
     * @param allowDamage if set to true the current team will be allowed to deal damage to other players
     */
    public void setAllowDamage(boolean allowDamage) {
        AllowDamage = allowDamage;
    }

    /**
     * @return returns true if the current team is allowed to deal damage to players in the same team
     */
    public boolean isAllowTeamDamage() {
        return AllowTeamDamage;
    }

    /**
     * @param allowTeamDamage if set to true the current team will be allowed to deal damage to other players in the same team
     */
    public void setAllowTeamDamage(boolean allowTeamDamage) {
        AllowTeamDamage = allowTeamDamage;
    }
}
