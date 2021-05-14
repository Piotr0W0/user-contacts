package pl.edu.wat.lab.usercontacts.dto.contact;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactResponse {
    @JsonProperty("contact_id")
    private int id;
    private String name;
    private String phoneNumber;

}
