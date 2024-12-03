package antidimon.web.restservice.models.dto.output.operation;

import antidimon.web.restservice.models.dto.output.stock.StockOutputDTO;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OperationOutputDTO {

    private long id;
    private OperationStatusOutputDTO status;
    private OperationNameOutputDTO name;
    private LocalDateTime createdAt;
    private StockOutputDTO stock;
    private double price;
    private int stocksAmount;
}
