package com.tennis.atp_matches_stats.rest;

import com.tennis.atp_matches_stats.model.AvgStatDTO;
import com.tennis.atp_matches_stats.model.SimplePage;
import com.tennis.atp_matches_stats.service.AvgStatService;
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
@RequestMapping(value = "/api/avgStats", produces = MediaType.APPLICATION_JSON_VALUE)
public class AvgStatResource {

    private final AvgStatService avgStatService;

    public AvgStatResource(final AvgStatService avgStatService) {
        this.avgStatService = avgStatService;
    }

    @GetMapping
    public ResponseEntity<SimplePage<AvgStatDTO>> getAllAvgStats(
            @RequestParam(required = false) final String filter,
            @SortDefault(sort = "id") @PageableDefault(size = 20) final Pageable pageable) {
        return ResponseEntity.ok(avgStatService.findAll(filter, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvgStatDTO> getAvgStat(@PathVariable final Long id) {
        return ResponseEntity.ok(avgStatService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createAvgStat(@RequestBody @Valid final AvgStatDTO avgStatDTO) {
        return new ResponseEntity<>(avgStatService.create(avgStatDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAvgStat(@PathVariable final Long id,
            @RequestBody @Valid final AvgStatDTO avgStatDTO) {
        avgStatService.update(id, avgStatDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvgStat(@PathVariable final Long id) {
        avgStatService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
