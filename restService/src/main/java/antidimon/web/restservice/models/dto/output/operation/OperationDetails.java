package antidimon.web.restservice.models.dto.output.operation;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


//@Entity
//@SqlResultSetMapping(
//        name = "OperationDetailsMapping",
//        entities = @EntityResult(
//                entityClass = OperationDetails.class,
//                fields = {
//                        @FieldResult(name = "operationId", column = "operation_id"),
//                        @FieldResult(name = "fio", column = "fio"),
//                        @FieldResult(name = "passportDetails", column = "passport_details"),
//                        @FieldResult(name = "phoneNumber", column = "phone_number"),
//                        @FieldResult(name = "briefcaseName", column = "briefcase_name"),
//                        @FieldResult(name = "operationStatus", column = "operation_status"),
//                        @FieldResult(name = "operationName", column = "operation_name"),
//                        @FieldResult(name = "stockName", column = "stock_name"),
//                        @FieldResult(name = "stocksAmount", column = "stocks_amount"),
//                        @FieldResult(name = "stockPrice", column = "stock_price"),
//                        @FieldResult(name = "stocksPerLot", column = "stocks_per_lot"),
//                        @FieldResult(name = "operationTime", column = "operation_time"),
//                        @FieldResult(name = "totalAmount", column = "total_amount")
//                }
//        )
//)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OperationDetails {
//    @Id
    private Long operationId;
    private String fio;
    private String passportDetails;
    private String phoneNumber;
    private String briefcaseName;
    private String operationStatus;
    private String operationName;
    private String stockName;
    private Integer stocksAmount;
    private double stockPrice;
    private int stocksPerLot;
    private LocalDateTime operationTime;
    private double totalAmount;
}
