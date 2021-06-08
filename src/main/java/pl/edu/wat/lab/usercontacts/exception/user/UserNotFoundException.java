package pl.edu.wat.lab.usercontacts.exception.user;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long userId) {
        super("Could not find user with id = " + userId);
    }

    public UserNotFoundException(String name) {
        super("Could not find user with name = " + name);
    }
}
