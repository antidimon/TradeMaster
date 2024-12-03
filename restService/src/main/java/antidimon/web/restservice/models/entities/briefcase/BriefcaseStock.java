package antidimon.web.restservice.models.entities.briefcase;


import antidimon.web.restservice.models.entities.stock.Stock;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "briefcase_stocks")
@NoArgsConstructor
@Getter
@Setter
public class BriefcaseStock implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "briefcase_id", referencedColumnName = "id")
    private Briefcase briefcase;

    @ManyToOne
    @JoinColumn(name = "stock_id", referencedColumnName = "id")
    private Stock stock;

    @Column(name = "stocks_amount", nullable = false)
    private int stocksAmount;

    @Column(name = "stock_actual_price")
    private double stockActualPrice;

    @Column(name = "revenue")
    private double revenue;

    @Column(name = "is_marginal", nullable = false)
    private boolean isMarginal;

    @Column(name = "stop_loss_price")
    private double stopLossPrice;

    @Column(name = "stop_loss_amount")
    private int stopLossAmount;

    @Column(name = "take_profit_price")
    private double takeProfitPrice;

    @Column(name = "take_profit_amount")
    private int takeProfitAmount;


    public BriefcaseStock(Briefcase briefcase, Stock stock, int stocksAmount, boolean isMarginal) {
        this.briefcase = briefcase;
        this.stock = stock;
        this.stocksAmount = stocksAmount;
        this.isMarginal = isMarginal;
    }

    @Override
    public String toString() {
        return "BriefcaseStock{" +
                "id=" + id +
                ", stock=" + stock +
                ", stocksAmount=" + stocksAmount +
                ", stockActualPrice=" + stockActualPrice +
                ", revenue=" + revenue +
                ", isMarginal=" + isMarginal +
                ", stopLossPrice=" + stopLossPrice +
                ", stopLossAmount=" + stopLossAmount +
                ", takeProfitPrice=" + takeProfitPrice +
                ", takeProfitAmount=" + takeProfitAmount +
                '}';
    }

    //equals hashcode ????
}
