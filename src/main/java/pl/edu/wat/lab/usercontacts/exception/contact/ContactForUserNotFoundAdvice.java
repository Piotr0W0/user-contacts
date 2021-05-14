package pl.edu.wat.lab.usercontacts.exception.contact;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ContactForUserNotFoundAdvice {

    @ExceptionHandler(ContactForUserNotFoundException.class)
    public ResponseEntity<String> contactForUserNotFoundHandler(ContactForUserNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
