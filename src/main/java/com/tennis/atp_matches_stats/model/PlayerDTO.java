package com.tennis.atp_matches_stats.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PlayerDTO {

    private Long id;

    @Size(max = 255)
    private String seed;

    @Size(max = 255)
    private String entry;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String hand;

    @Size(max = 255)
    private String height;

    @Size(max = 255)
    private String nationality;

    @Size(max = 255)
    private String age;

}
