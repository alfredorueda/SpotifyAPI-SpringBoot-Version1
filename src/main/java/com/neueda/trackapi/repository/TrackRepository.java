package com.neueda.trackapi.repository;

import com.neueda.trackapi.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepository extends JpaRepository<Track, String> {
    
    // JpaRepository ya proporciona todas las operaciones CRUD básicas:
    // - save(T entity) - para CREATE y UPDATE
    // - findById(ID id) - para READ por ID
    // - findAll() - para READ todos
    // - deleteById(ID id) - para DELETE
    // - existsById(ID id) - para verificar existencia
    
    // Aquí podrías agregar métodos de consulta personalizados si los necesitas en el futuro
    // Por ejemplo:
    // List<Track> findByArtist(String artist);
    // List<Track> findByTitleContaining(String title);
}