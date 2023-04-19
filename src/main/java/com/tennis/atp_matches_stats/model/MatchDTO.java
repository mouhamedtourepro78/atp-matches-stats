package com.tennis.atp_matches_stats.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MatchDTO {

    private Long id;

    @Size(max = 255)
    private String matchNumber;

    @Size(max = 255)
    private String score;

    @Size(max = 255)
    private String bestOf;

    @Size(max = 255)
    private String tour;

    @Size(max = 255)
    private String minutes;

    @Size(max = 255)
    private String winnerAces;

    @Size(max = 255)
    private String winnerDoubleFaults;

    @Size(max = 255)
    private String winnerServicePoints;

    @Size(max = 255)
    private String winnerFirstServeIn;

    @Size(max = 255)
    private String winnerFirstServeWon;

    @Size(max = 255)
    private String winnerSecondServiceWon;

    @Size(max = 255)
    private String winnerServiceGames;

    @Size(max = 255)
    private String winnerSavedBreakPoints;

    @Size(max = 255)
    private String winnerFacedBreakPoints;

    @Size(max = 255)
    private String loserAces;

    @Size(max = 255)
    private String loserDoubleFaults;

    @Size(max = 255)
    private String loserServicePoints;

    @Size(max = 255)
    private String loserFirstServeIn;

    @Size(max = 255)
    private String loserFirstServeWon;

    @Size(max = 255)
    private String loserSecondServiceWon;

    @Size(max = 255)
    private String loserServiceGames;

    @Size(max = 255)
    private String loserSavedBreakPoints;

    @Size(max = 255)
    private String loserFacedBreakPoints;

    @Size(max = 255)
    private String winnerRank;

    @Size(max = 255)
    private String winnerRankPoints;

    @Size(max = 255)
    private String loserRank;

    @Size(max = 255)
    private String loserRankPoints;

    private Long tournament;

    private Long winner;

    private Long loser;

}
