package com.neueda.trackapi.controller;

import com.neueda.trackapi.model.Track;
import com.neueda.trackapi.service.TrackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for Track API endpoints.
 * 
 * This controller demonstrates proper layered architecture by:
 * 1. Focusing solely on HTTP request/response handling
 * 2. Delegating all business logic to the TrackService
 * 3. Using constructor-based dependency injection
 * 
 * The controller acts as the presentation layer, translating HTTP requests
 * into service method calls and converting service responses back to HTTP responses.
 */
@RestController
@RequestMapping("/api/tracks")
public class TrackController {
    
    private final TrackService trackService;

    /**
     * Constructor-based dependency injection for TrackService.
     * 
     * Spring's IoC (Inversion of Control) container will:
     * 1. Create a TrackService instance (injecting TrackRepository into it)
     * 2. Create this TrackController instance
     * 3. Inject the TrackService into this controller via this constructor
     * 
     * This creates a dependency chain: Controller -> Service -> Repository
     * 
     * @param trackService the service layer for track business logic
     */
    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    /**
     * GET /api/tracks - Retrieve all tracks
     * 
     * @return List of all tracks
     */
    @GetMapping
    public List<Track> getAllTracks() {
        return trackService.getAllTracks();
    }

    /**
     * GET /api/tracks/{id} - Retrieve a track by ID
     * 
     * @param id the track ID
     * @return ResponseEntity with track if found, 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Track> getTrackById(@PathVariable String id) {
        Optional<Track> track = trackService.getTrackById(id);
        
        return track.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/tracks - Create a new track
     * 
     * The service layer handles:
     * - ID generation
     * - Creation date setting
     * - Persistence logic
     * 
     * @param track the track to create
     * @return ResponseEntity with created track and 201 status
     */
    @PostMapping
    public ResponseEntity<Track> createTrack(@RequestBody Track track) {
        Track savedTrack = trackService.createTrack(track);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTrack);
    }

    /**
     * PUT /api/tracks/{id} - Update an existing track
     * 
     * The service layer handles:
     * - Existence validation
     * - Business logic for preserving ID and creation date
     * - Update persistence
     * 
     * @param id the ID of the track to update
     * @param updatedTrack the updated track data
     * @return ResponseEntity with updated track if successful, 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Track> updateTrack(@PathVariable String id, @RequestBody Track updatedTrack) {
        Optional<Track> result = trackService.updateTrack(id, updatedTrack);
        
        return result.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /api/tracks/{id} - Delete a track
     * 
     * @param id the ID of the track to delete
     * @return ResponseEntity with 204 (No Content) if successful, 404 if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrack(@PathVariable String id) {
        boolean deleted = trackService.deleteTrack(id);
        
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}