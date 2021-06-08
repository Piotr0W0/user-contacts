package pl.edu.wat.lab.usercontacts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.validator.routines.EmailValidator;

@Getter
@Setter
@NoArgsConstructor
public class ContactRequest {
    private String name;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("email_address")
    private String emailAddress;

    public boolean hasInvalidAttributes() {
        return name == null || phoneNumber == null || emailAddress == null || !EmailValidator.getInstance(true).isValid(emailAddress) ||
                !phoneNumber.matches("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
                        + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
                        + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$");
    }

    @Override
    public String toString() {
        return "ContactRequest { " +
                "name = " + name +
                ", phone_number = " + phoneNumber +
                ", email_address = " + emailAddress +
                " } ";
    }
}