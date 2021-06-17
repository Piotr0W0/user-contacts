package pl.edu.wat.lab.usercontacts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.lab.usercontacts.dto.UserRequest;
import pl.edu.wat.lab.usercontacts.exception.badrequest.BadRequestException;
import pl.edu.wat.lab.usercontacts.exception.user.UserNotFoundException;
import pl.edu.wat.lab.usercontacts.model.User;
import pl.edu.wat.lab.usercontacts.repository.UserRepository;
import pl.edu.wat.lab.usercontacts.validation.Validator;

import java.util.List;
import java.util.Optional;

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

//    public User getUser(Long userId) {
//        User user = findUser(userId);
//        return user;
//    }

    public User postUser(UserRequest userRequest) {
        if (Validator.checkUserAttributes(userRequest) &&
                userRepository.findAll().stream().noneMatch(u -> u.getName().equals(userRequest.getName()))) {
            User user = new User();
            user.setName(userRequest.getName());
            userRepository.save(user);
            return user;
        }
        throw new BadRequestException("Bad request data: " + userRequest);
    }

    public User updateUser(Long userId, UserRequest userRequest) {
        Optional<User> optionalUser = userRepository.findAll().stream().filter(u -> u.getName().equals(userRequest.getName())).findFirst();
        if (Validator.checkUserAttributes(userRequest) && (optionalUser.isEmpty() || optionalUser.get().getUserId().equals(userId))) {
            {
                User user = findUser(userId);
                user.setName(userRequest.getName());
                userRepository.save(user);
                return user;
            }
        }
        throw new BadRequestException("Bad request data: " + userRequest);
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
