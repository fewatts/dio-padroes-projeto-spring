package com.api.freshwav.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.freshwav.model.Track;
import com.api.freshwav.repository.TrackRepository;

@RestController
@RequestMapping("/track")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TrackController {

    @Autowired
    private TrackRepository trackRepository;

    // Padrão Singleton: O Spring gerencia a instância do TrackRepository como um
    // Singleton.

    @GetMapping("/all")
    public ResponseEntity<List<Track>> findAll() {
        return ResponseEntity.ok(trackRepository.findAll());
    }

    // Padrão Repository: Utilização do TrackRepository para buscar todos os
    // registros.

    @GetMapping("/{id}")
    public ResponseEntity<Track> findById(@PathVariable Long id) {
        return trackRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Padrão Repository: Utilização do TrackRepository para buscar um registro por
    // ID.

    @PostMapping("/register")
    public ResponseEntity<Track> postTrack(@Valid @RequestBody Track track) {
        return ResponseEntity.status(HttpStatus.CREATED).body(trackRepository.save(track));
    }

    // Padrão Repository: Utilização do TrackRepository para salvar um novo
    // registro.

    @PutMapping("/update/{id}")
    public ResponseEntity<Track> put(@PathVariable Long id, @Valid @RequestBody Track track) {
        return trackRepository.findById(id)
                .map(existingTrack -> {
                    track.setId(existingTrack.getId());
                    Track updatedTrack = trackRepository.save(track);

                    // Padrão Repository: Utilização do TrackRepository para buscar, atualizar e
                    // salvar registros.

                    return ResponseEntity.ok(updatedTrack);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return trackRepository.findById(id)
                .map(existingTrack -> {
                    trackRepository.deleteById(id);

                    // Padrão Repository: Utilização do TrackRepository para buscar e excluir
                    // registros.

                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
