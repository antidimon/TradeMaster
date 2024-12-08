package antidimon.web.mvcservice.models.outputDTO.operation;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OperationDetails {
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