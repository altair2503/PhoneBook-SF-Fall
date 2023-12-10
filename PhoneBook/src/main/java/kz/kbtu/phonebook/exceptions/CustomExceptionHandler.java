package kz.kbtu.phonebook.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>("Exception : " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CustomBadCredentialsException.class)
    public ResponseEntity<String> handleCustomBadCredentialsException(CustomBadCredentialsException ex){
        return new ResponseEntity<>("Exception : " + ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(PermissionDeniedException.class)
    public ResponseEntity<String> handlePerrmissionDeniedExceptionException(PermissionDeniedException ex){
        return new ResponseEntity<>("Exception : " + ex.getMessage(), HttpStatus.FORBIDDEN);
    }

}
