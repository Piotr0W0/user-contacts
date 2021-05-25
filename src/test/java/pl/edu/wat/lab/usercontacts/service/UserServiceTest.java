package pl.edu.wat.lab.usercontacts.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import pl.edu.wat.lab.usercontacts.dto.user.UserRequest;
import pl.edu.wat.lab.usercontacts.dto.user.UserResponse;
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
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
    }

    @Test
    public void testGetAllUsers() {
        when(this.userRepository.findAll()).thenReturn(new ArrayList<User>());
        assertTrue(this.userService.getAllUsers().isEmpty());
        verify(this.userRepository).findAll();
    }

    @Test
    public void testGetAllUsers2() {
        User user = new User();
        user.setName("Name");
        user.setUserId(123);
        user.setContacts(new HashSet<Contact>());

        ArrayList<User> userList = new ArrayList<User>();
        userList.add(user);
        when(this.userRepository.findAll()).thenReturn(userList);
        List<UserResponse> actualAllUsers = this.userService.getAllUsers();
        assertEquals(1, actualAllUsers.size());
        UserResponse getResult = actualAllUsers.get(0);
        assertEquals(123, getResult.getId());
        assertEquals("Name", getResult.getName());
        verify(this.userRepository).findAll();
    }

    @Test
    public void testGetAllUsers3() {
        User user = new User();
        user.setName("Name");
        user.setUserId(123);
        user.setContacts(new HashSet<Contact>());

        User user1 = new User();
        user1.setName("Name");
        user1.setUserId(123);
        user1.setContacts(new HashSet<Contact>());

        ArrayList<User> userList = new ArrayList<User>();
        userList.add(user1);
        userList.add(user);
        when(this.userRepository.findAll()).thenReturn(userList);
        List<UserResponse> actualAllUsers = this.userService.getAllUsers();
        assertEquals(2, actualAllUsers.size());
        UserResponse getResult = actualAllUsers.get(0);
        assertEquals("Name", getResult.getName());
        UserResponse getResult1 = actualAllUsers.get(1);
        assertEquals("Name", getResult1.getName());
        assertEquals(123, getResult1.getId());
        assertEquals(123, getResult.getId());
        verify(this.userRepository).findAll();
    }

    @Test
    public void testGetUser() {
        User user = new User();
        user.setName("Name");
        user.setUserId(123);
        user.setContacts(new HashSet<Contact>());
        Optional<User> ofResult = Optional.of(user);
        when(this.userRepository.findById(any())).thenReturn(ofResult);
        UserResponse actualUser = this.userService.getUser(123);
        assertEquals(123, actualUser.getId());
        assertEquals("Name", actualUser.getName());
        verify(this.userRepository).findById(any());
    }

    @Test
    public void testGetUser2() {
        when(this.userRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> this.userService.getUser(123));
        verify(this.userRepository).findById(any());
    }

    @Test
    public void testPostUser() {
        User user = new User();
        user.setName("Name");
        user.setUserId(123);
        user.setContacts(new HashSet<Contact>());
        when(this.userRepository.save(any())).thenReturn(user);
        Optional<User> actualPostUserResult = this.userService.postUser(new UserRequest("Name"));
        assertTrue(actualPostUserResult.isPresent());
        assertEquals("Name", actualPostUserResult.get().getName());
        verify(this.userRepository).save(any());
    }

    @Test
    public void testPostUser2() {
        User user = new User();
        user.setName("Name");
        user.setUserId(123);
        user.setContacts(new HashSet<Contact>());
        when(this.userRepository.save(any())).thenReturn(user);
        assertFalse(this.userService.postUser(new UserRequest()).isPresent());
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setName("Name");
        user.setUserId(123);
        user.setContacts(new HashSet<Contact>());
        Optional<User> ofResult = Optional.of(user);

        User user1 = new User();
        user1.setName("Name");
        user1.setUserId(123);
        user1.setContacts(new HashSet<Contact>());
        when(this.userRepository.save(any())).thenReturn(user1);
        when(this.userRepository.findById(any())).thenReturn(ofResult);
        Optional<User> actualUpdateUserResult = this.userService.updateUser(123, new UserRequest("Name"));
        assertTrue(actualUpdateUserResult.isPresent());
        assertEquals("Name", actualUpdateUserResult.get().getName());
        verify(this.userRepository).save(any());
        verify(this.userRepository).findById(any());
    }

    @Test
    public void testUpdateUser2() {
        User user = new User();
        user.setName("Name");
        user.setUserId(123);
        user.setContacts(new HashSet<Contact>());
        when(this.userRepository.save(any())).thenReturn(user);
        when(this.userRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> this.userService.updateUser(123, new UserRequest("Name")));
        verify(this.userRepository).findById(any());
    }

    @Test
    public void testDeleteUser() {
        User user = new User();
        user.setName("Name");
        user.setUserId(123);
        user.setContacts(new HashSet<Contact>());
        Optional<User> ofResult = Optional.of(user);
        doNothing().when(this.userRepository).delete(any());
        when(this.userRepository.findById(any())).thenReturn(ofResult);
        this.userService.deleteUser(123);
        verify(this.userRepository).delete(any());
        verify(this.userRepository).findById(any());
    }

    @Test
    public void testDeleteUser2() {
        doNothing().when(this.userRepository).delete(any());
        when(this.userRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> this.userService.deleteUser(123));
        verify(this.userRepository).findById(any());
    }
}