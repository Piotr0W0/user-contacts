package pl.edu.wat.lab.usercontacts.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import pl.edu.wat.lab.usercontacts.model.Contact;
import pl.edu.wat.lab.usercontacts.model.User;

import java.util.HashSet;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ContextConfiguration(classes = {UserRepository.class})
@EnableAutoConfiguration
@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testStreamAllBy() {
        User user = new User();
        user.setName("Name");
        user.setUserId(123);
        user.setContacts(new HashSet<Contact>());

        User user1 = new User();
        user1.setName("Name");
        user1.setUserId(123);
        user1.setContacts(new HashSet<Contact>());
        this.userRepository.<User>save(user);
        this.userRepository.<User>save(user1);
        Stream<User> actualStreamAllByResult = this.userRepository.streamAllBy();
        assertTrue(actualStreamAllByResult.distinct() instanceof org.hibernate.query.spi.StreamDecorator);
        assertFalse(actualStreamAllByResult.isParallel());
    }
}

