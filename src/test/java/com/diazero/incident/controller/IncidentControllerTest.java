package com.diazero.incident.controller;

import com.diazero.incident.model.Incident;
import com.diazero.incident.repository.IncidentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class IncidentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IncidentRepository repository;

    @Test
    void shouldCreateIncident() throws Exception {
        String json = """
                {
                  "name": "Incident A",
                  "description": "Test incident",
                  "createdAt": "2025-04-29T12:00:00"
                }
                """;

        mockMvc.perform(post("/api/incidents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Incident A"));
    }

    @Test
    void shouldReturnLast20Incidents() throws Exception {
        for (int i = 0; i < 22; i++) {
            Incident inc = new Incident();
            inc.setName("Bulk " + i);
            inc.setCreatedAt(LocalDateTime.now().minusDays(i));
            repository.save(inc);
        }

        mockMvc.perform(get("/api/incidents/latest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(20)))
                .andExpect(jsonPath("$[0].name", containsString("Bulk")));
    }
}

