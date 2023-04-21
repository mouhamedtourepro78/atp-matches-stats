package com.tennis.atp_matches_stats.service;

import com.tennis.atp_matches_stats.model.SimplePage;
import com.tennis.atp_matches_stats.model.UserDTO;
import org.springframework.data.domain.Pageable;


public interface UserService {

    SimplePage<UserDTO> findAll(final String filter, final Pageable pageable);

    UserDTO get(final Integer id);

    Integer create(final UserDTO userDTO);

    void update(final Integer id, final UserDTO userDTO);

    void delete(final Integer id);

}
