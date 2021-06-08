package pl.edu.wat.lab.usercontacts.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.edu.wat.lab.usercontacts.dto.ContactRequest;
import pl.edu.wat.lab.usercontacts.exception.badrequest.BadRequestException;
import pl.edu.wat.lab.usercontacts.exception.contact.ContactNotFoundException;
import pl.edu.wat.lab.usercontacts.exception.user.UserNotFoundException;
import pl.edu.wat.lab.usercontacts.model.Contact;
import pl.edu.wat.lab.usercontacts.model.User;
import pl.edu.wat.lab.usercontacts.repository.ContactRepository;
import pl.edu.wat.lab.usercontacts.repository.UserRepository;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ContactService.class})
@ExtendWith(SpringExtension.class)
public class ContactServiceTest {
    @MockBean
    private ContactRepository contactRepository;

    @Autowired
    private ContactService contactService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testGetAllContacts() {
        User user = new User();
        user.setName("Name");
        user.setUserId(123L);
        user.setContacts(new HashSet<Contact>());
        Optional<User> ofResult = Optional.of(user);
        when(this.userRepository.findById(any())).thenReturn(ofResult);
        assertTrue(this.contactService.getAllContacts(123L).isEmpty());
        verify(this.userRepository).findById(any());
    }

    @Test
    public void testGetAllContacts2() {
        when(this.userRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> this.contactService.getAllContacts(123L));
        verify(this.userRepository).findById(any());
    }

    @Test
    public void testGetContact() {
        User user = new User();
        user.setName("Name");
        user.setUserId(123L);
        user.setContacts(new HashSet<Contact>());

        Contact contact = new Contact();
        contact.setContactId(123L);
        contact.setName("Name");
        contact.setPhoneNumber("4105551212");
        contact.setUser(user);
        contact.setEmailAddress("42 Main St");
        Optional<Contact> ofResult = Optional.of(contact);
        when(this.contactRepository.findById(any())).thenReturn(ofResult);
        assertSame(contact, this.contactService.getContact(123L));
        verify(this.contactRepository).findById(any());
    }

    @Test
    public void testGetContact2() {
        when(this.contactRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(ContactNotFoundException.class, () -> this.contactService.getContact(123L));
        verify(this.contactRepository).findById(any());
    }

    @Test
    public void testPostContact() {
        assertThrows(BadRequestException.class, () -> this.contactService.postContact(123L, new ContactRequest()));
    }

    @Test
    public void testPostContact2() {
        ContactRequest contactRequest = mock(ContactRequest.class);
        when(contactRequest.hasInvalidAttributes()).thenReturn(true);
        assertThrows(BadRequestException.class, () -> this.contactService.postContact(123L, contactRequest));
        verify(contactRequest).hasInvalidAttributes();
    }

    @Test
    public void testPostContact3() {
        User user = new User();
        user.setName("Name");
        user.setUserId(123L);
        user.setContacts(new HashSet<Contact>());
        Optional<User> ofResult = Optional.of(user);
        when(this.userRepository.findById(any())).thenReturn(ofResult);

        User user1 = new User();
        user1.setName("Name");
        user1.setUserId(123L);
        user1.setContacts(new HashSet<Contact>());

        Contact contact = new Contact();
        contact.setContactId(123L);
        contact.setName("Name");
        contact.setPhoneNumber("4105551212");
        contact.setUser(user1);
        contact.setEmailAddress("42 Main St");
        when(this.contactRepository.save(any())).thenReturn(contact);
        ContactRequest contactRequest = mock(ContactRequest.class);
        when(contactRequest.getEmailAddress()).thenReturn("foo");
        when(contactRequest.getPhoneNumber()).thenReturn("foo");
        when(contactRequest.getName()).thenReturn("foo");
        when(contactRequest.hasInvalidAttributes()).thenReturn(false);
        Contact actualPostContactResult = this.contactService.postContact(123L, contactRequest);
        assertSame(user, actualPostContactResult.getUser());
        assertEquals("foo", actualPostContactResult.getPhoneNumber());
        assertEquals("foo", actualPostContactResult.getEmailAddress());
        assertEquals("foo", actualPostContactResult.getName());
        verify(this.userRepository).findById(any());
        verify(this.contactRepository).save(any());
        verify(contactRequest).getEmailAddress();
        verify(contactRequest).getName();
        verify(contactRequest).getPhoneNumber();
        verify(contactRequest).hasInvalidAttributes();
    }

    @Test
    public void testPostContact4() {
        when(this.userRepository.findById(any())).thenReturn(Optional.empty());

        User user = new User();
        user.setName("Name");
        user.setUserId(123L);
        user.setContacts(new HashSet<Contact>());

        Contact contact = new Contact();
        contact.setContactId(123L);
        contact.setName("Name");
        contact.setPhoneNumber("4105551212");
        contact.setUser(user);
        contact.setEmailAddress("42 Main St");
        when(this.contactRepository.save(any())).thenReturn(contact);
        ContactRequest contactRequest = mock(ContactRequest.class);
        when(contactRequest.getEmailAddress()).thenReturn("foo");
        when(contactRequest.getPhoneNumber()).thenReturn("foo");
        when(contactRequest.getName()).thenReturn("foo");
        when(contactRequest.hasInvalidAttributes()).thenReturn(false);
        assertThrows(UserNotFoundException.class, () -> this.contactService.postContact(123L, contactRequest));
        verify(this.userRepository).findById(any());
        verify(contactRequest).getEmailAddress();
        verify(contactRequest).getName();
        verify(contactRequest).getPhoneNumber();
        verify(contactRequest).hasInvalidAttributes();
    }

    @Test
    public void testUpdateContact() {
        User user = new User();
        user.setName("Name");
        user.setUserId(123L);
        user.setContacts(new HashSet<Contact>());

        Contact contact = new Contact();
        contact.setContactId(123L);
        contact.setName("Name");
        contact.setPhoneNumber("4105551212");
        contact.setUser(user);
        contact.setEmailAddress("42 Main St");
        Optional<Contact> ofResult = Optional.of(contact);
        when(this.contactRepository.findById(any())).thenReturn(ofResult);
        assertThrows(BadRequestException.class, () -> this.contactService.updateContact(123L, new ContactRequest()));
        verify(this.contactRepository).findById(any());
    }

    @Test
    public void testUpdateContact2() {
        when(this.contactRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(ContactNotFoundException.class, () -> this.contactService.updateContact(123L, new ContactRequest()));
        verify(this.contactRepository).findById(any());
    }

    @Test
    public void testDeleteContact() {
        User user = new User();
        user.setName("Name");
        user.setUserId(123L);
        user.setContacts(new HashSet<Contact>());

        Contact contact = new Contact();
        contact.setContactId(123L);
        contact.setName("Name");
        contact.setPhoneNumber("4105551212");
        contact.setUser(user);
        contact.setEmailAddress("42 Main St");
        Optional<Contact> ofResult = Optional.of(contact);
        doNothing().when(this.contactRepository).delete(any());
        when(this.contactRepository.findById(any())).thenReturn(ofResult);
        this.contactService.deleteContact(123L);
        verify(this.contactRepository).delete(any());
        verify(this.contactRepository).findById(any());
    }

    @Test
    public void testDeleteContact2() {
        doNothing().when(this.contactRepository).delete(any());
        when(this.contactRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(ContactNotFoundException.class, () -> this.contactService.deleteContact(123L));
        verify(this.contactRepository).findById(any());
    }
}

