package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.example.DTO.TeamSummaryDataDto;
import org.example.models.PlayerInfo;
import org.example.models.TeamInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class StatisticsAggregator {

    private static Map<String, TeamInfo> teamsMap;

    private static Map<String, PlayerInfo> playersMap;

    private static ArrayList<TeamInfo> teamsList;

    private static ArrayList<PlayerInfo> playersList;

    private static List<TeamSummaryDataDto> resultStatistics;

    public static Map<String, TeamInfo> getTeamsMap() {
        return teamsMap;
    }

    public static void setTeamsMap(Map<String, TeamInfo> teamsMap) {
        StatisticsAggregator.teamsMap = teamsMap;
    }

    public static Map<String, PlayerInfo> getPlayersMap() {
        return playersMap;
    }

    public static void setPlayersMap(Map<String, PlayerInfo> playersMap) {
        StatisticsAggregator.playersMap = playersMap;
    }

    public static ArrayList<TeamInfo> getTeamsList() {
        return teamsList;
    }

    public static void setTeamsList(ArrayList<TeamInfo> teamsList) {
        StatisticsAggregator.teamsList = teamsList;
    }

    public static ArrayList<PlayerInfo> getPlayersList() {
        return playersList;
    }

    public static void setPlayersList(ArrayList<PlayerInfo> playersList) {
        StatisticsAggregator.playersList = playersList;
    }

    public static List<TeamSummaryDataDto> getResultStatistics() {
        return resultStatistics;
    }

    public static void setResultStatistics(List<TeamSummaryDataDto> resultStatistics) {
        StatisticsAggregator.resultStatistics = resultStatistics;
    }

    public static void extractDataFromFile(String fileName){
        try {
            teamsMap = new HashMap<>();
            playersMap = new HashMap<>();

            Scanner fileScanner = new Scanner(new File("file\\" + fileName));

            fileScanner.nextLine();

            while(fileScanner.hasNext()){
                String[] values = fileScanner.nextLine().split(",");

                String goalScorerTeamName = values[3];
                String goalReceiverTeamName = goalScorerTeamName.equals(values[2]) ? values[1] : values[2];

                if(!teamsMap.containsKey(goalScorerTeamName))
                    teamsMap.put(goalScorerTeamName, new TeamInfo(goalScorerTeamName, 1,0));
                else
                    teamsMap.get(goalScorerTeamName).increaseScoredByOne();

                if(!teamsMap.containsKey(goalReceiverTeamName))
                    teamsMap.put(goalReceiverTeamName, new TeamInfo(goalReceiverTeamName, 0,1));
                else
                    teamsMap.get(goalReceiverTeamName).increaseMissedByOne();

                String playerName = values[4];
                boolean isOwnGoal = Objects.equals(values[6], "TRUE");
                boolean isPenalty = Objects.equals(values[7], "TRUE");

                if(!playersMap.containsKey(playerName))
                    playersMap.put(playerName, new PlayerInfo(playerName, isOwnGoal ? goalReceiverTeamName : goalScorerTeamName, 1, isPenalty ? 1 : 0));
                else {
                    PlayerInfo info = playersMap.get(playerName);
                    info.increaseGoalCountByOne();
                    if(isPenalty)
                        info.increasePenaltyCountByOne();
                }
            }

            fileScanner.close();

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void generateStatistics() throws IOException {
        teamsList = new ArrayList<>(teamsMap.values());
        playersList = new ArrayList<>(playersMap.values());

        Collections.sort(teamsList);

        resultStatistics = new ArrayList<>();

        for(TeamInfo teamInfo : teamsList) {
            PlayerInfo topPlayer = playersList.stream().filter(p -> p.getTeamName().equals(teamInfo.getTeamName())).sorted().findFirst().get();

            resultStatistics.add(new TeamSummaryDataDto(teamInfo.getTeamName(), teamInfo.getGoalsScored(), teamInfo.getGoalsMissed(), topPlayer.getPlayerName(), topPlayer.getGoalCount()));
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        ObjectNode objectNode = mapper.createObjectNode();

        objectNode.putPOJO("teams", resultStatistics);

        mapper.writeValue(new File("data.json"), objectNode);
    }
}
