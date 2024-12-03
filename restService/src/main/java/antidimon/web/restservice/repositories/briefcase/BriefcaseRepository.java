package antidimon.web.restservice.repositories.briefcase;

import antidimon.web.restservice.models.entities.briefcase.Briefcase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BriefcaseRepository extends JpaRepository<Briefcase, Long> {
    @Query(value = "SELECT * FROM briefcases WHERE user_id = :user_id AND briefcase_name = :briefcase_name", nativeQuery = true)
    Optional<Briefcase> findByUserAndName(@Param("user_id") long user_id, @Param("briefcase_name") String briefcase_name);

    @Query(value = "SELECT briefcase_name FROM briefcases WHERE user_id = :user_id AND briefcase_name <> :briefcase_name", nativeQuery = true)
    List<String> findAllUserOtherBriefcaseNames(@Param("user_id") long user_id, @Param("briefcase_name") String briefcaseName);

    @Modifying
    @Query(value = "UPDATE briefcases SET briefcase_name = :newName WHERE id = :briefcase_id", nativeQuery = true)
    void updateBriefcaseName(@Param("briefcase_id") long briefcaseId, @Param("newName") String newName);

    @Modifying
    @Query(value = "UPDATE briefcases SET free_balance = :balance WHERE id = :briefcase_id", nativeQuery = true)
    void updateBriefcaseBalance(@Param("briefcase_id") long briefcaseId, @Param("balance") double balance);

    @Modifying
    @Query(value = "UPDATE briefcases SET deposited_balance = deposited_balance + :amount WHERE id = :briefcase_id", nativeQuery = true)
    void addDeposit(@Param("briefcase_id") long briefcaseId, @Param("amount") double balance);
}
