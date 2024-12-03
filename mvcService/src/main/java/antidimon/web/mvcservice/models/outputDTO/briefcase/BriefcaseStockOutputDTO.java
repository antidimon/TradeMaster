package antidimon.web.mvcservice.models.outputDTO.briefcase;

import antidimon.web.mvcservice.models.outputDTO.stock.StockOutputDTO;
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
