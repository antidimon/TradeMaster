package antidimon.web.restservice.models.entities.operation;


import antidimon.web.restservice.models.entities.stock.Stock;
import antidimon.web.restservice.models.entities.briefcase.Briefcase;
import antidimon.web.restservice.models.entities.user.MyUser;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "operations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Operation implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private MyUser user;

    @ManyToOne
    @JoinColumn(name = "briefcase_id", referencedColumnName = "id")
    private Briefcase operationBriefcase;

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private OperationStatusEntity operationStatus;

    @ManyToOne
    @JoinColumn(name = "operation_name_id", referencedColumnName = "id")
    private OperationNameEntity operationName;

    @ManyToOne
    @JoinColumn(name = "stock_id", referencedColumnName = "id")
    private Stock operationStock;

    @Column(name = "stocks_amount", nullable = false)
    private int stocksAmount;

    @Column(name = "stock_price")
    private double stockPrice;

    @Column(name = "stocks_per_lot")
    private int stocksPerLot;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Operation(MyUser user, Briefcase operationBriefcase, OperationStatusEntity operationStatus, OperationNameEntity operationName, Stock operationStock, int stocksAmount, double stockPrice, int stocksPerLot) {
        this.user = user;
        this.operationBriefcase = operationBriefcase;
        this.operationStatus = operationStatus;
        this.operationName = operationName;
        this.operationStock = operationStock;
        this.stocksAmount = stocksAmount;
        this.stockPrice = stockPrice;
        this.stocksPerLot = stocksPerLot;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + id +
                ", operationBriefcase=" + operationBriefcase +
                ", operationStatus=" + operationStatus +
                ", operationName=" + operationName +
                ", user=" + user +
                ", operationStock=" + operationStock +
                ", stocksAmount=" + stocksAmount +
                ", stockPrice=" + stockPrice +
                ", createdAt=" + createdAt +
                '}';
    }
}
