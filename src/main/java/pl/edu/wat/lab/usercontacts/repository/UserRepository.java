package pl.edu.wat.lab.usercontacts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wat.lab.usercontacts.dto.user.UserRequest;
import pl.edu.wat.lab.usercontacts.model.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByName(String name);

    Stream<User> streamAllBy();
}

