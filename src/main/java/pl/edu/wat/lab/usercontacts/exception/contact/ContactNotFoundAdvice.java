package pl.edu.wat.lab.usercontacts.exception.contact;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ContactNotFoundAdvice {

    @ExceptionHandler(ContactNotFoundException.class)
    public ResponseEntity<String> contactNotFoundHandler(ContactNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
