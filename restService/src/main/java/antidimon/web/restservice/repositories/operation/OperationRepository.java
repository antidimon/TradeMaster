package antidimon.web.restservice.repositories.operation;

import antidimon.web.restservice.models.dto.output.operation.OperationDetailsProjection;
import antidimon.web.restservice.models.entities.operation.Operation;
import antidimon.web.restservice.models.dto.output.operation.OperationDetails;
import antidimon.web.restservice.models.entities.operation.OperationStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {

    @Modifying
    @Query(value = "UPDATE operations SET status_id = 3 WHERE id = :operation_id", nativeQuery = true)
    void doOperation(@Param("operation_id") long operationId);


    @Query(value = "SELECT os FROM OperationStatusEntity os JOIN Operation o ON os.id = o.operationStatus.id WHERE o.id = :operation_id")
    OperationStatusEntity findOperationStatus(@Param("operation_id") long operationId);

    @Query(value = "SELECT * FROM operations WHERE id=:id", nativeQuery = true)
    List<Object[]> findOperation(@Param("id") long id);


    @Query(value = "SELECT o FROM Operation o WHERE o.id=:id")
    Map<String, String> findOperationMap(@Param("id") long id);

    @Query(value = "SELECT * FROM operation_details WHERE phone_number = :phoneNumber AND briefcase_name = :briefcase", nativeQuery = true)
    List<OperationDetailsProjection> getOperationsFromView(@Param("phoneNumber") String phoneNumber, @Param("briefcase") String briefcaseName);
}
