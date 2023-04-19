package com.tennis.atp_matches_stats.service;

import com.tennis.atp_matches_stats.domain.Match;
import com.tennis.atp_matches_stats.model.MatchDTO;
import com.tennis.atp_matches_stats.model.SimplePage;
import org.springframework.data.domain.Pageable;

import java.util.Set;


public interface MatchService {

    SimplePage<MatchDTO> findAll(final String filter, final Pageable pageable);

    MatchDTO get(final Long id);

    Long create(final MatchDTO matchDTO);

    void update(final Long id, final MatchDTO matchDTO);

    void delete(final Long id);
    Set<Match> findAllWonMatchsByPlayerId(Long playerId);

    Set<Match> findAllLostMatchsByPlayerId(Long playerId);

}
