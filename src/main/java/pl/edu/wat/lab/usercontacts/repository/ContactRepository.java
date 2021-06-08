package pl.edu.wat.lab.usercontacts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wat.lab.usercontacts.model.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
}
