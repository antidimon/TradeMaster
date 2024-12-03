package antidimon.web.mvcservice.models.outputDTO.operation;


import antidimon.web.mvcservice.models.entities.OperationStatuses;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationStatusesOutputDTO {
    private OperationStatuses operationStatus;
}
