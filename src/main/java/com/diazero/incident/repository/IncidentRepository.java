package com.diazero.incident.repository;

import com.diazero.incident.model.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IncidentRepository extends JpaRepository<Incident, Long> {

    @Query(value = "SELECT * FROM incident ORDER BY created_at DESC LIMIT 20", nativeQuery = true)
    List<Incident> findTop20ByOrderByCreatedAtDesc();
}
