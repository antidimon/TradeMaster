package antidimon.web.restservice.repositories.user;

import antidimon.web.restservice.models.entities.briefcase.BriefcaseStock;
import antidimon.web.restservice.models.entities.user.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Long> {
    Optional<MyUser> findByPhoneNumber(String phoneNumber);


    @Query(value = "SELECT bs FROM BriefcaseStock bs  " +
            "INNER JOIN bs.stock s " +
            "INNER JOIN bs.briefcase b " +
            "INNER JOIN b.owner u " +
            "WHERE u.phoneNumber = :phoneNumber AND s.name = :name")
    List<BriefcaseStock> findStocks(@Param("phoneNumber") String phoneNumber, @Param("name") String name);
}
