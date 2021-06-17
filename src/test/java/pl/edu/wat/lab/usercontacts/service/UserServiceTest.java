package pl.edu.wat.lab.usercontacts.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.edu.wat.lab.usercontacts.dto.UserRequest;
import pl.edu.wat.lab.usercontacts.exception.badrequest.BadRequestException;
import pl.edu.wat.lab.usercontacts.exception.user.UserNotFoundException;
import pl.edu.wat.lab.usercontacts.model.Contact;
import pl.edu.wat.lab.usercontacts.model.User;
import pl.edu.wat.lab.usercontacts.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UserService.class})
@ExtendWith(SpringExtension.class)
public class UserServiceTest {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void testConstructor() {
        assertTrue((new UserService(mock(UserRepository.class))).getAllUsers().isEmpty());
    }

    @Test
    public void testGetAllUsers() {
        ArrayList<User> userList = new ArrayList<User>();
        when(this.userRepository.findAll()).thenReturn(userList);
        List<User> actualAllUsers = this.userService.getAllUsers();
        assertSame(userList, actualAllUsers);
        assertTrue(actualAllUsers.isEmpty());
        verify(this.userRepository).findAll();
    }

    @Test
    public void testPostUser() {
        assertThrows(BadRequestException.class, () -> this.userService.postUser(new UserRequest()));
    }

    @Test
    public void testPostUser2() {
        User user = new User();
        user.setName("Name");
        user.setContacts(new ArrayList<Contact>());
        user.setUserId(123L);
        when(this.userRepository.save(any())).thenReturn(user);
        when(this.userRepository.findAll()).thenReturn(new ArrayList<User>());
        UserRequest userRequest = mock(UserRequest.class);
        when(userRequest.getName()).thenReturn("foo");
        assertEquals("foo", this.userService.postUser(userRequest).getName());
        verify(this.userRepository).findAll();
        verify(this.userRepository).save(any());
        verify(userRequest, times(4)).getName();
        assertTrue(this.userService.getAllUsers().isEmpty());
    }

    @Test
    public void testPostUser3() {
        User user = new User();
        user.setName("UUU");
        user.setContacts(new ArrayList<Contact>());
        user.setUserId(123L);

        ArrayList<User> userList = new ArrayList<User>();
        userList.add(user);

        User user1 = new User();
        user1.setName("Name");
        user1.setContacts(new ArrayList<Contact>());
        user1.setUserId(123L);
        when(this.userRepository.save(any())).thenReturn(user1);
        when(this.userRepository.findAll()).thenReturn(userList);
        UserRequest userRequest = mock(UserRequest.class);
        when(userRequest.getName()).thenReturn("foo");
        assertEquals("foo", this.userService.postUser(userRequest).getName());
        verify(this.userRepository).findAll();
        verify(this.userRepository).save(any());
        verify(userRequest, times(5)).getName();
        assertEquals(1, this.userService.getAllUsers().size());
    }

    @Test
    public void testPostUser4() {
        User user = new User();
        user.setName("UUU");
        user.setContacts(new ArrayList<Contact>());
        user.setUserId(123L);

        User user1 = new User();
        user1.setName("UUU");
        user1.setContacts(new ArrayList<Contact>());
        user1.setUserId(123L);

        ArrayList<User> userList = new ArrayList<User>();
        userList.add(user1);
        userList.add(user);

        User user2 = new User();
        user2.setName("Name");
        user2.setContacts(new ArrayList<Contact>());
        user2.setUserId(123L);
        when(this.userRepository.save(any())).thenReturn(user2);
        when(this.userRepository.findAll()).thenReturn(userList);
        UserRequest userRequest = mock(UserRequest.class);
        when(userRequest.getName()).thenReturn("foo");
        assertEquals("foo", this.userService.postUser(userRequest).getName());
        verify(this.userRepository).findAll();
        verify(this.userRepository).save(any());
        verify(userRequest, times(6)).getName();
        assertEquals(2, this.userService.getAllUsers().size());
    }

    @Test
    public void testPostUser5() {
        User user = new User();
        user.setName("Name");
        user.setContacts(new ArrayList<Contact>());
        user.setUserId(123L);
        when(this.userRepository.save(any())).thenReturn(user);
        when(this.userRepository.findAll()).thenReturn(new ArrayList<User>());
        UserRequest userRequest = mock(UserRequest.class);
        when(userRequest.getName()).thenReturn("UUU");
        assertEquals("UUU", this.userService.postUser(userRequest).getName());
        verify(this.userRepository).findAll();
        verify(this.userRepository).save(any());
        verify(userRequest, times(4)).getName();
        assertTrue(this.userService.getAllUsers().isEmpty());
    }

    @Test
    public void testPostUser6() {
        User user = new User();
        user.setName("Name");
        user.setContacts(new ArrayList<Contact>());
        user.setUserId(123L);
        when(this.userRepository.save(any())).thenReturn(user);
        when(this.userRepository.findAll()).thenReturn(new ArrayList<User>());
        UserRequest userRequest = mock(UserRequest.class);
        when(userRequest.getName()).thenReturn("");
        assertThrows(BadRequestException.class, () -> this.userService.postUser(userRequest));
        verify(userRequest, times(2)).getName();
    }

    @Test
    public void testPostUser7() {
        User user = new User();
        user.setName("Name");
        user.setContacts(new ArrayList<Contact>());
        user.setUserId(123L);
        when(this.userRepository.save(any())).thenReturn(user);
        when(this.userRepository.findAll()).thenReturn(new ArrayList<User>());
        UserRequest userRequest = mock(UserRequest.class);
        when(userRequest.getName()).thenReturn(".*\\w.*");
        assertEquals(".*\\w.*", this.userService.postUser(userRequest).getName());
        verify(this.userRepository).findAll();
        verify(this.userRepository).save(any());
        verify(userRequest, times(4)).getName();
        assertTrue(this.userService.getAllUsers().isEmpty());
    }

    @Test
    public void testPostUser8() {
        User user = new User();
        user.setName("Name");
        user.setContacts(new ArrayList<Contact>());
        user.setUserId(123L);
        when(this.userRepository.save(any())).thenReturn(user);
        when(this.userRepository.findAll()).thenReturn(new ArrayList<User>());
        UserRequest userRequest = mock(UserRequest.class);
        when(userRequest.getName()).thenReturn("42");
        assertEquals("42", this.userService.postUser(userRequest).getName());
        verify(this.userRepository).findAll();
        verify(this.userRepository).save(any());
        verify(userRequest, times(4)).getName();
        assertTrue(this.userService.getAllUsers().isEmpty());
    }

    @Test
    public void testPostUser9() {
        User user = new User();
        user.setName("UUU");
        user.setContacts(new ArrayList<Contact>());
        user.setUserId(123L);

        ArrayList<User> userList = new ArrayList<User>();
        userList.add(user);

        User user1 = new User();
        user1.setName("Name");
        user1.setContacts(new ArrayList<Contact>());
        user1.setUserId(123L);
        when(this.userRepository.save(any())).thenReturn(user1);
        when(this.userRepository.findAll()).thenReturn(userList);
        UserRequest userRequest = mock(UserRequest.class);
        when(userRequest.getName()).thenReturn("UUU");
        assertThrows(BadRequestException.class, () -> this.userService.postUser(userRequest));
        verify(this.userRepository).findAll();
        verify(userRequest, times(4)).getName();
    }

    @Test
    public void testUpdateUser() {
        when(this.userRepository.findAll()).thenReturn(new ArrayList<User>());
        assertThrows(BadRequestException.class, () -> this.userService.updateUser(123L, new UserRequest()));
        verify(this.userRepository).findAll();
    }

    @Test
    public void testUpdateUser2() {
        User user = new User();
        user.setName("Name");
        user.setContacts(new ArrayList<Contact>());
        user.setUserId(123L);

        ArrayList<User> userList = new ArrayList<User>();
        userList.add(user);
        when(this.userRepository.findAll()).thenReturn(userList);
        assertThrows(BadRequestException.class, () -> this.userService.updateUser(123L, new UserRequest()));
        verify(this.userRepository).findAll();
    }

    @Test
    public void testUpdateUser3() {
        User user = new User();
        user.setName("Name");
        user.setContacts(new ArrayList<Contact>());
        user.setUserId(123L);

        User user1 = new User();
        user1.setName("Name");
        user1.setContacts(new ArrayList<Contact>());
        user1.setUserId(123L);

        ArrayList<User> userList = new ArrayList<User>();
        userList.add(user1);
        userList.add(user);
        when(this.userRepository.findAll()).thenReturn(userList);
        assertThrows(BadRequestException.class, () -> this.userService.updateUser(123L, new UserRequest()));
        verify(this.userRepository).findAll();
    }

    @Test
    public void testDeleteUser() {
        User user = new User();
        user.setName("Name");
        user.setContacts(new ArrayList<Contact>());
        user.setUserId(123L);
        Optional<User> ofResult = Optional.of(user);
        doNothing().when(this.userRepository).delete(any());
        when(this.userRepository.findById(any())).thenReturn(ofResult);
        this.userService.deleteUser(123L);
        verify(this.userRepository).delete(any());
        verify(this.userRepository).findById(any());
        assertTrue(this.userService.getAllUsers().isEmpty());
    }

    @Test
    public void testDeleteUser2() {
        doNothing().when(this.userRepository).delete(any());
        when(this.userRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> this.userService.deleteUser(123L));
        verify(this.userRepository).findById(any());
    }
}

