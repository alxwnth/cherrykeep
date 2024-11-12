package my.nxvembrx.cherrybox.cherrykeep.exception;

public class UnauthorizedActionException extends RuntimeException {
    public UnauthorizedActionException() {
        super("Why are you trying to do this to someone else's note?");
    }
}
