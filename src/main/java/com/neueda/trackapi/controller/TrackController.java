package com.neueda.trackapi.controller;

import com.neueda.trackapi.model.Track;
import com.neueda.trackapi.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/tracks")
public class TrackController {
    /**
     * Repository for Track entity data access.
     * This interface extends JpaRepository to provide standard CRUD operations.
     * This violates the Single Responsibility Principle (SRP)
     * as it combines data access logic with controller logic.
     * This will be addressed in future refactoring.
     */
    private final TrackRepository trackRepository;

    /**
     * Constructor-based dependency injection for TrackRepository.
     * This is the recommended way to inject dependencies in Spring.
     */
    public TrackController(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    // GET /api/tracks - Get all tracks
    @GetMapping
    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }

    // GET /api/tracks/{id} - Get track by ID
    @GetMapping("/{id}")
    public ResponseEntity<Track> getTrackById(@PathVariable String id) {
        Optional<Track> track = trackRepository.findById(id);

        if (track.isPresent()) {
            return ResponseEntity.ok(track.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /api/tracks - Create a new track
    @PostMapping
    public ResponseEntity<Track> createTrack(@RequestBody Track track) {
        // Generate ID and set creation date
        track.setId(UUID.randomUUID().toString());
        track.setCreationDate(LocalDateTime.now());

        Track savedTrack = trackRepository.save(track);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTrack);
    }

    // PUT /api/tracks/{id} - Update an existing track
    @PutMapping("/{id}")
    public ResponseEntity<Track> updateTrack(@PathVariable String id, @RequestBody Track updatedTrack) {
        Optional<Track> existingTrack = trackRepository.findById(id);

        if (existingTrack.isPresent()) {
            // Keep the original ID and creation date
            updatedTrack.setId(id);
            updatedTrack.setCreationDate(existingTrack.get().getCreationDate());

            Track savedTrack = trackRepository.save(updatedTrack);
            return ResponseEntity.ok(savedTrack);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/tracks/{id} - Delete a track
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrack(@PathVariable String id) {
        if (trackRepository.existsById(id)) {
            trackRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}