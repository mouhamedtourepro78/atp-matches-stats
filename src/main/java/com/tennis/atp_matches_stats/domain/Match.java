package com.tennis.atp_matches_stats.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Match {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;

    @Column
    private String matchNumber;

    @Column
    private String score;

    @Column
    private String bestOf;

    @Column
    private String tour;

    @Column
    private String minutes;

    @Column
    private String winnerAces;

    @Column
    private String winnerDoubleFaults;

    @Column
    private String winnerServicePoints;

    @Column
    private String winnerFirstServeIn;

    @Column
    private String winnerFirstServeWon;

    @Column
    private String winnerSecondServiceWon;

    @Column
    private String winnerServiceGames;

    @Column
    private String winnerSavedBreakPoints;

    @Column
    private String winnerFacedBreakPoints;

    @Column
    private String loserAces;

    @Column
    private String loserDoubleFaults;

    @Column
    private String loserServicePoints;

    @Column
    private String loserFirstServeIn;

    @Column
    private String loserFirstServeWon;

    @Column
    private String loserSecondServiceWon;

    @Column
    private String loserServiceGames;

    @Column
    private String loserSavedBreakPoints;

    @Column
    private String loserFacedBreakPoints;

    @Column
    private String winnerRank;

    @Column
    private String winnerRankPoints;

    @Column
    private String loserRank;

    @Column
    private String loserRankPoints;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_id")
    private Player winner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loser_id")
    private Player loser;

}
