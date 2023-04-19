package com.tennis.atp_matches_stats.rest;

import com.tennis.atp_matches_stats.model.SimplePage;
import com.tennis.atp_matches_stats.model.TournamentDTO;
import com.tennis.atp_matches_stats.service.TournamentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/tournaments", produces = MediaType.APPLICATION_JSON_VALUE)
public class TournamentResource {

    private final TournamentService tournamentService;

    public TournamentResource(final TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @GetMapping
    public ResponseEntity<SimplePage<TournamentDTO>> getAllTournaments(
            @RequestParam(required = false) final String filter,
            @SortDefault(sort = "id") @PageableDefault(size = 20) final Pageable pageable) {
        return ResponseEntity.ok(tournamentService.findAll(filter, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TournamentDTO> getTournament(@PathVariable final Long id) {
        return ResponseEntity.ok(tournamentService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createTournament(
            @RequestBody @Valid final TournamentDTO tournamentDTO) {
        return new ResponseEntity<>(tournamentService.create(tournamentDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTournament(@PathVariable final Long id,
            @RequestBody @Valid final TournamentDTO tournamentDTO) {
        tournamentService.update(id, tournamentDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTournament(@PathVariable final Long id) {
        tournamentService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
