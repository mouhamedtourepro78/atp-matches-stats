package com.tennis.atp_matches_stats;

import com.tennis.atp_matches_stats.service.DataSavingService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class AtpMatchesStatsApplication implements ApplicationRunner {

    DataSavingService dataSavingService;
    String fileName = "src/main/resources/atp_matches_2023.csv";

    public AtpMatchesStatsApplication(DataSavingService dataSavingService) {
        this.dataSavingService = dataSavingService;
    }


    public static void main(final String[] args) {  
        SpringApplication.run(AtpMatchesStatsApplication.class, args);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        dataSavingService.parseCsvToMatchs(fileName);
    }

}
