package my.nxvembrx.cherrybox.cherrykeep.advice;

import my.nxvembrx.cherrybox.cherrykeep.exception.UnauthorizedActionException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UnauthorizedActionAdvice {
    @ExceptionHandler(UnauthorizedActionException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    String unauthorizedAction(UnauthorizedActionException ex) {
        return ex.getMessage();
    }
}
