package com.tennis.atp_matches_stats.repos;

import com.tennis.atp_matches_stats.domain.Tournament;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TournamentRepository extends JpaRepository<Tournament, Long> {

    Page<Tournament> findAllById(Long id, Pageable pageable);

}
