package pl.edu.wat.lab.usercontacts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.lab.usercontacts.dto.contact.ContactRequest;
import pl.edu.wat.lab.usercontacts.dto.contact.ContactResponse;
import pl.edu.wat.lab.usercontacts.exception.UserNotFoundException;
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
        Optional<User> user = userRepository.findById(userId);
        return user
                .get()
                .getContacts()
                .stream()
                .map(contact -> new ContactResponse(contact.getContactId(), contact.getName(), contact.getPhoneNumber()))
                .collect(Collectors.toList());
    }

    public ContactResponse getContact(int userId, int contactId) {
        Optional<User> user = userRepository.findById(userId);
        ContactResponse c = user
                .get()
                .getContacts()
                .stream()
                .map(contact -> new ContactResponse(contact.getContactId(), contact.getName(), contact.getPhoneNumber()))
                .filter(contactResponse -> contactResponse.getId() == contactId).findFirst().get();
        return c;
    }

    public Contact postContact(int userId, ContactRequest contactRequest) {
        Optional<User> user = userRepository.findById(userId);
        Contact contact = new Contact();
        if(!contactRequest.hasInvalidAttributes()) {
            contact.setName(contactRequest.getName());
            contact.setPhoneNumber(contactRequest.getPhoneNumber());
            contact.setUser(user.get());
            contactRepository.save(contact);
        }
        return contact;
    }

    public Optional<Contact> updateContact(int userId, int contactId, ContactRequest contactRequest) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Contact> contactOptional = contactRepository.findById(contactId);
        if(contactOptional.isPresent() && !contactRequest.hasInvalidAttributes()) {
            contactOptional.get().setName(contactRequest.getName());
            contactOptional.get().setPhoneNumber(contactRequest.getPhoneNumber());
            contactRepository.save(contactOptional.get());
        }
        return Optional.of(contactOptional.get());
    }

    public void deleteContact(int userId, int contactId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Contact> contactOptional = contactRepository.findById(contactId);
        if(contactOptional.get().getUser().getUserId() == userId){
            System.out.println(contactOptional.get().getUser().getUserId());System.out.println(userId);System.out.println(user.get().getUserId());;System.out.println(contactOptional.get().getContactId());
            contactRepository.delete(contactRepository.findById(contactId).get());
        }else{
            throw new RuntimeException("Could not find contact with id = " + contactId + " in user with id " + userId + " set");
        }
    }
}