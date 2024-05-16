package com.alxwnth.cherrybox.cherrykeep;

import com.alxwnth.cherrybox.cherrykeep.exception.NoteNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NoteController {
    private final NoteRepository noteRepository;

    NoteController(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @GetMapping("/notes")
    List<Note> all() {
        return noteRepository.findAll();
    }

    @PostMapping("/notes")
    Note newNote(@RequestBody Note note) {
        return noteRepository.save(note);
    }

    @GetMapping("/notes/{id}")
    Note getNote(@PathVariable long id) {
        return noteRepository.findById(id).orElseThrow(() -> new NoteNotFoundException(id));
    }

    @DeleteMapping("/notes/{id}")
    void deleteNote(@PathVariable long id) {
        noteRepository.deleteById(id);
    }
}

