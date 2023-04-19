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


public class TournamentResourceTest extends BaseIT {

    @Test
    @Sql("/data/tournamentData.sql")
    void getAllTournaments_success() throws Exception {
        mockMvc.perform(get("/api/tournaments")
                        .session(authenticatedSession())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.content[0].id").value(((long)1300)));
    }

    @Test
    @Sql("/data/tournamentData.sql")
    void getAllTournaments_filtered() throws Exception {
        mockMvc.perform(get("/api/tournaments?filter=1301")
                        .session(authenticatedSession())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.content[0].id").value(((long)1301)));
    }

    @Test
    @Sql("/data/tournamentData.sql")
    void getTournament_success() throws Exception {
        mockMvc.perform(get("/api/tournaments/1300")
                        .session(authenticatedSession())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Duis autem vel."));
    }

    @Test
    void getTournament_notFound() throws Exception {
        mockMvc.perform(get("/api/tournaments/1966")
                        .session(authenticatedSession())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.exception").value("NotFoundException"));
    }

    @Test
    void createTournament_success() throws Exception {
        mockMvc.perform(post("/api/tournaments")
                        .session(authenticatedSession())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(readResource("/requests/tournamentDTORequest.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        assertEquals(1, tournamentRepository.count());
    }

    @Test
    @Sql("/data/tournamentData.sql")
    void updateTournament_success() throws Exception {
        mockMvc.perform(put("/api/tournaments/1300")
                        .session(authenticatedSession())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(readResource("/requests/tournamentDTORequest.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertEquals("Nam liber tempor.", tournamentRepository.findById(((long)1300)).get().getName());
        assertEquals(2, tournamentRepository.count());
    }

    @Test
    @Sql("/data/tournamentData.sql")
    void deleteTournament_success() throws Exception {
        mockMvc.perform(delete("/api/tournaments/1300")
                        .session(authenticatedSession())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        assertEquals(1, tournamentRepository.count());
    }

}
