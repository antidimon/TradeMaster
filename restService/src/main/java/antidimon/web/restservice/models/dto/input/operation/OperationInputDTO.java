package antidimon.web.restservice.models.dto.input.operation;

import antidimon.web.restservice.models.dto.output.briefcases.BriefcaseOutputDTO;
import antidimon.web.restservice.models.dto.output.user.MyUserOutputDTO;
import antidimon.web.restservice.models.dto.output.stock.StockOutputDTO;
import antidimon.web.restservice.models.entities.operation.OperationNames;
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


}
