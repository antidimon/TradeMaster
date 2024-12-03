package antidimon.web.mvcservice.repositories;

import antidimon.web.mvcservice.security.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Long> {
    Optional<MyUser> findByPhoneNumber(String phoneNumber);
    Optional<MyUser> findByEmail(String email);
    Optional<MyUser> findByPassportDetails(String passportDetails);
}
