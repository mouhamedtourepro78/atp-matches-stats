package com.tennis.atp_matches_stats.model;

import com.tennis.atp_matches_stats.domain.Player;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AvgStatDTO {

    private Long id;
    private double avgAces;
    private double avgDoubleFaults;
    private double avgServicePoints;
    private double avgFirstServeIn;
    private double avgFirstServeWon;
    private double avgSecondServeWon;
    private double avgServiceGames;
    private double avgSavedBreakPoints;
    private double avgFacedBreakPoints;
    private Player player;
}
