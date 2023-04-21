package com.tennis.atp_matches_stats.model;

import com.tennis.atp_matches_stats.domain.Player;
import com.tennis.atp_matches_stats.domain.Tournament;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MatchDTO {

    private Long id;

    private Long matchNumber;

    @Size(max = 255)
    private String score;

    private Integer bestOf;

    @Size(max = 255)
    private String matchRound;

    private Long minutes;

    private Tournament tournament;

    private Player winner;

    private Player loser;

}
