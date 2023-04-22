package com.tennis.atp_matches_stats.service;

import com.tennis.atp_matches_stats.domain.Player;
import com.tennis.atp_matches_stats.domain.Stat;
import com.tennis.atp_matches_stats.model.SimplePage;
import com.tennis.atp_matches_stats.model.StatDTO;
import com.tennis.atp_matches_stats.repos.PlayerRepository;
import com.tennis.atp_matches_stats.repos.StatRepository;
import com.tennis.atp_matches_stats.util.NotFoundException;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class StatServiceImpl implements StatService {

    private final StatRepository statRepository;
    private final PlayerRepository playerRepository;

    public StatServiceImpl(final StatRepository statRepository,
            final PlayerRepository playerRepository) {
        this.statRepository = statRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public SimplePage<StatDTO> findAll(final String filter, final Pageable pageable) {
        Page<Stat> page;
        if (filter != null) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = statRepository.findAllById(longFilter, pageable);
        } else {
            page = statRepository.findAll(pageable);
        }
        return new SimplePage<>(page.getContent()
                .stream()
                .map((stat) -> mapToDTO(stat, new StatDTO()))
                .toList(),
                page.getTotalElements(), pageable);
    }

    @Override
    public StatDTO get(final Long id) {
        return statRepository.findById(id)
                .map(stat -> mapToDTO(stat, new StatDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Long create(final StatDTO statDTO) {
        final Stat stat = new Stat();
        mapToEntity(statDTO, stat);
        return statRepository.save(stat).getId();
    }

    @Override
    public void update(final Long id, final StatDTO statDTO) {
        final Stat stat = statRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(statDTO, stat);
        statRepository.save(stat);
    }

    @Override
    public void delete(final Long id) {
        statRepository.deleteById(id);
    }

    private StatDTO mapToDTO(final Stat stat, final StatDTO statDTO) {
        statDTO.setId(stat.getId());
        statDTO.setSeed(stat.getSeed());
        statDTO.setEntry(stat.getEntry());
        statDTO.setAces(stat.getAces());
        statDTO.setDoubleFaults(stat.getDoubleFaults());
        statDTO.setServicePoints(stat.getServicePoints());
        statDTO.setFirstServeIn(stat.getFirstServeIn());
        statDTO.setFirstServeWon(stat.getFirstServeWon());
        statDTO.setSecondServeWon(stat.getSecondServeWon());
        statDTO.setServiceGames(stat.getServiceGames());
        statDTO.setSavedBreakPoints(stat.getSavedBreakPoints());
        statDTO.setFacedBreakPoints(stat.getFacedBreakPoints());
        statDTO.setRank(stat.getRank());
        statDTO.setRankPoints(stat.getRankPoints());
        statDTO.setPlayer(stat.getPlayer());
        return statDTO;
    }

    private Stat mapToEntity(final StatDTO statDTO, final Stat stat) {
        stat.setSeed(statDTO.getSeed());
        stat.setEntry(statDTO.getEntry());
        stat.setAces(statDTO.getAces());
        stat.setDoubleFaults(statDTO.getDoubleFaults());
        stat.setServicePoints(statDTO.getServicePoints());
        stat.setFirstServeIn(statDTO.getFirstServeIn());
        stat.setFirstServeWon(statDTO.getFirstServeWon());
        stat.setSecondServeWon(statDTO.getSecondServeWon());
        stat.setServiceGames(statDTO.getServiceGames());
        stat.setSavedBreakPoints(statDTO.getSavedBreakPoints());
        stat.setFacedBreakPoints(statDTO.getFacedBreakPoints());
        stat.setRank(statDTO.getRank());
        stat.setRankPoints(statDTO.getRankPoints());
        final Player player = statDTO.getPlayer() == null ? null : playerRepository.findById(statDTO.getPlayer().getId())
                .orElseThrow(() -> new NotFoundException("player not found"));
        stat.setPlayer(player);
        return stat;
    }

    @Override
    public Set<Stat> findPlayerStatsByPlayerId(Long playerId) {
        return statRepository.findPlayerStatsByPlayerId(playerId);
    }
}
