package org.example.models;

import java.util.Comparator;

public class TeamInfo implements Comparable<TeamInfo> {

    private String teamName;

    private int goalsScored;

    private int goalsMissed;

    public TeamInfo() {
    }

    public TeamInfo(String teamName, int goalsScored, int goalsMissed) {
        this.teamName = teamName;
        this.goalsScored = goalsScored;
        this.goalsMissed = goalsMissed;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getGoalsScored() {
        return goalsScored;
    }

    public void setGoalsScored(int goalsScored) {
        this.goalsScored = goalsScored;
    }

    public int getGoalsMissed() {
        return goalsMissed;
    }

    public void setGoalsMissed(int goalsMissed) {
        this.goalsMissed = goalsMissed;
    }

    public void increaseScoredByOne(){
        this.goalsScored++;
    }

    public void increaseMissedByOne(){
        this.goalsMissed++;
    }

    @Override
    public int compareTo(TeamInfo o) {
        return Comparator.comparingInt(TeamInfo::getGoalsScored).thenComparing(TeamInfo::getTeamName, Comparator.reverseOrder()).compare(o, this);
    }

    @Override
    public String toString() {
        return "TeamInfo{" +
                "teamName='" + teamName + '\'' +
                ", goalsScored=" + goalsScored +
                ", goalsMissed=" + goalsMissed +
                '}';
    }
}
