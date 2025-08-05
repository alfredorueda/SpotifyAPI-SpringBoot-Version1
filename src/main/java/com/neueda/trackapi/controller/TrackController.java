package com.neueda.trackapi.controller;

import com.neueda.trackapi.model.Track;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/tracks")
public class TrackController {
    
    private final List<Track> tracks = new ArrayList<>();
    
    // Initialize with sample tracks
    public TrackController() {
        initializeSampleTracks();
    }
    
    private void initializeSampleTracks() {
        // Sample Track 1
        Track track1 = new Track("Bohemian Rhapsody", "Queen", 355);
        track1.setId(UUID.randomUUID().toString());
        track1.setCreationDate(LocalDateTime.now().minusDays(5));
        tracks.add(track1);
        
        // Sample Track 2
        Track track2 = new Track("Hotel California", "Eagles", 391);
        track2.setId(UUID.randomUUID().toString());
        track2.setCreationDate(LocalDateTime.now().minusDays(3));
        tracks.add(track2);
        
        // Sample Track 3
        Track track3 = new Track("Imagine", "John Lennon", 183);
        track3.setId(UUID.randomUUID().toString());
        track3.setCreationDate(LocalDateTime.now().minusDays(2));
        tracks.add(track3);
        
        // Sample Track 4
        Track track4 = new Track("Sweet Child O' Mine", "Guns N' Roses", 356);
        track4.setId(UUID.randomUUID().toString());
        track4.setCreationDate(LocalDateTime.now().minusDays(1));
        tracks.add(track4);
        
        // Sample Track 5
        Track track5 = new Track("Stairway to Heaven", "Led Zeppelin", 482);
        track5.setId(UUID.randomUUID().toString());
        track5.setCreationDate(LocalDateTime.now().minusHours(12));
        tracks.add(track5);
    }

    // GET /api/tracks - Get all tracks
    @GetMapping
    public List<Track> getAllTracks() {
        return tracks;
    }

    // GET /api/tracks/{id} - Get track by ID
    @GetMapping("/{id}")
    public ResponseEntity<Track> getTrackById(@PathVariable String id) {
        Optional<Track> track = tracks.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst();
        
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
        
        tracks.add(track);
        return ResponseEntity.status(HttpStatus.CREATED).body(track);
    }

    // PUT /api/tracks/{id} - Update an existing track
    @PutMapping("/{id}")
    public ResponseEntity<Track> updateTrack(@PathVariable String id, @RequestBody Track updatedTrack) {
        for (int i = 0; i < tracks.size(); i++) {
            Track track = tracks.get(i);
            if (track.getId().equals(id)) {
                // Keep the original ID and creation date
                updatedTrack.setId(id);
                updatedTrack.setCreationDate(track.getCreationDate());
                tracks.set(i, updatedTrack);
                return ResponseEntity.ok(updatedTrack);
            }
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE /api/tracks/{id} - Delete a track
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrack(@PathVariable String id) {
        boolean removed = tracks.removeIf(track -> track.getId().equals(id));
        
        if (removed) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}