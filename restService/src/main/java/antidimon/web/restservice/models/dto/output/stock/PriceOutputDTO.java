package antidimon.web.restservice.models.dto.output.stock;

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