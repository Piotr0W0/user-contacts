package pl.edu.wat.lab.usercontacts.validation;

import org.apache.commons.validator.routines.EmailValidator;
import pl.edu.wat.lab.usercontacts.dto.ContactRequest;

public class Validator {
    public static boolean checkId(Long id) {
        return !(id == null || id <= 0);
    }

    public static boolean checkAttributes(ContactRequest contactRequest) {
        return !(contactRequest.getName() == null ||
                contactRequest.getPhoneNumber() == null ||
                contactRequest.getEmailAddress() == null ||
                EmailValidator.getInstance().isValid(contactRequest.getEmailAddress()) ||
                !contactRequest.getPhoneNumber().matches("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
                        + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
                        + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$"));
    }
}
