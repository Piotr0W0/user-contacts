package pl.edu.wat.lab.usercontacts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.lab.usercontacts.dto.user.UserRequest;
import pl.edu.wat.lab.usercontacts.dto.user.UserResponse;
import pl.edu.wat.lab.usercontacts.exception.UserNotFoundException;
import pl.edu.wat.lab.usercontacts.model.User;
import pl.edu.wat.lab.usercontacts.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponse(user.getUserId(), user.getName()))
                .collect(Collectors.toList());
    }

    public UserResponse getUser(int userId) {
        Optional<UserResponse> userResponse = userRepository.findById(userId)
                .stream()
                .map(user -> new UserResponse(user.getUserId(), user.getName()))
                .findFirst();
        if (userResponse.isEmpty()) {
            throw new UserNotFoundException(userId);
        } else {
            return userResponse.get();
        }
    }

    public Optional<User> postUser(UserRequest userRequest) {
        if (!userRequest.hasInvalidAttributes()) {
            User user = new User();
            user.setName(userRequest.getName());
            userRepository.save(user);
            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    }

    public Optional<User> updateUser(int userId, UserRequest userRequest) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        user.setName(userRequest.getName());
        userRepository.save(user);
        return Optional.of(user);
    }

    public void deleteUser(int userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        userRepository.delete(user);
    }
}
