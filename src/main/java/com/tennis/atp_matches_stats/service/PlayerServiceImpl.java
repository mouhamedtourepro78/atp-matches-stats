package com.tennis.atp_matches_stats.service;

import com.tennis.atp_matches_stats.domain.Match;
import com.tennis.atp_matches_stats.domain.Player;
import com.tennis.atp_matches_stats.model.PlayerDTO;
import com.tennis.atp_matches_stats.model.SimplePage;
import com.tennis.atp_matches_stats.repos.MatchRepository;
import com.tennis.atp_matches_stats.repos.PlayerRepository;
import com.tennis.atp_matches_stats.util.NotFoundException;
import com.tennis.atp_matches_stats.util.WebUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final MatchRepository matchRepository;

    public PlayerServiceImpl(final PlayerRepository playerRepository,
            final MatchRepository matchRepository) {
        this.playerRepository = playerRepository;
        this.matchRepository = matchRepository;
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
        playerDTO.setSeed(player.getSeed());
        playerDTO.setEntry(player.getEntry());
        playerDTO.setName(player.getName());
        playerDTO.setHand(player.getHand());
        playerDTO.setHeight(player.getHeight());
        playerDTO.setNationality(player.getNationality());
        playerDTO.setAge(player.getAge());
        return playerDTO;
    }

    private Player mapToEntity(final PlayerDTO playerDTO, final Player player) {
        player.setSeed(playerDTO.getSeed());
        player.setEntry(playerDTO.getEntry());
        player.setName(playerDTO.getName());
        player.setHand(playerDTO.getHand());
        player.setHeight(playerDTO.getHeight());
        player.setNationality(playerDTO.getNationality());
        player.setAge(playerDTO.getAge());
        return player;
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
        return null;
    }

}
