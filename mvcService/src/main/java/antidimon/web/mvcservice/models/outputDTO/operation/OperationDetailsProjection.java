package antidimon.web.mvcservice.models.outputDTO.operation;

import java.time.LocalDateTime;

public interface OperationDetailsProjection {
    Long getOperationId();
    String getFio();
    String getPassportDetails();
    String getPhoneNumber();
    String getBriefcaseName();
    String getOperationStatus();
    String getOperationName();
    String getStockName();
    Integer getStocksAmount();
    double getStockPrice();
    int getStocksPerLot();
    LocalDateTime getOperationTime();
    double getTotalAmount();
}
