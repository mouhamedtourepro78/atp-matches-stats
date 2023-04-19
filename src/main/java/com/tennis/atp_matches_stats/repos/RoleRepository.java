package com.tennis.atp_matches_stats.repos;

import com.tennis.atp_matches_stats.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Long> {

    Page<Role> findAllById(Long id, Pageable pageable);

}
