package antidimon.web.mvcservice.models.outputDTO.stock;

import lombok.*;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PriceOutputDTO {

    public double price;
    public LocalDateTime dateTime;
}
