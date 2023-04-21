package com.tennis.atp_matches_stats.model;

import com.tennis.atp_matches_stats.domain.Player;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class StatDTO {

    private Long id;

    private Integer seed;

    @Size(max = 255)
    private String entry;

    private Long aces;

    private Long doubleFaults;

    private Long servicePoints;

    private Long firstServeIn;

    private Long firstServeWon;

    private Long secondServeWon;

    private Long serviceGames;

    private Long savedBreakPoints;

    private Long facedBreakPoints;

    private Integer rank;

    private Long rankPoints;

    private Player player;

}
