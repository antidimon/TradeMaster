package antidimon.web.restservice.mappers.operation;


import antidimon.web.restservice.models.dto.output.operation.OperationNameOutputDTO;
import antidimon.web.restservice.models.entities.operation.OperationNameEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class OperationNameMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public OperationNameOutputDTO toOutputDTO(OperationNameEntity operationNameEntity) {
        return modelMapper.map(operationNameEntity, OperationNameOutputDTO.class);
    }
}
