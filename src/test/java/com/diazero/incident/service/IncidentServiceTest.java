package com.diazero.incident.service;


import com.diazero.incident.model.Incident;
import com.diazero.incident.repository.IncidentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class IncidentServiceTest {

    @Autowired
    private IncidentService service;

    @Autowired
    private IncidentRepository repository;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    void shouldSaveIncident() {
        Incident incident = new Incident();
        incident.setName("Create");
        incident.setDescription("New incident");
        incident.setCreatedAt(LocalDateTime.now());

        Incident saved = service.save(incident);
        assertThat(saved.getIdIncident()).isNotNull();
    }

    @Test
    void shouldFindById() {
        Incident incident = new Incident();
        incident.setName("FindMe");
        incident.setDescription("To find");
        incident.setCreatedAt(LocalDateTime.now());

        Incident saved = repository.save(incident);
        Optional<Incident> found = service.findById(saved.getIdIncident());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("FindMe");
    }

    @Test
    void shouldDeleteById() {
        Incident incident = new Incident();
        incident.setName("DeleteMe");
        incident.setCreatedAt(LocalDateTime.now());
        Incident saved = repository.save(incident);

        service.deleteById(saved.getIdIncident());
        assertThat(repository.findById(saved.getIdIncident())).isEmpty();
    }
}

