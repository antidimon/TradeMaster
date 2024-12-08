package antidimon.web.mvcservice.models.inputDTO.operation;

import antidimon.web.mvcservice.models.entities.OperationNames;
import antidimon.web.mvcservice.models.outputDTO.briefcase.BriefcaseOutputDTO;
import antidimon.web.mvcservice.models.outputDTO.user.MyUserOutputDTO;

import antidimon.web.mvcservice.models.outputDTO.stock.StockOutputDTO;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OperationInputDTO {

    private String phoneNumber;
    private String briefcaseName;
    private OperationNames name;
    private String stockName;
    private int stocksAmount;
    private int stocksPerLot;


}
