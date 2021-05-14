package pl.edu.wat.lab.usercontacts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.wat.lab.usercontacts.dto.contact.ContactResponse;
import pl.edu.wat.lab.usercontacts.dto.user.UserRequest;
import pl.edu.wat.lab.usercontacts.dto.user.UserResponse;
import pl.edu.wat.lab.usercontacts.exception.user.UserNotFoundException;
import pl.edu.wat.lab.usercontacts.model.User;
import pl.edu.wat.lab.usercontacts.repository.UserRepository;

import java.util.ArrayList;
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

    @Transactional // it's not needed
    public Page<UserResponse> getFilteredUsers(String name, int page, int size) {
        List<UserResponse> users = userRepository.streamAllBy()
                .filter(user -> name == null || name.equals(user.getName()))
                .map(user -> new UserResponse(user.getUserId(), user.getName()))
                .collect(Collectors.toList());

        List<UserResponse> pageToReturn = new ArrayList<>();
        int startIndex = page * size;
        int endIndex = startIndex + size;

        if (users.size() < endIndex) {
            endIndex = users.size();
        }

        for (int i = startIndex; i < endIndex; i++) {
            pageToReturn.add(users.get(i));
        }

        return new PageImpl<>(pageToReturn, PageRequest.of(page, size, Sort.by("name").ascending()), users.size());
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
