package antidimon.web.restservice.repositories.briefcase;

import antidimon.web.restservice.models.entities.briefcase.BriefcaseStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BriefcaseStockRepository extends JpaRepository<BriefcaseStock, Long> {

    @Query(value = "SELECT * FROM briefcase_stocks WHERE briefcase_id = :id AND stock_id = :stock_id", nativeQuery = true)
    Optional<BriefcaseStock> findByBriefcaseAndStock(@Param("id") long id, @Param("stock_id") long id1);
}
