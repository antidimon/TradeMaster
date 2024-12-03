package antidimon.web.restservice.mappers.operation;

import antidimon.web.restservice.models.dto.output.operation.OperationStatusOutputDTO;
import antidimon.web.restservice.models.entities.operation.OperationStatusEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class OperationStatusMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public OperationStatusOutputDTO toOutputDTO(OperationStatusEntity operationStatusEntity) {
        return modelMapper.map(operationStatusEntity, OperationStatusOutputDTO.class);
    }
}
