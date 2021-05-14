package pl.edu.wat.lab.usercontacts.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import pl.edu.wat.lab.usercontacts.dto.user.UserRequest;
import pl.edu.wat.lab.usercontacts.dto.user.UserResponse;
import pl.edu.wat.lab.usercontacts.model.User;

@Mapper(componentModel = "spring")
@Repository
public interface UserMapper {
    User mapToUser(UserRequest userRequest);

    UserResponse mapToUserResponse(User user);
}
