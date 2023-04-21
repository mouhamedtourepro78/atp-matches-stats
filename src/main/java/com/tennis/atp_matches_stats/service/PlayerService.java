package com.tennis.atp_matches_stats.service;

import com.tennis.atp_matches_stats.model.PlayerDTO;
import com.tennis.atp_matches_stats.model.SimplePage;
import org.springframework.data.domain.Pageable;


public interface PlayerService {

    SimplePage<PlayerDTO> findAll(final String filter, final Pageable pageable);

    PlayerDTO get(final Long id);

    Long create(final PlayerDTO playerDTO);

    void update(final Long id, final PlayerDTO playerDTO);

    void delete(final Long id);

    boolean avgStatExists(final Long id);

    String getReferencedWarning(final Long id);

}
