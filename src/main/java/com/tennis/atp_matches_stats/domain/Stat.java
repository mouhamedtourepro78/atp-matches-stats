package com.tennis.atp_matches_stats.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Stat {
    @Id
    @Column(name = "stat_id", nullable = false, updatable = false)
    private Long id;

    @Column
    private Integer seed;

    @Column
    private String entry;

    @Column
    private Long aces;

    @Column
    private Long doubleFaults;

    @Column
    private Long servicePoints;

    @Column
    private Long firstServeIn;

    @Column
    private Long firstServeWon;

    @Column
    private Long secondServeWon;

    @Column
    private Long serviceGames;

    @Column
    private Long savedBreakPoints;

    @Column
    private Long facedBreakPoints;

    @Column
    private Integer rank;

    @Column
    private Long rankPoints;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "players_stats",
            joinColumns = {@JoinColumn(name = "stats_id", referencedColumnName="stat_id")},
            inverseJoinColumns = {@JoinColumn(name = "player_id", referencedColumnName="player_id")})
    private Player player;

}
