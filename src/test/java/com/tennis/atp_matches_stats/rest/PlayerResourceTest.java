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


public class PlayerResourceTest extends BaseIT {

    @Test
    @Sql("/data/playerData.sql")
    void getAllPlayers_success() throws Exception {
        mockMvc.perform(get("/api/players")
                        .session(authenticatedSession())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.content[0].id").value(((long)1100)));
    }

    @Test
    @Sql("/data/playerData.sql")
    void getAllPlayers_filtered() throws Exception {
        mockMvc.perform(get("/api/players?filter=1101")
                        .session(authenticatedSession())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.content[0].id").value(((long)1101)));
    }

    @Test
    @Sql("/data/playerData.sql")
    void getPlayer_success() throws Exception {
        mockMvc.perform(get("/api/players/1100")
                        .session(authenticatedSession())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.seed").value("Duis autem vel."));
    }

    @Test
    void getPlayer_notFound() throws Exception {
        mockMvc.perform(get("/api/players/1766")
                        .session(authenticatedSession())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.exception").value("NotFoundException"));
    }

    @Test
    void createPlayer_success() throws Exception {
        mockMvc.perform(post("/api/players")
                        .session(authenticatedSession())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(readResource("/requests/playerDTORequest.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        assertEquals(1, playerRepository.count());
    }

    @Test
    @Sql("/data/playerData.sql")
    void updatePlayer_success() throws Exception {
        mockMvc.perform(put("/api/players/1100")
                        .session(authenticatedSession())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(readResource("/requests/playerDTORequest.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertEquals("Nam liber tempor.", playerRepository.findById(((long)1100)).get().getSeed());
        assertEquals(2, playerRepository.count());
    }

    @Test
    @Sql("/data/playerData.sql")
    void deletePlayer_success() throws Exception {
        mockMvc.perform(delete("/api/players/1100")
                        .session(authenticatedSession())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        assertEquals(1, playerRepository.count());
    }

}
