package pl.edu.wat.lab.usercontacts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.lab.usercontacts.dto.contact.ContactRequest;
import pl.edu.wat.lab.usercontacts.dto.contact.ContactResponse;
import pl.edu.wat.lab.usercontacts.model.Contact;
import pl.edu.wat.lab.usercontacts.service.ContactService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ContactController {
    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("{userId}")
    public ResponseEntity<List<ContactResponse>> getAllContacts(@PathVariable int userId) {
        return new ResponseEntity<>(contactService.getAllContacts(userId), HttpStatus.OK);
    }

    @GetMapping("{userId}/{contactId}")
    public ResponseEntity<ContactResponse> getContact(@PathVariable int userId, @PathVariable int contactId) {
        return new ResponseEntity<>(contactService.getContact(userId, contactId), HttpStatus.OK);
    }

    @PostMapping("{userId}")
    public ResponseEntity<String> postContact(@PathVariable int userId, @RequestBody ContactRequest contactRequest) {
        Optional<Contact> contact = contactService.postContact(userId, contactRequest);
        if (contact.isPresent()) {
            return new ResponseEntity<>("Contact was created", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{userId}/{contactId}")
    public ResponseEntity<Contact> updateContact(@PathVariable int userId, @PathVariable int contactId, @RequestBody ContactRequest contactRequest) {
        return contactService
                .updateContact(userId, contactId, contactRequest)
                .map(response -> new ResponseEntity<>(response, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("/{userId}/{contactId}")
    public ResponseEntity<String> deleteContact(@PathVariable int userId, @PathVariable int contactId) {
        contactService.deleteContact(userId, contactId);
        return new ResponseEntity<>("Contact was deleted", HttpStatus.OK);
    }
}
