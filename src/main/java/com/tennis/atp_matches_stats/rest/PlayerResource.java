package com.tennis.atp_matches_stats.rest;

import com.tennis.atp_matches_stats.model.PlayerDTO;
import com.tennis.atp_matches_stats.model.SimplePage;
import com.tennis.atp_matches_stats.service.PlayerService;
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
@RequestMapping(value = "/api/players", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlayerResource {

    private final PlayerService playerService;

    public PlayerResource(final PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public ResponseEntity<SimplePage<PlayerDTO>> getAllPlayers(
            @RequestParam(required = false) final String filter,
            @SortDefault(sort = "id") @PageableDefault(size = 20) final Pageable pageable) {
        return ResponseEntity.ok(playerService.findAll(filter, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDTO> getPlayer(@PathVariable final Long id) {
        return ResponseEntity.ok(playerService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createPlayer(@RequestBody @Valid final PlayerDTO playerDTO) {
        return new ResponseEntity<>(playerService.create(playerDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePlayer(@PathVariable final Long id,
            @RequestBody @Valid final PlayerDTO playerDTO) {
        playerService.update(id, playerDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable final Long id) {
        playerService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
