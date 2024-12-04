package antidimon.web.restservice.repositories.stock;

import antidimon.web.restservice.models.dto.output.stock.PriceOutputDTO;
import antidimon.web.restservice.models.dto.output.stock.StockOutputDTO;
import antidimon.web.restservice.models.entities.stock.Stock;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    @Query(value = "SELECT DISTINCT ON (name) * FROM stocks WHERE price BETWEEN :from AND :to ORDER BY name, getted_at DESC", nativeQuery = true)
    List<Stock> findAllUnique(@Param("from") double from, @Param("to") double to);

    @Query(value = "SELECT DISTINCT ON (name) * FROM stocks WHERE name LIKE :name AND price BETWEEN :from AND :to ORDER BY name, getted_at DESC", nativeQuery = true)
    List<Stock> findAllUniqueByName(@Param("name") String name, @Param("from") double from, @Param("to") double to);

    @Query(value = "SELECT * FROM get_actual_stock(:name)", nativeQuery = true)
    Stock findLastByName(@Param("name") String name);

    @Query(value = "SELECT price, getted_at FROM stocks WHERE name = :name ORDER BY getted_at", nativeQuery = true)
    List<Tuple> getPrices(@Param("name") String name);
}
