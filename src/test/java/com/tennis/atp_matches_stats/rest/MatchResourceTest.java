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


public class MatchResourceTest extends BaseIT {

    @Test
    @Sql("/data/matchData.sql")
    void getAllMatchs_success() throws Exception {
        mockMvc.perform(get("/api/matchs")
                        .session(authenticatedSession())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.content[0].id").value(((long)1000)));
    }

    @Test
    @Sql("/data/matchData.sql")
    void getAllMatchs_filtered() throws Exception {
        mockMvc.perform(get("/api/matchs?filter=1001")
                        .session(authenticatedSession())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.content[0].id").value(((long)1001)));
    }

    @Test
    @Sql("/data/matchData.sql")
    void getMatch_success() throws Exception {
        mockMvc.perform(get("/api/matchs/1000")
                        .session(authenticatedSession())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.matchNumber").value("Nam liber tempor."));
    }

    @Test
    void getMatch_notFound() throws Exception {
        mockMvc.perform(get("/api/matchs/1666")
                        .session(authenticatedSession())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.exception").value("NotFoundException"));
    }

    @Test
    void createMatch_success() throws Exception {
        mockMvc.perform(post("/api/matchs")
                        .session(authenticatedSession())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(readResource("/requests/matchDTORequest.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        assertEquals(1, matchRepository.count());
    }

    @Test
    @Sql("/data/matchData.sql")
    void updateMatch_success() throws Exception {
        mockMvc.perform(put("/api/matchs/1000")
                        .session(authenticatedSession())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(readResource("/requests/matchDTORequest.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertEquals("Lorem ipsum dolor.", matchRepository.findById(((long)1000)).get().getMatchNumber());
        assertEquals(2, matchRepository.count());
    }

    @Test
    @Sql("/data/matchData.sql")
    void deleteMatch_success() throws Exception {
        mockMvc.perform(delete("/api/matchs/1000")
                        .session(authenticatedSession())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        assertEquals(1, matchRepository.count());
    }

}
