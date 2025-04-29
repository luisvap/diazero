package com.diazero.incident.service;

import com.diazero.incident.model.Incident;
import com.diazero.incident.repository.IncidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncidentService {

    @Autowired
    private IncidentRepository repository;

    public Incident save(Incident incident) {
        return repository.save(incident);
    }

    public List<Incident> findAll() {
        return repository.findAll();
    }

    public Optional<Incident> findById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<Incident> getLast20Incidents() {
        return repository.findTop20ByOrderByCreatedAtDesc();
    }
}
