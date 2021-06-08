package pl.edu.wat.lab.usercontacts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.lab.usercontacts.dto.UserRequest;
import pl.edu.wat.lab.usercontacts.exception.badrequest.BadRequestException;
import pl.edu.wat.lab.usercontacts.exception.user.UserNotFoundException;
import pl.edu.wat.lab.usercontacts.model.User;
import pl.edu.wat.lab.usercontacts.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long userId) {
        User user = findUser(userId);
        return user;
    }

    public User postUser(UserRequest userRequest) {
        if (!userRequest.hasInvalidAttributes()) {
            User user = new User();
            user.setName(userRequest.getName());
            userRepository.save(user);
            return user;
        }
        throw new BadRequestException("Bad request data: " + userRequest);
    }

    public User updateUser(Long userId, UserRequest userRequest) {
        User user = findUser(userId);
        user.setName(userRequest.getName());
        userRepository.save(user);
        return user;
    }

    public void deleteUser(Long userId) {
        User user = findUser(userId);
        userRepository.delete(user);
    }

    private User findUser(Long userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
