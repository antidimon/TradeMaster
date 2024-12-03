package antidimon.web.restservice.models.dto.output.operation;


import antidimon.web.restservice.models.entities.operation.OperationStatuses;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationStatusOutputDTO {
    private OperationStatuses operationStatus;
}
