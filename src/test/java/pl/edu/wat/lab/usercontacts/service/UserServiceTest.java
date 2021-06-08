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
import java.util.HashSet;
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
    public void testGetAllUsers() {
        ArrayList<User> userList = new ArrayList<User>();
        when(this.userRepository.findAll()).thenReturn(userList);
        List<User> actualAllUsers = this.userService.getAllUsers();
        assertSame(userList, actualAllUsers);
        assertTrue(actualAllUsers.isEmpty());
        verify(this.userRepository).findAll();
    }

    @Test
    public void testGetUser() {
        User user = new User();
        user.setName("Name");
        user.setUserId(123L);
        user.setContacts(new HashSet<Contact>());
        Optional<User> ofResult = Optional.of(user);
        when(this.userRepository.findById(any())).thenReturn(ofResult);
        assertSame(user, this.userService.getUser(123L));
        verify(this.userRepository).findById(any());
        assertTrue(this.userService.getAllUsers().isEmpty());
    }

    @Test
    public void testGetUser2() {
        when(this.userRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> this.userService.getUser(123L));
        verify(this.userRepository).findById(any());
    }

    @Test
    public void testPostUser() {
        assertThrows(BadRequestException.class, () -> this.userService.postUser(new UserRequest()));
    }

    @Test
    public void testPostUser2() {
        UserRequest userRequest = mock(UserRequest.class);
        when(userRequest.hasInvalidAttributes()).thenReturn(true);
        assertThrows(BadRequestException.class, () -> this.userService.postUser(userRequest));
        verify(userRequest).hasInvalidAttributes();
    }

    @Test
    public void testPostUser3() {
        User user = new User();
        user.setName("Name");
        user.setUserId(123L);
        user.setContacts(new HashSet<Contact>());
        when(this.userRepository.save(any())).thenReturn(user);
        UserRequest userRequest = mock(UserRequest.class);
        when(userRequest.getName()).thenReturn("foo");
        when(userRequest.hasInvalidAttributes()).thenReturn(false);
        assertEquals("foo", this.userService.postUser(userRequest).getName());
        verify(this.userRepository).save(any());
        verify(userRequest).getName();
        verify(userRequest).hasInvalidAttributes();
        assertTrue(this.userService.getAllUsers().isEmpty());
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setName("Name");
        user.setUserId(123L);
        user.setContacts(new HashSet<Contact>());
        Optional<User> ofResult = Optional.of(user);

        User user1 = new User();
        user1.setName("Name");
        user1.setUserId(123L);
        user1.setContacts(new HashSet<Contact>());
        when(this.userRepository.save(any())).thenReturn(user1);
        when(this.userRepository.findById(any())).thenReturn(ofResult);
        User actualUpdateUserResult = this.userService.updateUser(123L, new UserRequest());
        assertSame(user, actualUpdateUserResult);
        assertNull(actualUpdateUserResult.getName());
        verify(this.userRepository).findById(any());
        verify(this.userRepository).save(any());
        assertTrue(this.userService.getAllUsers().isEmpty());
    }

    @Test
    public void testUpdateUser2() {
        User user = new User();
        user.setName("Name");
        user.setUserId(123L);
        user.setContacts(new HashSet<Contact>());
        when(this.userRepository.save(any())).thenReturn(user);
        when(this.userRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> this.userService.updateUser(123L, new UserRequest()));
        verify(this.userRepository).findById(any());
    }

    @Test
    public void testDeleteUser() {
        User user = new User();
        user.setName("Name");
        user.setUserId(123L);
        user.setContacts(new HashSet<Contact>());
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

