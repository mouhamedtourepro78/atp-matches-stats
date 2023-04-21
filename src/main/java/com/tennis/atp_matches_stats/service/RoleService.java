package com.tennis.atp_matches_stats.service;

import com.tennis.atp_matches_stats.model.RoleDTO;
import com.tennis.atp_matches_stats.model.SimplePage;
import org.springframework.data.domain.Pageable;


public interface RoleService {

    SimplePage<RoleDTO> findAll(final String filter, final Pageable pageable);

    RoleDTO get(final Long id);

    Long create(final RoleDTO roleDTO);

    void update(final Long id, final RoleDTO roleDTO);

    void delete(final Long id);

    String getReferencedWarning(final Long id);

}
