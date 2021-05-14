package pl.edu.wat.lab.usercontacts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.lab.usercontacts.dto.contact.ContactRequest;
import pl.edu.wat.lab.usercontacts.dto.contact.ContactResponse;
import pl.edu.wat.lab.usercontacts.dto.user.UserRequest;
import pl.edu.wat.lab.usercontacts.dto.user.UserResponse;
import pl.edu.wat.lab.usercontacts.model.Contact;
import pl.edu.wat.lab.usercontacts.model.User;
import pl.edu.wat.lab.usercontacts.service.ContactService;
import pl.edu.wat.lab.usercontacts.service.UserService;

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
    public ResponseEntity<Contact> postContact(@PathVariable int userId, @RequestBody ContactRequest contactRequest) {
        return new ResponseEntity<>(contactService.postContact(userId, contactRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{userId}/{contactId}")
    public ResponseEntity<Contact> updateContact(@PathVariable int userId, @PathVariable int contactId, @RequestBody ContactRequest contactRequest) {
        return new ResponseEntity<>(contactService.updateContact(userId, contactId, contactRequest).get(), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/{contactId}")
    public ResponseEntity<String> deleteContact(@PathVariable int userId, @PathVariable int contactId) {
        contactService.deleteContact(userId, contactId);
        return new ResponseEntity<>("Contact was deleted", HttpStatus.OK);
    }
}
