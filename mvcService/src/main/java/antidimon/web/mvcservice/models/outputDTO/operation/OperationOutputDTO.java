package antidimon.web.mvcservice.models.outputDTO.operation;


import antidimon.web.mvcservice.models.entities.OperationNames;
import antidimon.web.mvcservice.models.outputDTO.stock.StockOutputDTO;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OperationOutputDTO {

    private long id;
    private OperationStatusesOutputDTO status;
    private OperationNamesOutputDTO name;
    private LocalDateTime createdAt;
    private StockOutputDTO stock;
    private double price;
    private int stocksAmount;

    public String getOperationName() {
        String name = "";
        switch (this.name.getOperationName()){
            case SALE -> name = "Продажа";
            case PURCHASE -> name = "Покупка";
            }
        return name;
    }

    public String getOperationStatus() {
        String status = "";
        switch (this.status.getOperationStatus()){
            case CREATED -> status = "Создано";
            case CANCELED -> status = "Отменено";
            case SUCCEEDED -> status = "Успешно";
            case WAITING_FOR_CAPTURE -> status = "Ожидает подтверждения";
        }
        return status;
    }
}
