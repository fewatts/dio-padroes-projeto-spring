package com.api.freshwav.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.freshwav.model.Track;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {
    public Optional <Track> findByName(String name);
}
