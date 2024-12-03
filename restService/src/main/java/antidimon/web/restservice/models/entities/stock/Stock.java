package antidimon.web.restservice.models.entities.stock;


import antidimon.web.restservice.models.entities.briefcase.BriefcaseStock;
import antidimon.web.restservice.models.entities.operation.Operation;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "stocks")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Stock implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "getted_at")
    private LocalDateTime gettedAt;

    @Column(name = "predicted_price")
    private double predictedPrice;

    @Column(name = "stocks_per_lot", nullable = false)
    private int stocksPerLot;

    @OneToMany(mappedBy = "stock", fetch = FetchType.LAZY)
    private List<BriefcaseStock> briefcaseStocks;

    @OneToMany(mappedBy = "operationStock", fetch = FetchType.LAZY)
    private List<Operation> operationsWithStock;


    public Stock(String name, double price, int stocksPerLot) {
        this.stocksPerLot = stocksPerLot;
        this.name = name;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stock stock)) return false;
        return id == stock.id && Objects.equals(name, stock.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", gettedAt=" + gettedAt +
                ", predictedPrice=" + predictedPrice +
                ", stocksPerLot=" + stocksPerLot +
                '}';
    }
}
