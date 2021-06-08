package pl.edu.wat.lab.usercontacts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.lab.usercontacts.dto.ContactRequest;
import pl.edu.wat.lab.usercontacts.model.Contact;
import pl.edu.wat.lab.usercontacts.service.ContactService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contacts")
public class ContactController {
    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAllContacts(@PathVariable Long userId) {
        List<Contact> allContacts = contactService.getAllContacts(userId);
        return new ResponseEntity<>(allContacts, HttpStatus.OK);
    }

    @GetMapping("/{contactId}")
    public ResponseEntity<?> getContact(@PathVariable Long contactId) {
        Contact contact = contactService.getContact(contactId);
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> postContact(@PathVariable Long userId, @RequestBody ContactRequest contactRequest) {
        Contact contact = contactService.postContact(userId, contactRequest);
        return new ResponseEntity<>("Contact " + contact.getContactId() + " was created", HttpStatus.CREATED);
    }

    @PatchMapping("/{contactId}")
    public ResponseEntity<?> updateContact(@PathVariable Long contactId, @RequestBody ContactRequest contactRequest) {
        Contact contact = contactService.updateContact(contactId, contactRequest);
        return new ResponseEntity<>("Contact with id " + contact.getContactId() + " was updated", HttpStatus.OK);
    }

    @DeleteMapping("/{contactId}")
    public ResponseEntity<?> deleteContact(@PathVariable Long contactId) {
        contactService.deleteContact(contactId);
        return new ResponseEntity<>("Contact " + contactId + " was deleted", HttpStatus.OK);
    }
}
