package antidimon.web.stocksadder.models;


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
