package com.tennis.atp_matches_stats.model;

import jakarta.validation.constraints.Size;
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

    @Size(max = 255)
    private String drawSize;

    @Size(max = 255)
    private String level;

    @Size(max = 255)
    private String date;

}
