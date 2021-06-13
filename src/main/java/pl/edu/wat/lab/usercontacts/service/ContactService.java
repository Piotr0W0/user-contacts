package pl.edu.wat.lab.usercontacts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.lab.usercontacts.dto.ContactRequest;
import pl.edu.wat.lab.usercontacts.exception.badrequest.BadRequestException;
import pl.edu.wat.lab.usercontacts.exception.contact.ContactNotFoundException;
import pl.edu.wat.lab.usercontacts.exception.user.UserNotFoundException;
import pl.edu.wat.lab.usercontacts.model.Contact;
import pl.edu.wat.lab.usercontacts.model.User;
import pl.edu.wat.lab.usercontacts.repository.ContactRepository;
import pl.edu.wat.lab.usercontacts.repository.UserRepository;
import pl.edu.wat.lab.usercontacts.validation.Validator;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactService {
    private final ContactRepository contactRepository;
    private final UserRepository userRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository, UserRepository userRepository) {
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
    }

    public List<Contact> getAllContacts(Long userId) {
        User user = findUser(userId);
        return new ArrayList<>(user.getContacts());
    }

//    public Contact getContact(Long contactId) {
//        Contact contact = findContact(contactId);
//        return contact;
//    }

    public Contact postContact(Long userId, ContactRequest contactRequest) {
        if (Validator.checkId(userId) && Validator.checkAttributes(contactRequest)) {
            Contact contact = new Contact();
            contact.setName(contactRequest.getName());
            contact.setPhoneNumber(contactRequest.getPhoneNumber());
            contact.setEmailAddress(contactRequest.getEmailAddress());
            contact.setUser(findUser(userId));
            contactRepository.save(contact);
            return contact;
        }
        throw new BadRequestException("Bad request data: " + contactRequest);
    }

    public Contact updateContact(Long contactId, ContactRequest contactRequest) {
        if (Validator.checkId(contactId) && Validator.checkAttributes(contactRequest)) {
            Contact contact = findContact(contactId);
            contact.setName(contactRequest.getName());
            contact.setPhoneNumber(contactRequest.getPhoneNumber());
            contact.setEmailAddress(contactRequest.getEmailAddress());
            contactRepository.save(contact);
            return contact;
        }
        throw new BadRequestException("Bad request data: " + contactRequest);
    }

    public void deleteContact(Long contactId) {
        Contact contact = findContact(contactId);
        contactRepository.delete(contact);
    }

    private User findUser(Long userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    private Contact findContact(Long contactId) {
        return contactRepository
                .findById(contactId)
                .orElseThrow(() -> new ContactNotFoundException(contactId));
    }
}