package pl.edu.wat.lab.usercontacts.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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
import java.util.HashSet;
import java.util.List;
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
        user.setUserId(123);
        user.setContacts(new HashSet<Contact>());
        Optional<User> ofResult = Optional.of(user);
        when(this.userRepository.findById(any())).thenReturn(ofResult);
        PageImpl<Contact> pageImpl = new PageImpl<Contact>(new ArrayList<Contact>());
        when(this.contactRepository.findAllByUser(any(), any()))
                .thenReturn(pageImpl);
        Page<ContactResponse> actualAllContacts = this.contactService.getAllContacts(123, 1, 3);
        assertEquals(pageImpl, actualAllContacts);
        assertTrue(actualAllContacts.toList().isEmpty());
        verify(this.contactRepository).findAllByUser(any(), any());
        verify(this.userRepository).findById(any());
    }

    @Test
    public void testGetAllContacts2() {
        when(this.userRepository.findById(any())).thenReturn(Optional.empty());
        when(this.contactRepository.findAllByUser(any(), any()))
                .thenReturn(new PageImpl<Contact>(new ArrayList<Contact>()));
        assertThrows(UserNotFoundException.class, () -> this.contactService.getAllContacts(123, 1, 3));
        verify(this.userRepository).findById(any());
    }

    @Test
    public void testGetAllContacts3() {
        User user = new User();
        user.setName("Name");
        user.setUserId(123);
        user.setContacts(new HashSet<Contact>());
        Optional<User> ofResult = Optional.of(user);
        when(this.userRepository.findById(any())).thenReturn(ofResult);

        User user1 = new User();
        user1.setName("name");
        user1.setUserId(123);
        user1.setContacts(new HashSet<Contact>());

        Contact contact = new Contact();
        contact.setContactId(123);
        contact.setName("name");
        contact.setPhoneNumber("4105551212");
        contact.setUser(user1);

        ArrayList<Contact> contactList = new ArrayList<Contact>();
        contactList.add(contact);
        PageImpl<Contact> pageImpl = new PageImpl<Contact>(contactList);
        when(this.contactRepository.findAllByUser(any(), any()))
                .thenReturn(pageImpl);
        List<ContactResponse> toListResult = this.contactService.getAllContacts(123, 1, 3).toList();
        assertEquals(1, toListResult.size());
        ContactResponse getResult = toListResult.get(0);
        assertEquals(123, getResult.getId());
        assertEquals("4105551212", getResult.getPhoneNumber());
        assertEquals("name", getResult.getName());
        verify(this.contactRepository).findAllByUser(any(), any());
        verify(this.userRepository).findById(any());
    }

    @Test
    public void testGetAllContacts4() {
        User user = new User();
        user.setName("Name");
        user.setUserId(123);
        user.setContacts(new HashSet<Contact>());
        Optional<User> ofResult = Optional.of(user);
        when(this.userRepository.findById(any())).thenReturn(ofResult);

        User user1 = new User();
        user1.setName("name");
        user1.setUserId(123);
        user1.setContacts(new HashSet<Contact>());

        Contact contact = new Contact();
        contact.setContactId(123);
        contact.setName("name");
        contact.setPhoneNumber("4105551212");
        contact.setUser(user1);

        User user2 = new User();
        user2.setName("name");
        user2.setUserId(123);
        user2.setContacts(new HashSet<Contact>());

        Contact contact1 = new Contact();
        contact1.setContactId(123);
        contact1.setName("name");
        contact1.setPhoneNumber("4105551212");
        contact1.setUser(user2);

        ArrayList<Contact> contactList = new ArrayList<Contact>();
        contactList.add(contact1);
        contactList.add(contact);
        PageImpl<Contact> pageImpl = new PageImpl<Contact>(contactList);
        when(this.contactRepository.findAllByUser(any(), any()))
                .thenReturn(pageImpl);
        List<ContactResponse> toListResult = this.contactService.getAllContacts(123, 1, 3).toList();
        assertEquals(2, toListResult.size());
        ContactResponse getResult = toListResult.get(0);
        assertEquals("4105551212", getResult.getPhoneNumber());
        ContactResponse getResult1 = toListResult.get(1);
        assertEquals("4105551212", getResult1.getPhoneNumber());
        assertEquals("name", getResult1.getName());
        assertEquals(123, getResult1.getId());
        assertEquals("name", getResult.getName());
        assertEquals(123, getResult.getId());
        verify(this.contactRepository).findAllByUser(any(), any());
        verify(this.userRepository).findById(any());
    }

    @Test
    public void testGetAllContacts5() {
        User user = new User();
        user.setName("Name");
        user.setUserId(123);
        user.setContacts(new HashSet<Contact>());
        Optional<User> ofResult = Optional.of(user);
        when(this.userRepository.findById(any())).thenReturn(ofResult);
        when(this.contactRepository.findAllByUser(any(), any()))
                .thenReturn(new PageImpl<Contact>(new ArrayList<Contact>()));
        this.contactService.getAllContacts(123, -1, 3);
        verify(this.userRepository).findById(any());
    }

    @Test
    public void testGetContact() {
        User user = new User();
        user.setName("Name");
        user.setUserId(123);
        user.setContacts(new HashSet<Contact>());
        Optional<User> ofResult = Optional.of(user);
        when(this.userRepository.findById(any())).thenReturn(ofResult);
        assertThrows(ContactForUserNotFoundException.class, () -> this.contactService.getContact(123, 123));
        verify(this.userRepository).findById(any());
    }

    @Test
    public void testGetContact2() {
        User user = new User();
        user.setName("Name");
        user.setUserId(123);
        user.setContacts(new HashSet<Contact>());

        Contact contact = new Contact();
        contact.setContactId(123);
        contact.setName("Name");
        contact.setPhoneNumber("4105551212");
        contact.setUser(user);

        HashSet<Contact> contactSet = new HashSet<Contact>();
        contactSet.add(contact);

        User user1 = new User();
        user1.setName("Name");
        user1.setUserId(123);
        user1.setContacts(contactSet);
        Optional<User> ofResult = Optional.of(user1);
        when(this.userRepository.findById(any())).thenReturn(ofResult);
        ContactResponse actualContact = this.contactService.getContact(123, 123);
        assertEquals(123, actualContact.getId());
        assertEquals("4105551212", actualContact.getPhoneNumber());
        assertEquals("Name", actualContact.getName());
        verify(this.userRepository).findById(any());
    }

    @Test
    public void testGetContact3() {
        when(this.userRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> this.contactService.getContact(123, 123));
        verify(this.userRepository).findById(any());
    }

    @Test
    public void testGetContact4() {
        User user = new User();
        user.setName("Name");
        user.setUserId(123);
        user.setContacts(new HashSet<Contact>());

        Contact contact = new Contact();
        contact.setContactId(123);
        contact.setName("Name");
        contact.setPhoneNumber("4105551212");
        contact.setUser(user);

        HashSet<Contact> contactSet = new HashSet<Contact>();
        contactSet.add(contact);

        User user1 = new User();
        user1.setName("Name");
        user1.setUserId(123);
        user1.setContacts(contactSet);
        Optional<User> ofResult = Optional.of(user1);
        when(this.userRepository.findById(any())).thenReturn(ofResult);
        assertThrows(ContactForUserNotFoundException.class, () -> this.contactService.getContact(123, 0));
        verify(this.userRepository).findById(any());
    }

    @Test
    public void testGetContact5() {
        User user = new User();
        user.setName("Name");
        user.setUserId(123);
        user.setContacts(new HashSet<Contact>());

        Contact contact = new Contact();
        contact.setContactId(123);
        contact.setName("Name");
        contact.setPhoneNumber("4105551212");
        contact.setUser(user);

        User user1 = new User();
        user1.setName("Name");
        user1.setUserId(123);
        user1.setContacts(new HashSet<Contact>());

        Contact contact1 = new Contact();
        contact1.setContactId(123);
        contact1.setName("Name");
        contact1.setPhoneNumber("4105551212");
        contact1.setUser(user1);

        HashSet<Contact> contactSet = new HashSet<Contact>();
        contactSet.add(contact1);
        contactSet.add(contact);

        User user2 = new User();
        user2.setName("Name");
        user2.setUserId(123);
        user2.setContacts(contactSet);
        Optional<User> ofResult = Optional.of(user2);
        when(this.userRepository.findById(any())).thenReturn(ofResult);
        assertThrows(ContactForUserNotFoundException.class, () -> this.contactService.getContact(123, 0));
        verify(this.userRepository).findById(any());
    }

    @Test
    public void testPostContact() {
        User user = new User();
        user.setName("Name");
        user.setUserId(123);
        user.setContacts(new HashSet<Contact>());
        Optional<User> ofResult = Optional.of(user);
        when(this.userRepository.findById(any())).thenReturn(ofResult);

        User user1 = new User();
        user1.setName("Name");
        user1.setUserId(123);
        user1.setContacts(new HashSet<Contact>());

        Contact contact = new Contact();
        contact.setContactId(123);
        contact.setName("Name");
        contact.setPhoneNumber("4105551212");
        contact.setUser(user1);
        when(this.contactRepository.save(any())).thenReturn(contact);
        Optional<Contact> actualPostContactResult = this.contactService.postContact(123,
                new ContactRequest("Name", "4105551212"));
        assertTrue(actualPostContactResult.isPresent());
        Contact getResult = actualPostContactResult.get();
        assertSame(user, getResult.getUser());
        assertEquals("4105551212", getResult.getPhoneNumber());
        assertEquals("Name", getResult.getName());
        verify(this.contactRepository).save(any());
        verify(this.userRepository).findById(any());
    }

    @Test
    public void testPostContact2() {
        when(this.userRepository.findById(any())).thenReturn(Optional.empty());

        User user = new User();
        user.setName("Name");
        user.setUserId(123);
        user.setContacts(new HashSet<Contact>());

        Contact contact = new Contact();
        contact.setContactId(123);
        contact.setName("Name");
        contact.setPhoneNumber("4105551212");
        contact.setUser(user);
        when(this.contactRepository.save(any())).thenReturn(contact);
        assertThrows(UserNotFoundException.class,
                () -> this.contactService.postContact(123, new ContactRequest("Name", "4105551212")));
        verify(this.userRepository).findById(any());
    }

    @Test
    public void testPostContact3() {
        User user = new User();
        user.setName("Name");
        user.setUserId(123);
        user.setContacts(new HashSet<Contact>());
        Optional<User> ofResult = Optional.of(user);
        when(this.userRepository.findById(any())).thenReturn(ofResult);

        User user1 = new User();
        user1.setName("Name");
        user1.setUserId(123);
        user1.setContacts(new HashSet<Contact>());

        Contact contact = new Contact();
        contact.setContactId(123);
        contact.setName("Name");
        contact.setPhoneNumber("4105551212");
        contact.setUser(user1);
        when(this.contactRepository.save(any())).thenReturn(contact);
        assertFalse(this.contactService.postContact(123, new ContactRequest()).isPresent());
    }

    @Test
    public void testDeleteContact() {
        User user = new User();
        user.setName("Name");
        user.setUserId(123);
        user.setContacts(new HashSet<Contact>());

        Contact contact = new Contact();
        contact.setContactId(123);
        contact.setName("Name");
        contact.setPhoneNumber("4105551212");
        contact.setUser(user);
        Optional<Contact> ofResult = Optional.of(contact);
        doNothing().when(this.contactRepository).delete(any());
        when(this.contactRepository.findById(any())).thenReturn(ofResult);
        this.contactService.deleteContact(123, 123);
        verify(this.contactRepository).delete(any());
        verify(this.contactRepository).findById(any());
    }

    @Test
    public void testDeleteContact2() {
        User user = new User();
        user.setName("Name");
        user.setUserId(0);
        user.setContacts(new HashSet<Contact>());

        Contact contact = new Contact();
        contact.setContactId(123);
        contact.setName("Name");
        contact.setPhoneNumber("4105551212");
        contact.setUser(user);
        Optional<Contact> ofResult = Optional.of(contact);
        doNothing().when(this.contactRepository).delete(any());
        when(this.contactRepository.findById(any())).thenReturn(ofResult);
        assertThrows(ContactForUserNotFoundException.class, () -> this.contactService.deleteContact(123, 123));
        verify(this.contactRepository).findById(any());
    }

    @Test
    public void testDeleteContact3() {
        doNothing().when(this.contactRepository).delete(any());
        when(this.contactRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(ContactNotFoundException.class, () -> this.contactService.deleteContact(123, 123));
        verify(this.contactRepository).findById(any());
    }
}

