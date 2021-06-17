package pl.edu.wat.lab.usercontacts.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequest {
    private String name;

    @Override
    public String toString() {
        return "UserRequest { " +
                "name = " + name +
                " } ";
    }
}
