package com.alxwnth.cherrybox.cherrykeep.controller;

import com.alxwnth.cherrybox.cherrykeep.assembler.NoteModelAssembler;
import com.alxwnth.cherrybox.cherrykeep.repository.NoteRepository;
import com.alxwnth.cherrybox.cherrykeep.exception.NoteNotFoundException;
import com.alxwnth.cherrybox.cherrykeep.entity.Note;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class NoteController {
    private final NoteRepository noteRepository;
    private final NoteModelAssembler assembler;

    NoteController(NoteRepository noteRepository, NoteModelAssembler assembler) {
        this.noteRepository = noteRepository;
        this.assembler = assembler;
    }

    @GetMapping("/notes")
    public CollectionModel<EntityModel<Note>> all() {
        List<EntityModel<Note>> notes =
                noteRepository.findAll().stream()
                        .map(assembler::toModel)
                        .collect(Collectors.toList());

        return CollectionModel.of(notes, linkTo(methodOn(NoteController.class).all()).withSelfRel());
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

