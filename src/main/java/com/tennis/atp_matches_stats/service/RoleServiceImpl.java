package com.tennis.atp_matches_stats.service;

import com.tennis.atp_matches_stats.domain.Role;
import com.tennis.atp_matches_stats.domain.User;
import com.tennis.atp_matches_stats.model.RoleDTO;
import com.tennis.atp_matches_stats.model.SimplePage;
import com.tennis.atp_matches_stats.repos.RoleRepository;
import com.tennis.atp_matches_stats.repos.UserRepository;
import com.tennis.atp_matches_stats.util.NotFoundException;
import com.tennis.atp_matches_stats.util.WebUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public RoleServiceImpl(final RoleRepository roleRepository,
            final UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public SimplePage<RoleDTO> findAll(final String filter, final Pageable pageable) {
        Page<Role> page;
        if (filter != null) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = roleRepository.findAllById(longFilter, pageable);
        } else {
            page = roleRepository.findAll(pageable);
        }
        return new SimplePage<>(page.getContent()
                .stream()
                .map((role) -> mapToDTO(role, new RoleDTO()))
                .toList(),
                page.getTotalElements(), pageable);
    }

    @Override
    public RoleDTO get(final Long id) {
        return roleRepository.findById(id)
                .map(role -> mapToDTO(role, new RoleDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Long create(final RoleDTO roleDTO) {
        final Role role = new Role();
        mapToEntity(roleDTO, role);
        return roleRepository.save(role).getId();
    }

    @Override
    public void update(final Long id, final RoleDTO roleDTO) {
        final Role role = roleRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(roleDTO, role);
        roleRepository.save(role);
    }

    @Override
    public void delete(final Long id) {
        roleRepository.deleteById(id);
    }

    private RoleDTO mapToDTO(final Role role, final RoleDTO roleDTO) {
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());
        return roleDTO;
    }

    private Role mapToEntity(final RoleDTO roleDTO, final Role role) {
        role.setName(roleDTO.getName());
        return role;
    }

    @Override
    public String getReferencedWarning(final Long id) {
        final Role role = roleRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final User rolesUser = userRepository.findFirstByRoles(role);
        if (rolesUser != null) {
            return WebUtils.getMessage("role.user.roles.referenced", rolesUser.getId());
        }
        return null;
    }

}
