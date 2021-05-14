package pl.edu.wat.lab.usercontacts.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import pl.edu.wat.lab.usercontacts.dto.contact.ContactRequest;
import pl.edu.wat.lab.usercontacts.dto.contact.ContactResponse;
import pl.edu.wat.lab.usercontacts.model.Contact;

@Mapper(componentModel = "spring")
@Repository
public interface ContactMapper {
    Contact mapToContact(ContactRequest contactRequest);

    ContactResponse mapToContactResponse(Contact contact);
}
