package pl.edu.wat.lab.usercontacts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wat.lab.usercontacts.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}

