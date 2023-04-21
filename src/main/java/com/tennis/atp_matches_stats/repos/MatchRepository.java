package com.tennis.atp_matches_stats.repos;

import com.tennis.atp_matches_stats.domain.Match;
import com.tennis.atp_matches_stats.domain.Player;
import com.tennis.atp_matches_stats.domain.Tournament;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface MatchRepository extends JpaRepository<Match, Long> {

    Page<Match> findAllById(Long id, Pageable pageable);

    Match findFirstByWinner(Player player);

    Match findFirstByLoser(Player player);

    Match findFirstByTournament(Tournament tournament);

     
    @Query("select m from Match m where m.winner.id = :playerId")
    Set<Match> findAllWonMatchsByPlayerId(@Param("playerId") Long playerId);

    @Query("select m from Match m where m.loser.id = :playerId")
    Set<Match> findAllLostMatchsByPlayerId(@Param("playerId") Long playerId);

}
