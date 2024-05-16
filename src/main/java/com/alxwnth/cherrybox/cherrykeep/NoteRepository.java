package com.alxwnth.cherrybox.cherrykeep;

import org.springframework.data.jpa.repository.JpaRepository;

interface NoteRepository extends JpaRepository<Note, Long> {

}
