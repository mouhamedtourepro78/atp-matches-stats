package com.tennis.atp_matches_stats.model;

import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TournamentDTO {

    private Long id;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String surface;

    private Integer drawSize;

    @Size(max = 255)
    private String level;

    private LocalDate date;

}
