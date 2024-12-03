package antidimon.web.restservice.models.dto.input.stock;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StockInputDTO {

    private String name;
    private double price;
    private int stocksPerLot;
}
