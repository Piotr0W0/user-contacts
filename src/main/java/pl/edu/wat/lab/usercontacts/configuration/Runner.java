package pl.edu.wat.lab.usercontacts.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.wat.lab.usercontacts.model.Contact;
import pl.edu.wat.lab.usercontacts.model.User;
import pl.edu.wat.lab.usercontacts.repository.ContactRepository;
import pl.edu.wat.lab.usercontacts.repository.UserRepository;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
public class Runner implements CommandLineRunner {

    private static final Random random = new Random();
    private final UserRepository userRepository;
    private final ContactRepository contactRepository;

    @Autowired
    public Runner(UserRepository userRepository, ContactRepository contactRepository) {
        this.userRepository = userRepository;
        this.contactRepository = contactRepository;
    }

    private String generatePhoneNumber() {
        DecimalFormat df3 = new DecimalFormat("000");
        return df3.format((random.nextInt(7) + 1) * 100 + (random.nextInt(8) * 10) + random.nextInt(8)) + "" +
                df3.format(random.nextInt(743)) + "" +
                new DecimalFormat("0000").format(random.nextInt(10000));
    }

    private List<User> generateUsers() {
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            User user = new User();
            user.setName("user" + i);
            userRepository.save(user);
            users.add(user);
        }
        return users;
    }

    private List<Contact> generateContacts(User user) {
        List<Contact> contacts = new ArrayList<>();
        for (int i = 1; i <= random.nextInt(3) + 1; i++) {
            Contact contact = new Contact();
            contact.setName("contact" + i + "-" + user.getUserId());
            contact.setPhoneNumber(generatePhoneNumber());
            contact.setEmailAddress(contact.getName() + "@gmail.com");
            contact.setUser(user);
            contactRepository.save(contact);
        }
        return contacts;

    }

    @Override
    @Transactional
    public void run(String... args) {
//        List<User> users = generateUsers();
//        for (User user : users) {
//            user.setContacts(generateContacts(user));
//        }
    }
}
