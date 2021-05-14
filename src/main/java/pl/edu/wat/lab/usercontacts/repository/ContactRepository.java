package pl.edu.wat.lab.usercontacts.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wat.lab.usercontacts.model.Contact;
import pl.edu.wat.lab.usercontacts.model.User;

import java.util.stream.Stream;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

    Stream<Contact> streamAllByUser(User user);

    Page<Contact> findAllByUser(User user, Pageable pageable);
}
