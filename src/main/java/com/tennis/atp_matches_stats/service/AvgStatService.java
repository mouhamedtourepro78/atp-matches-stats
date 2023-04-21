package com.tennis.atp_matches_stats.service;

import com.tennis.atp_matches_stats.domain.AvgStat;
import com.tennis.atp_matches_stats.domain.Player;
import com.tennis.atp_matches_stats.model.AvgStatDTO;
import com.tennis.atp_matches_stats.model.SimplePage;
import com.tennis.atp_matches_stats.repos.PlayerRepository;

import java.util.List;

import org.springframework.data.domain.Pageable;


public interface AvgStatService {

    SimplePage<AvgStatDTO> findAll(final String filter, final Pageable pageable);

    AvgStatDTO get(final Long id);

    Long create(final AvgStatDTO avgStatDTO);

    void update(final Long id, final AvgStatDTO avgStatDTO);

    void delete(final Long id);

    String getReferencedWarning(final Long id);

    AvgStat computeAvgStatsByPlayer(Player player);
}
