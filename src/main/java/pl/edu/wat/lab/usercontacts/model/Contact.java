package pl.edu.wat.lab.usercontacts.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "contact")
@Getter
@Setter
@JsonPropertyOrder({"contactId", "name", "phoneNumber"})
public class Contact {
    @Id
    @Column(name = "contact_id", nullable = false, updatable = false)
    @JsonProperty("contact_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @JsonProperty("phone_number")
    private String phoneNumber;

    @Column(nullable = false)
    @JsonProperty("email_address")
    private String emailAddress;

    @ToString.Exclude
    @JsonBackReference
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.PERSIST})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
