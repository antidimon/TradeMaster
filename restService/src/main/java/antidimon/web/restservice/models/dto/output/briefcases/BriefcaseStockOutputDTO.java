package antidimon.web.restservice.models.dto.output.briefcases;

import antidimon.web.restservice.models.dto.output.stock.StockOutputDTO;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BriefcaseStockOutputDTO {

    private StockOutputDTO stock;
    private int stocksAmount;
    private double stockActualPrice;
    private double revenue;
    private boolean isMarginal;
    private double stopLossPrice;
    private int stopLossAmount;
    private double takeProfitPrice;
    private int takeProfitAmount;
    private String briefcaseName;
}
