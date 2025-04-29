package com.diazero.incident.repository;

import com.diazero.incident.model.Incident;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class IncidentRepositoryTest {

    @Autowired
    private IncidentRepository repository;

    @Test
    void shouldSaveAndFindIncident() {
        Incident incident = new Incident();
        incident.setName("Test Incident");
        incident.setDescription("Description");
        incident.setCreatedAt(LocalDateTime.now());

        repository.save(incident);
        List<Incident> all = repository.findAll();

        assertThat(all).hasSize(1);
        assertThat(all.get(0).getName()).isEqualTo("Test Incident");
    }

    @Test
    void shouldReturnLast20Incidents() {
        for (int i = 0; i < 25; i++) {
            Incident incident = new Incident();
            incident.setName("Incident " + i);
            incident.setDescription("Desc " + i);
            incident.setCreatedAt(LocalDateTime.now().minusDays(i));
            repository.save(incident);
        }

        List<Incident> latest = repository.findTop20ByOrderByCreatedAtDesc();
        assertThat(latest).hasSize(20);
        assertThat(latest.get(0).getName()).isEqualTo("Incident 0");
    }
}
