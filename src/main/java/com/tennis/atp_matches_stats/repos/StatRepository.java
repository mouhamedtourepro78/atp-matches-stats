package com.tennis.atp_matches_stats.repos;

import com.tennis.atp_matches_stats.domain.Player;
import com.tennis.atp_matches_stats.domain.Stat;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface StatRepository extends JpaRepository<Stat, Long> {

    Page<Stat> findAllById(Long id, Pageable pageable);

    Stat findFirstByPlayer(Player player);

    @Query("select s from Stat s where s.player.id = :playerId")
    Set<Stat> findPlayerStatsByPlayerId(@Param("playerId") Long playerId);

}
