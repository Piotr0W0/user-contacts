package pl.edu.wat.lab.usercontacts.validation;

import org.apache.commons.validator.routines.EmailValidator;
import pl.edu.wat.lab.usercontacts.dto.ContactRequest;
import pl.edu.wat.lab.usercontacts.dto.UserRequest;

public class Validator {
    public static boolean checkId(Long id) {
        return !(id == null || id <= 0);
    }

    public static boolean checkContactAttributes(ContactRequest contactRequest) {
        return !(contactRequest.getName() == null ||
                contactRequest.getName().equals("") ||
                !contactRequest.getName().matches(".*\\w.*") ||
                contactRequest.getPhoneNumber() == null ||
                contactRequest.getEmailAddress() == null ||
                !EmailValidator.getInstance().isValid(contactRequest.getEmailAddress()) ||
                !contactRequest.getPhoneNumber().matches("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
                        + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
                        + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$")
        );
    }

    public static boolean checkUserAttributes(UserRequest userRequest) {
        return !(userRequest.getName() == null ||
                userRequest.getName().equals("") ||
                !userRequest.getName().matches(".*\\w.*")
        );
    }

}
