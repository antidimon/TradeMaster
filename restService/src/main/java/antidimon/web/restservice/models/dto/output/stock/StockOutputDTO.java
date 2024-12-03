package antidimon.web.restservice.models.dto.output.stock;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StockOutputDTO {

    private String name;
    private double price;
    private LocalDateTime gettedAt;
    private double predictedPrice;
    private int stocksPerLot;
    private String url;
    private List<PriceOutputDTO> prices;
}
