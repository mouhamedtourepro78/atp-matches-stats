package com.tennis.atp_matches_stats.service;

import com.tennis.atp_matches_stats.domain.Match;
import com.tennis.atp_matches_stats.domain.Tournament;
import com.tennis.atp_matches_stats.model.SimplePage;
import com.tennis.atp_matches_stats.model.TournamentDTO;
import com.tennis.atp_matches_stats.repos.MatchRepository;
import com.tennis.atp_matches_stats.repos.TournamentRepository;
import com.tennis.atp_matches_stats.util.NotFoundException;
import com.tennis.atp_matches_stats.util.WebUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class TournamentServiceImpl implements TournamentService {

    private final TournamentRepository tournamentRepository;
    private final MatchRepository matchRepository;

    public TournamentServiceImpl(final TournamentRepository tournamentRepository,
            final MatchRepository matchRepository) {
        this.tournamentRepository = tournamentRepository;
        this.matchRepository = matchRepository;
    }

    @Override
    public SimplePage<TournamentDTO> findAll(final String filter, final Pageable pageable) {
        Page<Tournament> page;
        if (filter != null) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = tournamentRepository.findAllById(longFilter, pageable);
        } else {
            page = tournamentRepository.findAll(pageable);
        }
        return new SimplePage<>(page.getContent()
                .stream()
                .map((tournament) -> mapToDTO(tournament, new TournamentDTO()))
                .toList(),
                page.getTotalElements(), pageable);
    }

    @Override
    public TournamentDTO get(final Long id) {
        return tournamentRepository.findById(id)
                .map(tournament -> mapToDTO(tournament, new TournamentDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Long create(final TournamentDTO tournamentDTO) {
        final Tournament tournament = new Tournament();
        mapToEntity(tournamentDTO, tournament);
        return tournamentRepository.save(tournament).getId();
    }

    @Override
    public void update(final Long id, final TournamentDTO tournamentDTO) {
        final Tournament tournament = tournamentRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(tournamentDTO, tournament);
        tournamentRepository.save(tournament);
    }

    @Override
    public void delete(final Long id) {
        tournamentRepository.deleteById(id);
    }

    private TournamentDTO mapToDTO(final Tournament tournament, final TournamentDTO tournamentDTO) {
        tournamentDTO.setId(tournament.getId());
        tournamentDTO.setName(tournament.getName());
        tournamentDTO.setSurface(tournament.getSurface());
        tournamentDTO.setDrawSize(tournament.getDrawSize());
        tournamentDTO.setLevel(tournament.getLevel());
        tournamentDTO.setDate(tournament.getDate());
        return tournamentDTO;
    }

    private Tournament mapToEntity(final TournamentDTO tournamentDTO, final Tournament tournament) {
        tournament.setName(tournamentDTO.getName());
        tournament.setSurface(tournamentDTO.getSurface());
        tournament.setDrawSize(tournamentDTO.getDrawSize());
        tournament.setLevel(tournamentDTO.getLevel());
        tournament.setDate(tournamentDTO.getDate());
        return tournament;
    }

    @Override
    public String getReferencedWarning(final Long id) {
        final Tournament tournament = tournamentRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Match tournamentMatch = matchRepository.findFirstByTournament(tournament);
        if (tournamentMatch != null) {
            return WebUtils.getMessage("tournament.match.tournament.referenced", tournamentMatch.getId());
        }
        return null;
    }

}
