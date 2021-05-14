package pl.edu.wat.lab.usercontacts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.lab.usercontacts.dto.contact.ContactResponse;
import pl.edu.wat.lab.usercontacts.dto.user.UserRequest;
import pl.edu.wat.lab.usercontacts.dto.user.UserResponse;
import pl.edu.wat.lab.usercontacts.model.User;
import pl.edu.wat.lab.usercontacts.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("filtered")
    public ResponseEntity<Page<UserResponse>> getFilteredUsers(@RequestParam(value = "name", required = false) String name,
                                                                     @RequestParam(value = "page", defaultValue = "0") int page,
                                                                     @RequestParam(value = "size", defaultValue = "10") int size) {
        return new ResponseEntity<>(userService.getFilteredUsers(name, page, size), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable int userId) {
        return new ResponseEntity<>(userService.getUser(userId), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<String> postUser(@RequestBody UserRequest userRequest) {
        Optional<User> user = userService.postUser(userRequest);
        if (user.isPresent()) {
            return new ResponseEntity<>("User was created", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable int userId, @RequestBody UserRequest userRequest) {
        return userService
                .updateUser(userId, userRequest)
                .map(response -> new ResponseEntity<>(response, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable int userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>("User was deleted", HttpStatus.OK);
    }

}
