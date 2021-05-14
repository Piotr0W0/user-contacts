package pl.edu.wat.lab.usercontacts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.wat.lab.usercontacts.dto.contact.ContactRequest;
import pl.edu.wat.lab.usercontacts.dto.contact.ContactResponse;
import pl.edu.wat.lab.usercontacts.exception.contact.ContactForUserNotFoundException;
import pl.edu.wat.lab.usercontacts.exception.contact.ContactNotFoundException;
import pl.edu.wat.lab.usercontacts.exception.user.UserNotFoundException;
import pl.edu.wat.lab.usercontacts.model.Contact;
import pl.edu.wat.lab.usercontacts.model.User;
import pl.edu.wat.lab.usercontacts.repository.ContactRepository;
import pl.edu.wat.lab.usercontacts.repository.UserRepository;

import java.util.ArrayList;
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

    public Page<ContactResponse> getAllContacts(int userId, int page, int size) {
        return contactRepository.findAllByUser(findUser(userId), PageRequest.of(page, size, Sort.by("name").ascending()))
                .map(contact -> new ContactResponse(contact.getContactId(), contact.getName(), contact.getPhoneNumber()));
    }

    @Transactional // it's not needed
    public Page<ContactResponse> getFilteredContacts(int userId, String name, int page, int size) {
        List<ContactResponse> contacts = contactRepository.streamAllByUser(findUser(userId))
                .filter(contact -> name == null || name.equals(contact.getName()))
                .map(contact -> new ContactResponse(contact.getContactId(), contact.getName(), contact.getPhoneNumber()))
                .collect(Collectors.toList());

        List<ContactResponse> pageToReturn = new ArrayList<>();
        int startIndex = page * size;
        int endIndex = startIndex + size;

        if (contacts.size() < endIndex) {
            endIndex = contacts.size();
        }

        for (int i = startIndex; i < endIndex; i++) {
            pageToReturn.add(contacts.get(i));
        }

        return new PageImpl<>(pageToReturn, PageRequest.of(page, size, Sort.by("name").ascending()), contacts.size());
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