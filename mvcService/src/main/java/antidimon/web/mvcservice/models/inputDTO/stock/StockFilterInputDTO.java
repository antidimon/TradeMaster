package antidimon.web.mvcservice.models.inputDTO.stock;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StockFilterInputDTO {
    private String name;
    private double from;
    private double to;
}
