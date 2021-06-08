package pl.edu.wat.lab.usercontacts.exception.badrequest;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
