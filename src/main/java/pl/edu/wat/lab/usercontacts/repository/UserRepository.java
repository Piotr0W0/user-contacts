package pl.edu.wat.lab.usercontacts.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wat.lab.usercontacts.model.User;

import java.util.stream.Stream;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Stream<User> streamAllBy();

}

