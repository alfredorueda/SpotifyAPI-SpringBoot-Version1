package com.neueda.trackapi.service;

import com.neueda.trackapi.model.Track;
import com.neueda.trackapi.repository.TrackRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service layer for Track entity business logic.
 * 
 * This class demonstrates proper layered architecture by:
 * 1. Separating business logic from the REST controller
 * 2. Acting as an intermediary between the controller and repository
 * 3. Using constructor-based dependency injection (recommended over @Autowired)
 * 
 * The @Service annotation marks this class as a Spring service component,
 * making it eligible for dependency injection.
 */
@Service
public class TrackService {

    private final TrackRepository trackRepository;

    /**
     * Constructor-based dependency injection.
     * 
     * Spring will automatically inject the TrackRepository when creating this service.
     * This approach is preferred over @Autowired field injection because:
     * - It makes dependencies explicit and immutable
     * - It enables easier unit testing
     * - It prevents NullPointerException issues
     * - It follows Spring's recommended practices
     * 
     * @param trackRepository the repository to handle track persistence
     */
    public TrackService(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    /**
     * Retrieves all tracks from the database.
     * 
     * @return List of all tracks
     */
    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }

    /**
     * Retrieves a track by its ID.
     * 
     * @param id the track ID
     * @return Optional containing the track if found, empty otherwise
     */
    public Optional<Track> getTrackById(String id) {
        return trackRepository.findById(id);
    }

    /**
     * Creates a new track.
     * 
     * This method handles the business logic for track creation:
     * - Generates a unique ID using UUID
     * - Sets the creation timestamp
     * - Persists the track to the database
     * 
     * @param track the track to create (without ID and creationDate)
     * @return the saved track with generated ID and creationDate
     */
    public Track createTrack(Track track) {
        // Business logic: generate ID and set creation date
        track.setId(UUID.randomUUID().toString());
        track.setCreationDate(LocalDateTime.now());
        
        // Delegate persistence to repository
        return trackRepository.save(track);
    }

    /**
     * Updates an existing track.
     * 
     * This method handles the business logic for track updates:
     * - Validates that the track exists
     * - Preserves the original ID and creation date
     * - Updates only the modifiable fields
     * 
     * @param id the ID of the track to update
     * @param updatedTrack the track data to update
     * @return Optional containing the updated track if successful, empty if not found
     */
    public Optional<Track> updateTrack(String id, Track updatedTrack) {
        Optional<Track> existingTrack = trackRepository.findById(id);
        
        if (existingTrack.isPresent()) {
            // Business logic: preserve ID and creation date
            updatedTrack.setId(id);
            updatedTrack.setCreationDate(existingTrack.get().getCreationDate());
            
            // Delegate persistence to repository
            Track savedTrack = trackRepository.save(updatedTrack);
            return Optional.of(savedTrack);
        }
        
        return Optional.empty();
    }

    /**
     * Deletes a track by its ID.
     * 
     * @param id the ID of the track to delete
     * @return true if the track was deleted, false if not found
     */
    public boolean deleteTrack(String id) {
        if (trackRepository.existsById(id)) {
            trackRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Checks if a track exists by its ID.
     * 
     * @param id the track ID
     * @return true if the track exists, false otherwise
     */
    public boolean existsById(String id) {
        return trackRepository.existsById(id);
    }
}