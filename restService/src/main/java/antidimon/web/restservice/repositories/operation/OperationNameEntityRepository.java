package antidimon.web.restservice.repositories.operation;

import antidimon.web.restservice.models.entities.operation.OperationNameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationNameEntityRepository extends JpaRepository<OperationNameEntity, Short> {
    @Query(value = "SELECT * FROM operation_names WHERE name = :name", nativeQuery = true)
    OperationNameEntity findByName(@Param("name") String name);
}
