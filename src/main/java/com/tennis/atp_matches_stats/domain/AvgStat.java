package com.tennis.atp_matches_stats.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class AvgStat {

    @Id
    @Column(name= "avg_stat_id", nullable = false, updatable = false)
    private Long id;

    @Column
    private double avgAces;

    @Column
    private double avgDoubleFaults;

    @Column
    private double avgServicePoints;

    @Column
    private double avgFirstServeIn;

    @Column
    private double avgFirstServeWon;

    @Column
    private double avgSecondServeWon;

    @Column
    private double avgServiceGames;

    @Column
    private double avgSavedBreakPoints;

    @Column
    private double avgFacedBreakPoints;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "player_avg_stats",
            joinColumns = {@JoinColumn(name = "avg_stat_id", referencedColumnName="avg_stat_id")},
            inverseJoinColumns = {@JoinColumn(name = "player_id", referencedColumnName="player_id")})
    private Player player;

}
