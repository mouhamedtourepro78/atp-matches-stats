package com.tennis.atp_matches_stats.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.time.LocalDate;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Embeddable
public class Tournament {
    @Id
    @Column(name = "tournament_id", nullable = false, updatable = false)
    private Long id;

    @Column
    private String name;

    @Column
    private String surface;

    @Column
    private Integer drawSize;

    @Column
    private String level;

    @Column
    private LocalDate date;

    @OneToMany(mappedBy = "tournament")
    private Set<Match> matchs;
}
