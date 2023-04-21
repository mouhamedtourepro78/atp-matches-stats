package com.tennis.atp_matches_stats.service;

import com.tennis.atp_matches_stats.model.SimplePage;
import com.tennis.atp_matches_stats.model.TournamentDTO;
import org.springframework.data.domain.Pageable;


public interface TournamentService {

    SimplePage<TournamentDTO> findAll(final String filter, final Pageable pageable);

    TournamentDTO get(final Long id);

    Long create(final TournamentDTO tournamentDTO);

    void update(final Long id, final TournamentDTO tournamentDTO);

    void delete(final Long id);

    String getReferencedWarning(final Long id);

}
