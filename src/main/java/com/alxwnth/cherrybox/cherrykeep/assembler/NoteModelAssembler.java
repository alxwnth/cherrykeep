package com.alxwnth.cherrybox.cherrykeep.assembler;

import com.alxwnth.cherrybox.cherrykeep.controller.NoteController;
import com.alxwnth.cherrybox.cherrykeep.entity.Note;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class NoteModelAssembler implements RepresentationModelAssembler<Note, EntityModel<Note>> {
    @Override
    public EntityModel<Note> toModel(Note note) {
        return EntityModel.of(note,
                linkTo(methodOn(NoteController.class).one(note.getId())).withSelfRel(),
                linkTo(methodOn(NoteController.class).all(note.getUser().getId())).withRel("notes"));
    }
}
