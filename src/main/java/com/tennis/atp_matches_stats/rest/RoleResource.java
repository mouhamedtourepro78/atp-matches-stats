package com.tennis.atp_matches_stats.rest;

import com.tennis.atp_matches_stats.model.RoleDTO;
import com.tennis.atp_matches_stats.model.SimplePage;
import com.tennis.atp_matches_stats.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/roles", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoleResource {

    private final RoleService roleService;

    public RoleResource(final RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<SimplePage<RoleDTO>> getAllRoles(
            @RequestParam(required = false) final String filter,
            @SortDefault(sort = "id") @PageableDefault(size = 20) final Pageable pageable) {
        return ResponseEntity.ok(roleService.findAll(filter, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRole(@PathVariable final Long id) {
        return ResponseEntity.ok(roleService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createRole(@RequestBody @Valid final RoleDTO roleDTO) {
        return new ResponseEntity<>(roleService.create(roleDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRole(@PathVariable final Long id,
            @RequestBody @Valid final RoleDTO roleDTO) {
        roleService.update(id, roleDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable final Long id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
