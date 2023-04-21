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
public class Match {

    @Id
    @Column(name = "match_id", nullable = false, updatable = false)
    private Long id;

    @Column
    private Long matchNumber;

    @Column
    private String score;

    @Column
    private Integer bestOf;

    @Column
    private String matchRound;

    @Column
    private Long minutes;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "tournaments_matchs",
            joinColumns = {
                    @JoinColumn(name = "match_id", referencedColumnName="match_id")},
            inverseJoinColumns = { @JoinColumn(name = "tournament_id"  , referencedColumnName="tournament_id")})
    private Tournament tournament;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "won_matchs",
            joinColumns = {@JoinColumn(name = "match_id", referencedColumnName="match_id")},
            inverseJoinColumns = {@JoinColumn(name = "winner_id", referencedColumnName="player_id")})
    private Player winner;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "lost_matchs",
            joinColumns = {@JoinColumn(name = "match_id", referencedColumnName="match_id")},
            inverseJoinColumns = {@JoinColumn(name = "loser_id", referencedColumnName="player_id")})
    private Player loser;

}
