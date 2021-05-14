package pl.edu.wat.lab.usercontacts.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(int userId) {
        super("Could not find user with id = " + userId);
    }
}
