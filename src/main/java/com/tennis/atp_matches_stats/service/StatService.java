package com.tennis.atp_matches_stats.service;

import com.tennis.atp_matches_stats.domain.Stat;
import com.tennis.atp_matches_stats.model.SimplePage;
import com.tennis.atp_matches_stats.model.StatDTO;

import java.util.Set;

import org.springframework.data.domain.Pageable;


public interface StatService {

    SimplePage<StatDTO> findAll(final String filter, final Pageable pageable);

    StatDTO get(final Long id);

    Long create(final StatDTO statDTO);

    void update(final Long id, final StatDTO statDTO);

    void delete(final Long id);

    Set<Stat> findPlayerStatsByPlayerId(Long playerId);
  
}
