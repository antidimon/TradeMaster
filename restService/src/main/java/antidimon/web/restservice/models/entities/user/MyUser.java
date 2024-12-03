package antidimon.web.restservice.models.entities.user;


import antidimon.web.restservice.models.entities.operation.Operation;
import antidimon.web.restservice.models.entities.briefcase.Briefcase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class MyUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "fio", nullable = false)
    private String fio;

    @Column(name = "age", nullable = false)
    private short age;

    @Column(name = "passport_details", nullable = false, unique = true)
    private String passportDetails;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "is_qualified")
    private boolean isQualified = false;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<Briefcase> briefcases;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Operation> operations;

    public MyUser(String fio, short age, String passportDetails, String phoneNumber, String email) {
        this.fio = fio;
        this.age = age;
        this.passportDetails = passportDetails;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MyUser myUser)) return false;
        return age == myUser.age && Objects.equals(fio, myUser.fio) && Objects.equals(passportDetails, myUser.passportDetails)
                && Objects.equals(phoneNumber, myUser.phoneNumber) && Objects.equals(email, myUser.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fio, age, passportDetails, phoneNumber, email);
    }

    @Override
    public String toString() {
        return "MyUser{" +
                "id=" + id +
                ", fio='" + fio + '\'' +
                ", age=" + age +
                ", passportDetails='" + passportDetails + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", isQualified=" + isQualified +
                '}';
    }
}
