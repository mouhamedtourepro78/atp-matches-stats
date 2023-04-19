package com.tennis.atp_matches_stats.repos;

import com.tennis.atp_matches_stats.domain.Role;
import com.tennis.atp_matches_stats.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmailIgnoreCase(String email);

    Page<User> findAllById(Integer id, Pageable pageable);

    boolean existsByEmailIgnoreCase(String email);

    User findFirstByRoles(Role role);

}
