package pl.edu.wat.lab.usercontacts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.lab.usercontacts.dto.UserRequest;
import pl.edu.wat.lab.usercontacts.model.User;
import pl.edu.wat.lab.usercontacts.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

//    @GetMapping("/{userId}")
//    public ResponseEntity<?> getUser(@PathVariable Long userId) {
//        User user = userService.getUser(userId);
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }

    @PostMapping
    public ResponseEntity<?> postUser(@RequestBody UserRequest userRequest) {
        User user = userService.postUser(userRequest);
        return new ResponseEntity<>("User with id " + user.getUserId() + " was created", HttpStatus.CREATED);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody UserRequest userRequest) {
        User user = userService.updateUser(userId, userRequest);
        return new ResponseEntity<>("User with id " + user.getUserId() + " was updated", HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>("User with id " + userId + " was deleted", HttpStatus.OK);
    }

}
