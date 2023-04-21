package com.tennis.atp_matches_stats.repos;

import com.tennis.atp_matches_stats.domain.AvgStat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AvgStatRepository extends JpaRepository<AvgStat, Long> {

    Page<AvgStat> findAllById(Long id, Pageable pageable);

}
