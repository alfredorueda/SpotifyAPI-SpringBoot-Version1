package com.neueda.trackapi.config;

import com.neueda.trackapi.model.Track;
import com.neueda.trackapi.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class DataInitializer implements CommandLineRunner {
    /**
     * Repository for Track entity data access.
     * This interface extends JpaRepository to provide standard CRUD operations.
     */
    private final TrackRepository trackRepository;

    public DataInitializer(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if the database is empty and initialize with sample tracks if it is
        if (trackRepository.count() == 0) {
            initializeSampleTracks();
            System.out.println("✅ Sample tracks initialized in database");
        } else {
            System.out.println("📊 Database already contains " + trackRepository.count() + " tracks");
        }
    }

    private void initializeSampleTracks() {
        // Sample Track 1
        Track track1 = new Track("Bohemian Rhapsody", "Queen", 355);
        track1.setId(UUID.randomUUID().toString());
        track1.setCreationDate(LocalDateTime.now().minusDays(5));
        trackRepository.save(track1);

        // Sample Track 2
        Track track2 = new Track("Hotel California", "Eagles", 391);
        track2.setId(UUID.randomUUID().toString());
        track2.setCreationDate(LocalDateTime.now().minusDays(3));
        trackRepository.save(track2);

        // Sample Track 3
        Track track3 = new Track("Imagine", "John Lennon", 183);
        track3.setId(UUID.randomUUID().toString());
        track3.setCreationDate(LocalDateTime.now().minusDays(2));
        trackRepository.save(track3);

        // Sample Track 4
        Track track4 = new Track("Sweet Child O' Mine", "Guns N' Roses", 356);
        track4.setId(UUID.randomUUID().toString());
        track4.setCreationDate(LocalDateTime.now().minusDays(1));
        trackRepository.save(track4);

        // Sample Track 5
        Track track5 = new Track("Stairway to Heaven", "Led Zeppelin", 482);
        track5.setId(UUID.randomUUID().toString());
        track5.setCreationDate(LocalDateTime.now().minusHours(12));
        trackRepository.save(track5);
    }
}