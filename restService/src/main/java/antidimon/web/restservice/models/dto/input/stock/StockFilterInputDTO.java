package antidimon.web.restservice.models.dto.input.stock;

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
