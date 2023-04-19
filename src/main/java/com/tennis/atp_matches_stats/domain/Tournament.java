package com.tennis.atp_matches_stats.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Tournament {

    @Id
    @Column(name = "tournament_id", nullable = false, updatable = false)
    private Long id;

    @Column
    private String name;

    @Column
    private String surface;

    @Column
    private String drawSize;

    @Column
    private String level;

    @Column
    private String date;

    @OneToMany(mappedBy = "tournament")
    private Set<Match> tournamentsMatchs;

}
