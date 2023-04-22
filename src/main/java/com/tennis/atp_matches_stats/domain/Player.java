package com.tennis.atp_matches_stats.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Player {
    @Id
    @Column(name = "player_id", nullable = false, updatable = false)
    private Long id;

    @Column
    private String name;

    @Column
    private String hand;

    @Column
    private Long height;

    @Column
    private String nationality;

    @Column
    private Double age;

    @OneToMany(mappedBy = "winner")
    private Set<Match> wonMatchs;

    @OneToMany(mappedBy = "loser")
    private Set<Match> lostMatchs;

    @OneToMany(mappedBy = "player")
    private Set<Stat> stats;
    
    @OneToOne(mappedBy = "player")
    private AvgStat avgStat;
}
