package org.example;

import java.io.IOException;

public class App
{
    public static void main( String[] args ) throws IOException {

        StatisticsAggregator.extractDataFromFile("goalscorers.csv");
        StatisticsAggregator.generateStatistics();
    }
}
