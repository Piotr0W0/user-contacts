package pl.edu.wat.lab.usercontacts.exception.contact;

public class ContactForUserNotFoundException extends RuntimeException {

    public ContactForUserNotFoundException(int contactId, int userId) {
        super("Could not find contact with id = " + contactId + " for user contacts set with id = " + userId);
    }
}
