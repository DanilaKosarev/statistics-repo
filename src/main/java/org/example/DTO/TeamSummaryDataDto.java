package org.example.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.Map;

public class TeamSummaryDataDto {

    @JsonProperty("team")
    private String teamName;

    @JsonProperty("goals_scored")
    private int goalsScored;

    @JsonProperty("goals_missed")
    private int goalsMissed;

    @JsonProperty("top_scorer")
    private PlayerSummary topPlayerSummary;

    public TeamSummaryDataDto() {
    }

    public TeamSummaryDataDto(String teamName, int goalsScored, int goalsMissed, String topPlayerName, int topPlayerGoals) {
        this.teamName = teamName;
        this.goalsScored = goalsScored;
        this.goalsMissed = goalsMissed;
        this.topPlayerSummary = new PlayerSummary(topPlayerName, topPlayerGoals);
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

    public PlayerSummary getTopPlayerSummary() {
        return topPlayerSummary;
    }

    public void setTopPlayerSummary(PlayerSummary topPlayerSummary) {
        this.topPlayerSummary = topPlayerSummary;
    }

    private class PlayerSummary{

        @JsonProperty("name")
        private String playerName;

        @JsonProperty("goals_scored")
        private int playerGoalsScored;

        public PlayerSummary() {
        }

        public PlayerSummary(String playerName, int playerGoalsScored) {
            this.playerName = playerName;
            this.playerGoalsScored = playerGoalsScored;
        }

        public String getPlayerName() {
            return playerName;
        }

        public void setPlayerName(String playerName) {
            this.playerName = playerName;
        }

        public int getPlayerGoalsScored() {
            return playerGoalsScored;
        }

        public void setPlayerGoalsScored(int playerGoalsScored) {
            this.playerGoalsScored = playerGoalsScored;
        }
    }
}
