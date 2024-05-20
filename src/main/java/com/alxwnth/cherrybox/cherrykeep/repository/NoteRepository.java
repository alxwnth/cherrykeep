package com.alxwnth.cherrybox.cherrykeep.repository;

import com.alxwnth.cherrybox.cherrykeep.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {

}
