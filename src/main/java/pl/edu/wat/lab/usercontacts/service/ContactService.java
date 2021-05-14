package pl.edu.wat.lab.usercontacts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.lab.usercontacts.dto.contact.ContactRequest;
import pl.edu.wat.lab.usercontacts.dto.contact.ContactResponse;
import pl.edu.wat.lab.usercontacts.exception.contact.ContactForUserNotFoundException;
import pl.edu.wat.lab.usercontacts.exception.contact.ContactNotFoundException;
import pl.edu.wat.lab.usercontacts.exception.user.UserNotFoundException;
import pl.edu.wat.lab.usercontacts.model.Contact;
import pl.edu.wat.lab.usercontacts.model.User;
import pl.edu.wat.lab.usercontacts.repository.ContactRepository;
import pl.edu.wat.lab.usercontacts.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactService {
    private final ContactRepository contactRepository;
    private final UserRepository userRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository, UserRepository userRepository) {
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
    }

    public List<ContactResponse> getAllContacts(int userId) {
        return findUser(userId)
                .getContacts()
                .stream()
                .map(contact -> new ContactResponse(contact.getContactId(), contact.getName(), contact.getPhoneNumber()))
                .collect(Collectors.toList());
    }

    public ContactResponse getContact(int userId, int contactId) {
        Optional<ContactResponse> contactResponse = findUser(userId)
                .getContacts()
                .stream()
                .map(contact -> new ContactResponse(contact.getContactId(), contact.getName(), contact.getPhoneNumber()))
                .filter(contactFilter -> contactFilter.getId() == contactId).findFirst();
        if (contactResponse.isEmpty()) {
            throw new ContactForUserNotFoundException(contactId, userId);
        } else {
            return contactResponse.get();
        }
    }

    public Optional<Contact> postContact(int userId, ContactRequest contactRequest) {
        if (!contactRequest.hasInvalidAttributes()) {
            Contact contact = new Contact();
            contact.setName(contactRequest.getName());
            contact.setPhoneNumber(contactRequest.getPhoneNumber());
            contact.setUser(findUser(userId));
            contactRepository.save(contact);
            return Optional.of(contact);
        } else {
            return Optional.empty();
        }
    }

    public Optional<Contact> updateContact(int userId, int contactId, ContactRequest contactRequest) {
        Contact contact = contactRepository
                .findById(contactId)
                .orElseThrow(() -> new ContactNotFoundException(contactId));
        if (!findUser(userId).getContacts().contains(contact)) {
            throw new ContactForUserNotFoundException(contactId, userId);
        }
        if(!contactRequest.hasInvalidAttributes()){
            contact.setName(contactRequest.getName());
            contact.setPhoneNumber(contactRequest.getPhoneNumber());
            contactRepository.save(contact);
        }
        return Optional.of(contact);
    }

    public void deleteContact(int userId, int contactId) {
        Contact contact = contactRepository
                .findById(contactId)
                .orElseThrow(() -> new ContactNotFoundException(contactId));
        if (contact.getUser().getUserId() == userId) {
            contactRepository.delete(contact);
        } else {
            throw new ContactForUserNotFoundException(contactId, userId);
        }
    }

    private User findUser(int userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}