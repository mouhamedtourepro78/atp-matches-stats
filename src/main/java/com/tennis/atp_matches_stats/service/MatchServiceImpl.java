package com.tennis.atp_matches_stats.service;

import com.tennis.atp_matches_stats.domain.Match;
import com.tennis.atp_matches_stats.domain.Player;
import com.tennis.atp_matches_stats.domain.Tournament;
import com.tennis.atp_matches_stats.model.MatchDTO;
import com.tennis.atp_matches_stats.model.SimplePage;
import com.tennis.atp_matches_stats.repos.MatchRepository;
import com.tennis.atp_matches_stats.repos.PlayerRepository;
import com.tennis.atp_matches_stats.repos.TournamentRepository;
import com.tennis.atp_matches_stats.util.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;
    private final TournamentRepository tournamentRepository;
    private final PlayerRepository playerRepository;

    public MatchServiceImpl(final MatchRepository matchRepository,
            final TournamentRepository tournamentRepository,
            final PlayerRepository playerRepository) {
        this.matchRepository = matchRepository;
        this.tournamentRepository = tournamentRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public SimplePage<MatchDTO> findAll(final String filter, final Pageable pageable) {
        Page<Match> page;
        if (filter != null) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = matchRepository.findAllById(longFilter, pageable);
        } else {
            page = matchRepository.findAll(pageable);
        }
        return new SimplePage<>(page.getContent()
                .stream()
                .map((match) -> mapToDTO(match, new MatchDTO()))
                .toList(),
                page.getTotalElements(), pageable);
    }

    @Override
    public MatchDTO get(final Long id) {
        return matchRepository.findById(id)
                .map(match -> mapToDTO(match, new MatchDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Long create(final MatchDTO matchDTO) {
        final Match match = new Match();
        mapToEntity(matchDTO, match);
        return matchRepository.save(match).getId();
    }

    @Override
    public void update(final Long id, final MatchDTO matchDTO) {
        final Match match = matchRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(matchDTO, match);
        matchRepository.save(match);
    }

    @Override
    public void delete(final Long id) {
        matchRepository.deleteById(id);
    }

    @Override
    public Set<Match> findAllWonMatchsByPlayerId(Long playerId) {
        return matchRepository.findAllWonMatchsByPlayerId(playerId);
    }

    @Override
    public Set<Match> findAllLostMatchsByPlayerId(Long playerId) {
        return matchRepository.findAllLostMatchsByPlayerId(playerId);
    }

    private MatchDTO mapToDTO(final Match match, final MatchDTO matchDTO) {
        matchDTO.setId(match.getId());
        matchDTO.setMatchNumber(match.getMatchNumber());
        matchDTO.setScore(match.getScore());
        matchDTO.setBestOf(match.getBestOf());
        matchDTO.setTour(match.getTour());
        matchDTO.setMinutes(match.getMinutes());
        matchDTO.setWinnerAces(match.getWinnerAces());
        matchDTO.setWinnerDoubleFaults(match.getWinnerDoubleFaults());
        matchDTO.setWinnerServicePoints(match.getWinnerServicePoints());
        matchDTO.setWinnerFirstServeIn(match.getWinnerFirstServeIn());
        matchDTO.setWinnerFirstServeWon(match.getWinnerFirstServeWon());
        matchDTO.setWinnerSecondServiceWon(match.getWinnerSecondServiceWon());
        matchDTO.setWinnerServiceGames(match.getWinnerServiceGames());
        matchDTO.setWinnerSavedBreakPoints(match.getWinnerSavedBreakPoints());
        matchDTO.setWinnerFacedBreakPoints(match.getWinnerFacedBreakPoints());
        matchDTO.setLoserAces(match.getLoserAces());
        matchDTO.setLoserDoubleFaults(match.getLoserDoubleFaults());
        matchDTO.setLoserServicePoints(match.getLoserServicePoints());
        matchDTO.setLoserFirstServeIn(match.getLoserFirstServeIn());
        matchDTO.setLoserFirstServeWon(match.getLoserFirstServeWon());
        matchDTO.setLoserSecondServiceWon(match.getLoserSecondServiceWon());
        matchDTO.setLoserServiceGames(match.getLoserServiceGames());
        matchDTO.setLoserSavedBreakPoints(match.getLoserSavedBreakPoints());
        matchDTO.setLoserFacedBreakPoints(match.getLoserFacedBreakPoints());
        matchDTO.setWinnerRank(match.getWinnerRank());
        matchDTO.setWinnerRankPoints(match.getWinnerRankPoints());
        matchDTO.setLoserRank(match.getLoserRank());
        matchDTO.setLoserRankPoints(match.getLoserRankPoints());
        matchDTO.setTournament(match.getTournament() == null ? null : match.getTournament().getId());
        matchDTO.setWinner(match.getWinner() == null ? null : match.getWinner().getId());
        matchDTO.setLoser(match.getLoser() == null ? null : match.getLoser().getId());
        return matchDTO;
    }

    private Match mapToEntity(final MatchDTO matchDTO, final Match match) {
        match.setMatchNumber(matchDTO.getMatchNumber());
        match.setScore(matchDTO.getScore());
        match.setBestOf(matchDTO.getBestOf());
        match.setTour(matchDTO.getTour());
        match.setMinutes(matchDTO.getMinutes());
        match.setWinnerAces(matchDTO.getWinnerAces());
        match.setWinnerDoubleFaults(matchDTO.getWinnerDoubleFaults());
        match.setWinnerServicePoints(matchDTO.getWinnerServicePoints());
        match.setWinnerFirstServeIn(matchDTO.getWinnerFirstServeIn());
        match.setWinnerFirstServeWon(matchDTO.getWinnerFirstServeWon());
        match.setWinnerSecondServiceWon(matchDTO.getWinnerSecondServiceWon());
        match.setWinnerServiceGames(matchDTO.getWinnerServiceGames());
        match.setWinnerSavedBreakPoints(matchDTO.getWinnerSavedBreakPoints());
        match.setWinnerFacedBreakPoints(matchDTO.getWinnerFacedBreakPoints());
        match.setLoserAces(matchDTO.getLoserAces());
        match.setLoserDoubleFaults(matchDTO.getLoserDoubleFaults());
        match.setLoserServicePoints(matchDTO.getLoserServicePoints());
        match.setLoserFirstServeIn(matchDTO.getLoserFirstServeIn());
        match.setLoserFirstServeWon(matchDTO.getLoserFirstServeWon());
        match.setLoserSecondServiceWon(matchDTO.getLoserSecondServiceWon());
        match.setLoserServiceGames(matchDTO.getLoserServiceGames());
        match.setLoserSavedBreakPoints(matchDTO.getLoserSavedBreakPoints());
        match.setLoserFacedBreakPoints(matchDTO.getLoserFacedBreakPoints());
        match.setWinnerRank(matchDTO.getWinnerRank());
        match.setWinnerRankPoints(matchDTO.getWinnerRankPoints());
        match.setLoserRank(matchDTO.getLoserRank());
        match.setLoserRankPoints(matchDTO.getLoserRankPoints());
        final Tournament tournament = matchDTO.getTournament() == null ? null : tournamentRepository.findById(matchDTO.getTournament())
                .orElseThrow(() -> new NotFoundException("tournament not found"));
        match.setTournament(tournament);
        final Player winner = matchDTO.getWinner() == null ? null : playerRepository.findById(matchDTO.getWinner())
                .orElseThrow(() -> new NotFoundException("winner not found"));
        match.setWinner(winner);
        final Player loser = matchDTO.getLoser() == null ? null : playerRepository.findById(matchDTO.getLoser())
                .orElseThrow(() -> new NotFoundException("loser not found"));
        match.setLoser(loser);
        return match;
    }

}
