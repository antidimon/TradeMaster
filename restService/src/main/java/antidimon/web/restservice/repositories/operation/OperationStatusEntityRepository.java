package antidimon.web.restservice.repositories.operation;

import antidimon.web.restservice.models.entities.operation.OperationStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationStatusEntityRepository extends JpaRepository<OperationStatusEntity, Short> {
    @Query(value = "SELECT * FROM operation_statuses WHERE status = :status", nativeQuery = true)
    OperationStatusEntity findByName(@Param("status") String status);
}
