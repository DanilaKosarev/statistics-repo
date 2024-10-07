package org.example;

import org.example.models.TeamInfo;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class AppTest {

    String filename = "goalscorers.csv";

    @Test
    void countOfGoalsShouldMatchCountOfLinesInTheFile() throws IOException {
        //given
        BufferedReader reader = new BufferedReader(new FileReader("file\\" + filename));
        int lines = 0;
        while (reader.readLine() != null) lines++;
        reader.close();

        //when
        StatisticsAggregator.extractDataFromFile(filename);
        StatisticsAggregator.generateStatistics();

        //then
        assertThat(StatisticsAggregator.getTeamsList().stream().mapToInt(TeamInfo::getGoalsScored).sum()).isEqualTo(lines - 1);
    }

}
