package com.tennis.atp_matches_stats.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tennis.atp_matches_stats.config.BaseIT;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;


public class RoleResourceTest extends BaseIT {

    @Test
    @Sql("/data/roleData.sql")
    void getAllRoles_success() throws Exception {
        mockMvc.perform(get("/api/roles")
                        .session(authenticatedSession())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.content[0].id").value(((long)1200)));
    }

    @Test
    @Sql("/data/roleData.sql")
    void getAllRoles_filtered() throws Exception {
        mockMvc.perform(get("/api/roles?filter=1201")
                        .session(authenticatedSession())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.content[0].id").value(((long)1201)));
    }

    @Test
    @Sql("/data/roleData.sql")
    void getRole_success() throws Exception {
        mockMvc.perform(get("/api/roles/1200")
                        .session(authenticatedSession())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Duis autem vel."));
    }

    @Test
    void getRole_notFound() throws Exception {
        mockMvc.perform(get("/api/roles/1866")
                        .session(authenticatedSession())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.exception").value("NotFoundException"));
    }

    @Test
    void createRole_success() throws Exception {
        mockMvc.perform(post("/api/roles")
                        .session(authenticatedSession())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(readResource("/requests/roleDTORequest.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        assertEquals(1, roleRepository.count());
    }

    @Test
    void createRole_missingField() throws Exception {
        mockMvc.perform(post("/api/roles")
                        .session(authenticatedSession())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(readResource("/requests/roleDTORequest_missingField.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exception").value("MethodArgumentNotValidException"))
                .andExpect(jsonPath("$.fieldErrors[0].field").value("name"));
    }

    @Test
    @Sql("/data/roleData.sql")
    void updateRole_success() throws Exception {
        mockMvc.perform(put("/api/roles/1200")
                        .session(authenticatedSession())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(readResource("/requests/roleDTORequest.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertEquals("Nam liber tempor.", roleRepository.findById(((long)1200)).get().getName());
        assertEquals(2, roleRepository.count());
    }

    @Test
    @Sql("/data/roleData.sql")
    void deleteRole_success() throws Exception {
        mockMvc.perform(delete("/api/roles/1200")
                        .session(authenticatedSession())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        assertEquals(1, roleRepository.count());
    }

}
