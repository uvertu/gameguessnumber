package com.game.gameguessnumber.backend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GameApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void startGameShouldReturnGameId() throws Exception {
        String body = mockMvc.perform(post("/api/v1/games")
                        .header("Accept-Language", "en")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode json = objectMapper.readTree(body);
        assertNotNull(json.get("gameId"));
        assertTrue(json.get("gameId").asText().length() > 0);
        assertEquals(1, json.get("min").asInt());
        assertEquals(100, json.get("max").asInt());
    }

    @Test
    void leaderboardShouldBeOk() throws Exception {
        mockMvc.perform(get("/api/v1/leaderboard")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
