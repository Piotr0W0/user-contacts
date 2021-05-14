package pl.edu.wat.lab.usercontacts.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String name;

    public boolean hasInvalidAttributes() {
        return name == null;
    }
}
