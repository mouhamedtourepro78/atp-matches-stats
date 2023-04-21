package com.tennis.atp_matches_stats.service;

import com.tennis.atp_matches_stats.domain.AvgStat;
import com.tennis.atp_matches_stats.domain.Match;
import com.tennis.atp_matches_stats.domain.Player;
import com.tennis.atp_matches_stats.domain.Stat;
import com.tennis.atp_matches_stats.model.PlayerDTO;
import com.tennis.atp_matches_stats.model.SimplePage;
import com.tennis.atp_matches_stats.repos.AvgStatRepository;
import com.tennis.atp_matches_stats.repos.MatchRepository;
import com.tennis.atp_matches_stats.repos.PlayerRepository;
import com.tennis.atp_matches_stats.repos.StatRepository;
import com.tennis.atp_matches_stats.util.NotFoundException;
import com.tennis.atp_matches_stats.util.WebUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final AvgStatRepository avgStatRepository;
    private final MatchRepository matchRepository;
    private final StatRepository statRepository;

    public PlayerServiceImpl(final PlayerRepository playerRepository,
            final AvgStatRepository avgStatRepository, final MatchRepository matchRepository,
            final StatRepository statRepository) {
        this.playerRepository = playerRepository;
        this.avgStatRepository = avgStatRepository;
        this.matchRepository = matchRepository;
        this.statRepository = statRepository;
    }

    @Override
    public SimplePage<PlayerDTO> findAll(final String filter, final Pageable pageable) {
        Page<Player> page;
        if (filter != null) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = playerRepository.findAllById(longFilter, pageable);
        } else {
            page = playerRepository.findAll(pageable);
        }
        return new SimplePage<>(page.getContent()
                .stream()
                .map((player) -> mapToDTO(player, new PlayerDTO()))
                .toList(),
                page.getTotalElements(), pageable);
    }

    @Override
    public PlayerDTO get(final Long id) {
        return playerRepository.findById(id)
                .map(player -> mapToDTO(player, new PlayerDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Long create(final PlayerDTO playerDTO) {
        final Player player = new Player();
        mapToEntity(playerDTO, player);
        return playerRepository.save(player).getId();
    }

    @Override
    public void update(final Long id, final PlayerDTO playerDTO) {
        final Player player = playerRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(playerDTO, player);
        playerRepository.save(player);
    }

    @Override
    public void delete(final Long id) {
        playerRepository.deleteById(id);
    }

    private PlayerDTO mapToDTO(final Player player, final PlayerDTO playerDTO) {
        playerDTO.setId(player.getId());
        playerDTO.setName(player.getName());
        playerDTO.setHand(player.getHand());
        playerDTO.setHeight(player.getHeight());
        playerDTO.setNationality(player.getNationality());
        playerDTO.setAge(player.getAge());
        playerDTO.setAvgStat(player.getAvgStat() == null ? null : player.getAvgStat().getId());
        return playerDTO;
    }

    private Player mapToEntity(final PlayerDTO playerDTO, final Player player) {
        player.setName(playerDTO.getName());
        player.setHand(playerDTO.getHand());
        player.setHeight(playerDTO.getHeight());
        player.setNationality(playerDTO.getNationality());
        player.setAge(playerDTO.getAge());
        final AvgStat avgStat = playerDTO.getAvgStat() == null ? null : avgStatRepository.findById(playerDTO.getAvgStat())
                .orElseThrow(() -> new NotFoundException("avgStat not found"));
        player.setAvgStat(avgStat);
        return player;
    }

    @Override
    public boolean avgStatExists(final Long id) {
        return playerRepository.existsByAvgStatId(id);
    }

    @Override
    public String getReferencedWarning(final Long id) {
        final Player player = playerRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Match winnerMatch = matchRepository.findFirstByWinner(player);
        if (winnerMatch != null) {
            return WebUtils.getMessage("player.match.winner.referenced", winnerMatch.getId());
        }
        final Match loserMatch = matchRepository.findFirstByLoser(player);
        if (loserMatch != null) {
            return WebUtils.getMessage("player.match.loser.referenced", loserMatch.getId());
        }
        final Stat playerStat = statRepository.findFirstByPlayer(player);
        if (playerStat != null) {
            return WebUtils.getMessage("player.stat.player.referenced", playerStat.getId());
        }
        return null;
    }

}
