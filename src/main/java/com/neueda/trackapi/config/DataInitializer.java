package com.neueda.trackapi.config;

import com.neueda.trackapi.model.Track;
import com.neueda.trackapi.service.TrackService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Data initialization component that runs on application startup.
 * 
 * This class demonstrates:
 * 1. Using the service layer instead of direct repository access
 * 2. Constructor-based dependency injection
 * 3. CommandLineRunner interface for startup tasks
 * 
 * The @Component annotation makes this class a Spring-managed bean.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final TrackService trackService;

    /**
     * Constructor-based dependency injection for TrackService.
     * 
     * This follows the same pattern as our controller:
     * Spring will inject the TrackService when creating this component.
     * 
     * @param trackService the service layer for track operations
     */
    public DataInitializer(TrackService trackService) {
        this.trackService = trackService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Use service layer to check if data already exists
        if (trackService.getAllTracks().isEmpty()) {
            initializeSampleTracks();
            System.out.println("✅ Sample tracks initialized in database");
        } else {
            System.out.println("📊 Database already contains " + trackService.getAllTracks().size() + " tracks");
        }
    }

    private void initializeSampleTracks() {
        // Create sample tracks using the service layer
        // Note: The service will handle ID generation and creation date setting
        
        Track track1 = new Track("Bohemian Rhapsody", "Queen", 355);
        track1.setCreationDate(LocalDateTime.now().minusDays(5));
        trackService.createTrack(track1);

        Track track2 = new Track("Hotel California", "Eagles", 391);
        track2.setCreationDate(LocalDateTime.now().minusDays(3));
        trackService.createTrack(track2);

        Track track3 = new Track("Imagine", "John Lennon", 183);
        track3.setCreationDate(LocalDateTime.now().minusDays(2));
        trackService.createTrack(track3);

        Track track4 = new Track("Sweet Child O' Mine", "Guns N' Roses", 356);
        track4.setCreationDate(LocalDateTime.now().minusDays(1));
        trackService.createTrack(track4);

        Track track5 = new Track("Stairway to Heaven", "Led Zeppelin", 482);
        track5.setCreationDate(LocalDateTime.now().minusHours(12));
        trackService.createTrack(track5);
    }
}