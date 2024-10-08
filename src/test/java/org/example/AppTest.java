package org.example;

import org.example.DTO.TeamSummaryDataDto;
import org.example.models.PlayerInfo;
import org.example.models.TeamInfo;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import static java.util.Map.entry;
import static org.assertj.core.api.Assertions.assertThat;

class AppTest {

    private final String filename = "goalscorers.csv";

    private final String aTeamName = "AName";
    private final String bTeamName = "BName";
    private final String cTeamName = "CName";
    private final String teamNameLessGoalsCount = "lessGoalsTeamName";
    private final String teamNameEvenLessGoalsCount = "evenLessGoalsCountTeamName";

    private final String playerNameLessPenalties = "playerWithSameGoalsCountButLessPenalties";
    private final String playerNameMorePenalties = "playerWithSameGoalsCountButMorePenalties";

    private final int teamGoalsCount = 10;
    private final int playerGoalsCount = 10;

    private final Map<String, TeamInfo> teamsMap = Map.ofEntries(
            entry(aTeamName, new TeamInfo(aTeamName, teamGoalsCount, 0)),
            entry(bTeamName, new TeamInfo(bTeamName, teamGoalsCount, 0)),
            entry(cTeamName, new TeamInfo(cTeamName, teamGoalsCount, 0)),
            entry(teamNameLessGoalsCount, new TeamInfo(teamNameLessGoalsCount, teamGoalsCount - 5, 0)),
            entry(teamNameEvenLessGoalsCount, new TeamInfo(teamNameEvenLessGoalsCount, teamGoalsCount - 9, 0))
    );

    private final Map<String, PlayerInfo> playersMap = Map.ofEntries(
            entry("playerName1", new PlayerInfo(playerNameMorePenalties, aTeamName, playerGoalsCount, 10)),
            entry("playerName2", new PlayerInfo(playerNameLessPenalties, aTeamName, playerGoalsCount, 0)),
            entry("playerName3", new PlayerInfo("playerName3", bTeamName, playerGoalsCount, 10)),
            entry("playerName4", new PlayerInfo(playerNameLessPenalties, bTeamName, playerGoalsCount, 0)),
            entry("playerName5", new PlayerInfo("playerName5", cTeamName, playerGoalsCount, 0)),
            entry("playerName6", new PlayerInfo(playerNameMorePenalties, teamNameLessGoalsCount, playerGoalsCount, 10)),
            entry("playerName7", new PlayerInfo("playerName7", teamNameEvenLessGoalsCount, playerGoalsCount, 10))
    );

    @Test
    void countOfGoalsShouldMatchCountOfLinesInTheFileMinusOne() throws IOException {
        //given
        BufferedReader reader = new BufferedReader(new FileReader("file\\" + filename));
        int lines = 0;
        while (reader.readLine() != null) lines++;
        reader.close();

        lines -= 1;

        //when
        StatisticsAggregator.extractDataFromFile(filename);
        StatisticsAggregator.generateStatistics();

        //then
        assertThat(StatisticsAggregator.getTeamsList().stream().mapToInt(TeamInfo::getGoalsScored).sum()).isEqualTo(lines);
        assertThat(StatisticsAggregator.getTeamsList().stream().mapToInt(TeamInfo::getGoalsMissed).sum()).isEqualTo(lines);
    }

    @Test
    void teamsShouldBeSortedByGoalsCount() throws IOException {
        //given
        StatisticsAggregator.setTeamsMap(teamsMap);
        StatisticsAggregator.setPlayersMap(playersMap);

        //when
        StatisticsAggregator.generateStatistics();

        //then
        assertThat(StatisticsAggregator.getResultStatistics().get(StatisticsAggregator.getResultStatistics().size() - 2).getTeamName()).isEqualTo(teamNameLessGoalsCount);
        assertThat(StatisticsAggregator.getResultStatistics().get(StatisticsAggregator.getResultStatistics().size() - 1).getTeamName()).isEqualTo(teamNameEvenLessGoalsCount);
    }

    @Test
    void teamsWithSameGoalsCountShouldBeSortedByAlphabet() throws IOException {
        //given
        StatisticsAggregator.setTeamsMap(teamsMap);
        StatisticsAggregator.setPlayersMap(playersMap);

        //when
        StatisticsAggregator.generateStatistics();

        //then
        assertThat(StatisticsAggregator.getResultStatistics().get(0).getTeamName()).isEqualTo(aTeamName);
        assertThat(StatisticsAggregator.getResultStatistics().get(1).getTeamName()).isEqualTo(bTeamName);
        assertThat(StatisticsAggregator.getResultStatistics().get(2).getTeamName()).isEqualTo(cTeamName);
    }

    @Test
    void playersShouldBeSortedByGoalsCount() throws IOException {
        //when
        StatisticsAggregator.extractDataFromFile(filename);
        StatisticsAggregator.generateStatistics();

        //then
        for(TeamSummaryDataDto teamSummaryDto : StatisticsAggregator.getResultStatistics()){
            String currentTeamName = teamSummaryDto.getTeamName();
            assertThat(StatisticsAggregator.getPlayersList().stream().filter(p -> p.getTeamName().equals(currentTeamName)).sorted().findFirst().get().getPlayerName()).isEqualTo(StatisticsAggregator.getResultStatistics().stream().filter(t -> t.getTeamName().equals(currentTeamName)).findFirst().get().getTopPlayerSummary().getPlayerName());
        }
    }

    @Test
    void playersWithSameGoalsCountShouldBeSortedByPenaltyCount() throws IOException {
        //given
        StatisticsAggregator.setTeamsMap(teamsMap);
        StatisticsAggregator.setPlayersMap(playersMap);

        //when
        StatisticsAggregator.generateStatistics();

        //then
        assertThat(StatisticsAggregator.getResultStatistics().get(0).getTopPlayerSummary().getPlayerName()).isEqualTo(playerNameLessPenalties);
        assertThat(StatisticsAggregator.getResultStatistics().get(1).getTopPlayerSummary().getPlayerName()).isEqualTo(playerNameLessPenalties);
    }
}
