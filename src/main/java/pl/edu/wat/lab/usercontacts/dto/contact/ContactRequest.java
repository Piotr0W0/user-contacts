package pl.edu.wat.lab.usercontacts.dto.contact;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactRequest {
    private String name;
    private String phoneNumber;

    public boolean hasInvalidAttributes() {
        return name == null || phoneNumber == null;
    }
}
