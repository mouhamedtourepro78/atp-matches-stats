package com.tennis.atp_matches_stats.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Match {

    @Id
    @Column(name = "match_id", nullable = false, updatable = false)
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "tournaments_matchs",
            joinColumns = {
                    @JoinColumn(name = "match_id", referencedColumnName="match_id")},
            inverseJoinColumns = { @JoinColumn(name = "tournament_id"  , referencedColumnName="tournament_id")})
    private Tournament tournament;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "won_matchs",
            joinColumns = {@JoinColumn(name = "match_id", referencedColumnName="match_id")},
            inverseJoinColumns = {@JoinColumn(name = "winner_player_id", referencedColumnName="player_id")})
    private Player winner;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "lost_matchs",
            joinColumns = {@JoinColumn(name = "match_id", referencedColumnName="match_id")},
            inverseJoinColumns = {@JoinColumn(name = "loser_player_id", referencedColumnName="player_id")})
    private Player loser;

}
