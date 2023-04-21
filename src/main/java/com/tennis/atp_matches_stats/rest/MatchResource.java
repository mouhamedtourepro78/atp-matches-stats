package com.tennis.atp_matches_stats.rest;

import com.tennis.atp_matches_stats.model.MatchDTO;
import com.tennis.atp_matches_stats.model.SimplePage;
import com.tennis.atp_matches_stats.service.MatchService;
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
@RequestMapping(value = "/api/matchs", produces = MediaType.APPLICATION_JSON_VALUE)
public class MatchResource {

    private final MatchService matchService;

    public MatchResource(final MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping
    public ResponseEntity<SimplePage<MatchDTO>> getAllMatchs(
            @RequestParam(required = false) final String filter,
            @SortDefault(sort = "id") @PageableDefault(size = 20) final Pageable pageable) {
        return ResponseEntity.ok(matchService.findAll(filter, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchDTO> getMatch(@PathVariable final Long id) {
        return ResponseEntity.ok(matchService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createMatch(@RequestBody @Valid final MatchDTO matchDTO) {
        return new ResponseEntity<>(matchService.create(matchDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMatch(@PathVariable final Long id,
            @RequestBody @Valid final MatchDTO matchDTO) {
        matchService.update(id, matchDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable final Long id) {
        matchService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
