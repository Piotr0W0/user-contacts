package pl.edu.wat.lab.usercontacts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContactRequest {
    private String name;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("email_address")
    private String emailAddress;

    @Override
    public String toString() {
        return "ContactRequest { " +
                "name = " + name +
                ", phone_number = " + phoneNumber +
                ", email_address = " + emailAddress +
                " } ";
    }
}
