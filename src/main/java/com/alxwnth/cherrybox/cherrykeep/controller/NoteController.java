package com.alxwnth.cherrybox.cherrykeep.controller;

import com.alxwnth.cherrybox.cherrykeep.assembler.NoteModelAssembler;
import com.alxwnth.cherrybox.cherrykeep.entity.Note;
import com.alxwnth.cherrybox.cherrykeep.entity.User;
import com.alxwnth.cherrybox.cherrykeep.exception.NoteNotFoundException;
import com.alxwnth.cherrybox.cherrykeep.repository.NoteRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

@RestController
public class NoteController {
    private final NoteRepository noteRepository;
    private final NoteModelAssembler assembler;

    NoteController(NoteRepository noteRepository, NoteModelAssembler assembler) {
        this.noteRepository = noteRepository;
        this.assembler = assembler;
    }

    /**
     * Find a {@link User}'s {@link Note}s based upon user id. Turn it into context-based link.
     */

    @GetMapping("/users/{id}/notes")
    public CollectionModel<EntityModel<Note>> all(@PathVariable Long id) {
        return assembler.toCollectionModel(noteRepository.findByUserId(id));
    }

    @PostMapping("/notes")
    Note newNote(@RequestBody Note note) {
        return noteRepository.save(note);
    }

    @GetMapping("/notes/{id}")
    public EntityModel<Note> one(@PathVariable long id) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new NoteNotFoundException(id));
        return assembler.toModel(note);
    }

    @DeleteMapping("/notes/{id}")
    void deleteNote(@PathVariable long id) {
        noteRepository.deleteById(id);
    }
}

