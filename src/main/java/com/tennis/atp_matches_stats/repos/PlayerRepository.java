package com.tennis.atp_matches_stats.repos;

import com.tennis.atp_matches_stats.domain.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PlayerRepository extends JpaRepository<Player, Long> {

    Page<Player> findAllById(Long id, Pageable pageable);

}
