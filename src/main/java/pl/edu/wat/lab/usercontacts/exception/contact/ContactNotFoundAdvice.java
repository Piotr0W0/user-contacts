package pl.edu.wat.lab.usercontacts.exception.contact;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.edu.wat.lab.usercontacts.dto.ExceptionDto;

@RestControllerAdvice
public class ContactNotFoundAdvice {

    @ExceptionHandler(ContactNotFoundException.class)
    public ResponseEntity<?> contactNotFoundHandler(ContactNotFoundException e) {
        return new ResponseEntity<>(new ExceptionDto(e.getMessage()), HttpStatus.NOT_FOUND);
    }
}
