package antidimon.web.restservice.models.dto.output.operation;


import antidimon.web.restservice.models.entities.operation.OperationNames;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationNameOutputDTO {
    private OperationNames operationName;
}
