package fr.supinfo.league;

import fr.supinfo.league.season.SeasonNotEmptyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = {SeasonNotEmptyException.class})
    @ResponseStatus(HttpStatus.LOCKED)
    public ResponseEntity<Object> handleException(SeasonNotEmptyException ex) {
        return ResponseEntity.badRequest().body("Impossible de supprimer la saison "
                + ex.getMessage() + ", en effet, elle contient encore des définitions de journées");
    }
}
