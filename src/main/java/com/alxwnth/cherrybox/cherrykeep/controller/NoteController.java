package com.alxwnth.cherrybox.cherrykeep.controller;

import com.alxwnth.cherrybox.cherrykeep.assembler.NoteModelAssembler;
import com.alxwnth.cherrybox.cherrykeep.entity.Note;
import com.alxwnth.cherrybox.cherrykeep.entity.User;
import com.alxwnth.cherrybox.cherrykeep.exception.NoteNotFoundException;
import com.alxwnth.cherrybox.cherrykeep.exception.UnauthorizedActionException;
import com.alxwnth.cherrybox.cherrykeep.exception.UserNotFoundException;
import com.alxwnth.cherrybox.cherrykeep.repository.NoteRepository;
import com.alxwnth.cherrybox.cherrykeep.repository.UserRepository;
import com.alxwnth.cherrybox.cherrykeep.service.UserService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NoteController {
    private final NoteRepository noteRepository;
    private final NoteModelAssembler assembler;
    private final UserService userService;

    NoteController(NoteRepository noteRepository, NoteModelAssembler assembler, UserService userService) {
        this.noteRepository = noteRepository;
        this.assembler = assembler;
        this.userService = userService;
    }

    /**
     * Find the current {@link User}'s {@link Note}s based upon user id. Return a view populated with a {@link User}'s {@link Note}s.
     */
    @GetMapping("/notes")
    public String all(Model model) {
        CollectionModel<EntityModel<Note>> noteCollection = assembler.toCollectionModel(
                noteRepository.findByUserIdAndPinnedFalseOrderByCreatedAt(userService.getCurrentlyAuthenticatedUser().getId()).reversed());
        model.addAttribute("userNotes", noteCollection);
        return "notes";
    }

    @PostMapping("/notes")
    ModelAndView newNote(@RequestParam String noteText) {
        Note note = noteRepository.save(
                new Note(noteText, userService.getCurrentlyAuthenticatedUser())
        );
        ModelAndView mav = new ModelAndView("fragments/note");
        mav.addObject("noteEntity", assembler.toModel(note));
        return mav;
    }

    @GetMapping("/notes/pinned")
    String pinned(Model model) {
        CollectionModel<EntityModel<Note>> noteCollection = assembler.toCollectionModel(
                noteRepository.findByUserIdAndPinnedTrueOrderByCreatedAt(userService.getCurrentlyAuthenticatedUser().getId()).reversed());
        model.addAttribute("userNotes", noteCollection);
        return "notes";
    }

    @GetMapping("/notes/{id}")
    public EntityModel<Note> one(@PathVariable long id) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new NoteNotFoundException(id));

        // TODO: Think about doing this with an interceptor or maybe filter
        if (!userService.getCurrentlyAuthenticatedUser().getId().equals(note.getUser().getId())) {
            throw new UnauthorizedActionException();
        }

        return assembler.toModel(note);
    }

    @DeleteMapping("/notes/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable long id) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new NoteNotFoundException(id));

        if (!userService.getCurrentlyAuthenticatedUser().getId().equals(note.getUser().getId())) {
            throw new UnauthorizedActionException();
        }

        noteRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/notes/{id}/pin")
    public ResponseEntity<EntityModel<Note>> pin(@PathVariable long id) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new NoteNotFoundException(id));

        if (!userService.getCurrentlyAuthenticatedUser().getId().equals(note.getUser().getId())) {
            throw new UnauthorizedActionException();
        }

        note.pin();
        return ResponseEntity.ok(assembler.toModel(noteRepository.save(note)));
    }

    @PatchMapping("/notes/{id}/unpin")
    public ResponseEntity<EntityModel<Note>> unpin(@PathVariable long id) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new NoteNotFoundException(id));

        if (!userService.getCurrentlyAuthenticatedUser().getId().equals(note.getUser().getId())) {
            throw new UnauthorizedActionException();
        }
        
        note.unpin();
        return ResponseEntity.ok(assembler.toModel(noteRepository.save(note)));
    }
}

