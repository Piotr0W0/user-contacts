package pl.edu.wat.lab.usercontacts.exception.contact;

public class ContactNotFoundException extends RuntimeException {

    public ContactNotFoundException(Long contactId) {
        super("Could not find contact with id = " + contactId);
    }
}
