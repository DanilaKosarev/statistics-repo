package org.example.models;

import java.util.Comparator;

public class PlayerInfo implements Comparable<PlayerInfo>{

    private String teamName;

    private String playerName;

    private int goalCount;

    private int penaltyCount;

    public PlayerInfo() {
    }

    public PlayerInfo(String playerName, String teamName, int goalCount, int penaltyCount) {
        this.playerName = playerName;
        this.teamName = teamName;
        this.goalCount = goalCount;
        this.penaltyCount = penaltyCount;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getGoalCount() {
        return goalCount;
    }

    public void setGoalCount(int goalCount) {
        this.goalCount = goalCount;
    }

    public int getPenaltyCount() {
        return penaltyCount;
    }

    public void setPenaltyCount(int penaltyCount) {
        this.penaltyCount = penaltyCount;
    }

    public void increaseGoalCountByOne(){
        this.goalCount++;
    }

    public void increasePenaltyCountByOne(){
        this.penaltyCount++;
    }

    @Override
    public int compareTo(PlayerInfo o) {
        return Comparator.comparingInt(PlayerInfo::getGoalCount).thenComparing(PlayerInfo::getPenaltyCount, Comparator.reverseOrder()).compare(o, this);
    }

    @Override
    public String toString() {
        return "PlayerInfo{" +
                "teamName='" + teamName + '\'' +
                ", playerName='" + playerName + '\'' +
                ", goalCount=" + goalCount +
                ", penaltyCount=" + penaltyCount +
                '}';
    }
}
