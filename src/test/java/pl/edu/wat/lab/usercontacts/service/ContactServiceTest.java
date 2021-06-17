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

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        user.setContacts(new ArrayList<Contact>());
        user.setUserId(123L);
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
    public void testPostContact() {
        assertThrows(BadRequestException.class, () -> this.contactService.postContact(123L, new ContactRequest()));
        assertThrows(BadRequestException.class, () -> this.contactService.postContact(0L, new ContactRequest()));
    }

    @Test
    public void testPostContact10() {
        ContactRequest contactRequest = mock(ContactRequest.class);
        when(contactRequest.getEmailAddress()).thenReturn("  U@UUU ");
        when(contactRequest.getPhoneNumber()).thenReturn("foo");
        when(contactRequest.getName()).thenReturn(" \\U\\U\\U.\\U\\U\\U.\\U\\U\\U");
        assertThrows(BadRequestException.class, () -> this.contactService.postContact(123L, contactRequest));
        verify(contactRequest, times(2)).getEmailAddress();
        verify(contactRequest, times(3)).getName();
        verify(contactRequest).getPhoneNumber();
    }

    @Test
    public void testPostContact11() {
        ContactRequest contactRequest = mock(ContactRequest.class);
        when(contactRequest.getEmailAddress()).thenReturn("  U@UUU ");
        when(contactRequest.getPhoneNumber()).thenReturn("foo");
        when(contactRequest.getName()).thenReturn("[U]");
        assertThrows(BadRequestException.class, () -> this.contactService.postContact(123L, contactRequest));
        verify(contactRequest, times(2)).getEmailAddress();
        verify(contactRequest, times(3)).getName();
        verify(contactRequest).getPhoneNumber();
    }

    @Test
    public void testPostContact12() {
        ContactRequest contactRequest = mock(ContactRequest.class);
        when(contactRequest.getEmailAddress()).thenReturn("  U@UUU ");
        when(contactRequest.getPhoneNumber()).thenReturn("foo");
        when(contactRequest.getName()).thenReturn("U.U.U.U");
        assertThrows(BadRequestException.class, () -> this.contactService.postContact(123L, contactRequest));
        verify(contactRequest, times(2)).getEmailAddress();
        verify(contactRequest, times(3)).getName();
        verify(contactRequest).getPhoneNumber();
    }

    @Test
    public void testPostContact2() {
        ContactRequest contactRequest = mock(ContactRequest.class);
        when(contactRequest.getEmailAddress()).thenReturn("foo");
        when(contactRequest.getPhoneNumber()).thenReturn("foo");
        when(contactRequest.getName()).thenReturn("foo");
        assertThrows(BadRequestException.class, () -> this.contactService.postContact(123L, contactRequest));
        verify(contactRequest, times(2)).getEmailAddress();
        verify(contactRequest, times(3)).getName();
        verify(contactRequest).getPhoneNumber();
    }

    @Test
    public void testPostContact3() {
        ContactRequest contactRequest = mock(ContactRequest.class);
        when(contactRequest.getEmailAddress()).thenReturn("foo");
        when(contactRequest.getPhoneNumber()).thenReturn("foo");
        when(contactRequest.getName()).thenReturn("foo");
        assertThrows(BadRequestException.class, () -> this.contactService.postContact(1L, contactRequest));
        verify(contactRequest, times(2)).getEmailAddress();
        verify(contactRequest, times(3)).getName();
        verify(contactRequest).getPhoneNumber();
    }

    @Test
    public void testPostContact4() {
        ContactRequest contactRequest = mock(ContactRequest.class);
        when(contactRequest.getEmailAddress()).thenReturn("  U@UUU ");
        when(contactRequest.getPhoneNumber()).thenReturn("foo");
        when(contactRequest.getName()).thenReturn("foo");
        assertThrows(BadRequestException.class, () -> this.contactService.postContact(123L, contactRequest));
        verify(contactRequest, times(2)).getEmailAddress();
        verify(contactRequest, times(3)).getName();
        verify(contactRequest).getPhoneNumber();
    }

    @Test
    public void testPostContact5() {
        ContactRequest contactRequest = mock(ContactRequest.class);
        when(contactRequest.getEmailAddress()).thenReturn("foo");
        when(contactRequest.getPhoneNumber()).thenReturn("foo");
        when(contactRequest.getName()).thenReturn("UUU");
        assertThrows(BadRequestException.class, () -> this.contactService.postContact(123L, contactRequest));
        verify(contactRequest, times(2)).getEmailAddress();
        verify(contactRequest, times(3)).getName();
        verify(contactRequest).getPhoneNumber();
    }

    @Test
    public void testPostContact6() {
        ContactRequest contactRequest = mock(ContactRequest.class);
        when(contactRequest.getEmailAddress()).thenReturn("foo");
        when(contactRequest.getPhoneNumber()).thenReturn("foo");
        when(contactRequest.getName()).thenReturn("  U@UUU ");
        assertThrows(BadRequestException.class, () -> this.contactService.postContact(123L, contactRequest));
        verify(contactRequest, times(2)).getEmailAddress();
        verify(contactRequest, times(3)).getName();
        verify(contactRequest).getPhoneNumber();
    }

    @Test
    public void testPostContact7() {
        ContactRequest contactRequest = mock(ContactRequest.class);
        when(contactRequest.getEmailAddress()).thenReturn("foo");
        when(contactRequest.getPhoneNumber()).thenReturn("foo");
        when(contactRequest.getName()).thenReturn("");
        assertThrows(BadRequestException.class, () -> this.contactService.postContact(123L, contactRequest));
        verify(contactRequest, times(2)).getName();
    }

    @Test
    public void testPostContact8() {
        ContactRequest contactRequest = mock(ContactRequest.class);
        when(contactRequest.getEmailAddress()).thenReturn("foo");
        when(contactRequest.getPhoneNumber()).thenReturn("foo");
        when(contactRequest.getName()).thenReturn(".*\\w.*");
        assertThrows(BadRequestException.class, () -> this.contactService.postContact(123L, contactRequest));
        verify(contactRequest, times(2)).getEmailAddress();
        verify(contactRequest, times(3)).getName();
        verify(contactRequest).getPhoneNumber();
    }

    @Test
    public void testPostContact9() {
        ContactRequest contactRequest = mock(ContactRequest.class);
        when(contactRequest.getEmailAddress()).thenReturn("foo");
        when(contactRequest.getPhoneNumber()).thenReturn("foo");
        when(contactRequest.getName()).thenReturn("42");
        assertThrows(BadRequestException.class, () -> this.contactService.postContact(123L, contactRequest));
        verify(contactRequest, times(2)).getEmailAddress();
        verify(contactRequest, times(3)).getName();
        verify(contactRequest).getPhoneNumber();
    }

    @Test
    public void testUpdateContact() {
        User user = new User();
        user.setName("Name");
        user.setContacts(new ArrayList<Contact>());
        user.setUserId(123L);

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
        User user = new User();
        user.setName("Name");
        user.setContacts(new ArrayList<Contact>());
        user.setUserId(123L);

        Contact contact = new Contact();
        contact.setContactId(123L);
        contact.setName("Name");
        contact.setPhoneNumber("4105551212");
        contact.setUser(user);
        contact.setEmailAddress("42 Main St");

        ArrayList<Contact> contactList = new ArrayList<Contact>();
        contactList.add(contact);

        User user1 = new User();
        user1.setName("Name");
        user1.setContacts(contactList);
        user1.setUserId(123L);

        Contact contact1 = new Contact();
        contact1.setContactId(123L);
        contact1.setName("Name");
        contact1.setPhoneNumber("4105551212");
        contact1.setUser(user1);
        contact1.setEmailAddress("42 Main St");
        Optional<Contact> ofResult = Optional.of(contact1);
        when(this.contactRepository.findById(any())).thenReturn(ofResult);
        assertThrows(BadRequestException.class, () -> this.contactService.updateContact(123L, new ContactRequest()));
        verify(this.contactRepository).findById(any());
    }

    @Test
    public void testUpdateContact3() {
        User user = new User();
        user.setName("Name");
        user.setContacts(new ArrayList<Contact>());
        user.setUserId(123L);

        Contact contact = new Contact();
        contact.setContactId(123L);
        contact.setName("Name");
        contact.setPhoneNumber("4105551212");
        contact.setUser(user);
        contact.setEmailAddress("42 Main St");

        User user1 = new User();
        user1.setName("Name");
        user1.setContacts(new ArrayList<Contact>());
        user1.setUserId(123L);

        Contact contact1 = new Contact();
        contact1.setContactId(123L);
        contact1.setName("Name");
        contact1.setPhoneNumber("4105551212");
        contact1.setUser(user1);
        contact1.setEmailAddress("42 Main St");

        ArrayList<Contact> contactList = new ArrayList<Contact>();
        contactList.add(contact1);
        contactList.add(contact);

        User user2 = new User();
        user2.setName("Name");
        user2.setContacts(contactList);
        user2.setUserId(123L);

        Contact contact2 = new Contact();
        contact2.setContactId(123L);
        contact2.setName("Name");
        contact2.setPhoneNumber("4105551212");
        contact2.setUser(user2);
        contact2.setEmailAddress("42 Main St");
        Optional<Contact> ofResult = Optional.of(contact2);
        when(this.contactRepository.findById(any())).thenReturn(ofResult);
        assertThrows(BadRequestException.class, () -> this.contactService.updateContact(123L, new ContactRequest()));
        verify(this.contactRepository).findById(any());
    }

    @Test
    public void testUpdateContact4() {
        when(this.contactRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(ContactNotFoundException.class, () -> this.contactService.updateContact(123L, new ContactRequest()));
        verify(this.contactRepository).findById(any());
    }

    @Test
    public void testUpdateContact5() {
        User user = new User();
        user.setName("Name");
        user.setContacts(new ArrayList<Contact>());
        user.setUserId(123L);

        Contact contact = new Contact();
        contact.setContactId(123L);
        contact.setName("Name");
        contact.setPhoneNumber("4105551212");
        contact.setUser(user);
        contact.setEmailAddress("42 Main St");
        Optional<Contact> ofResult = Optional.of(contact);
        when(this.contactRepository.findById(any())).thenReturn(ofResult);
        assertThrows(BadRequestException.class, () -> this.contactService.updateContact(0L, new ContactRequest()));
        verify(this.contactRepository).findById(any());
    }

    @Test
    public void testUpdateContact6() {
        when(this.contactRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(ContactNotFoundException.class, () -> this.contactService.updateContact(123L, null));
        verify(this.contactRepository).findById(any());
    }

    @Test
    public void testDeleteContact() {
        User user = new User();
        user.setName("Name");
        user.setContacts(new ArrayList<Contact>());
        user.setUserId(123L);

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

