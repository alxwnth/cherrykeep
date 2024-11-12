package my.nxvembrx.cherrybox.cherrykeep.exception;

public class NoteNotFoundException extends RuntimeException {
    public NoteNotFoundException(Long id) {
        super(String.format("Note with id '%s' not found", id));
    }
}
