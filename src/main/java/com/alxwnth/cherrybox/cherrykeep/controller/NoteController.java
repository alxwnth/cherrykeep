package com.alxwnth.cherrybox.cherrykeep.controller;

import com.alxwnth.cherrybox.cherrykeep.assembler.NoteModelAssembler;
import com.alxwnth.cherrybox.cherrykeep.entity.Note;
import com.alxwnth.cherrybox.cherrykeep.entity.User;
import com.alxwnth.cherrybox.cherrykeep.exception.NoteNotFoundException;
import com.alxwnth.cherrybox.cherrykeep.exception.UserNotFoundException;
import com.alxwnth.cherrybox.cherrykeep.repository.NoteRepository;
import com.alxwnth.cherrybox.cherrykeep.repository.UserRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class NoteController {
    private final NoteRepository noteRepository;
    private final NoteModelAssembler assembler;
    private final UserRepository userRepository;

    NoteController(NoteRepository noteRepository, NoteModelAssembler assembler, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.assembler = assembler;
        this.userRepository = userRepository;
    }

    /**
     * Find a {@link User}'s {@link Note}s based upon user id. Turn it into context-based link.
     */
    @GetMapping("/users/{id}/notes")
    public CollectionModel<EntityModel<Note>> all(@PathVariable Long id) {
        return assembler.toCollectionModel(noteRepository.findByUserId(id));
    }

    @PostMapping("/notes")
    Note newNote(@RequestParam String noteText) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();
        Long authenticatedUserId = authenticatedUser.getId();
        User userInDatabase = userRepository.findById(authenticatedUserId)
                .orElseThrow(() -> new UserNotFoundException(authenticatedUserId));

        Note note = new Note(noteText, userInDatabase);

        return noteRepository.save(note);
    }

    @GetMapping("/notes/{id}")
    public EntityModel<Note> one(@PathVariable long id) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new NoteNotFoundException(id));
        return assembler.toModel(note);
    }

    @DeleteMapping("/notes/{id}")
    public void deleteNote(@PathVariable long id) {
        noteRepository.deleteById(id);
    }

    @GetMapping("/notes/{id}/pin")
    public ResponseEntity<EntityModel<Note>> pin(@PathVariable long id) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new NoteNotFoundException(id));
        note.pin();
        return ResponseEntity.ok(assembler.toModel(noteRepository.save(note)));
    }

    @GetMapping("/notes/{id}/unpin")
    public ResponseEntity<EntityModel<Note>> unpin(@PathVariable long id) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new NoteNotFoundException(id));
        note.unpin();
        return ResponseEntity.ok(assembler.toModel(noteRepository.save(note)));
    }
}

