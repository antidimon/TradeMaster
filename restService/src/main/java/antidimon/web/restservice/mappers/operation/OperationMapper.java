package antidimon.web.restservice.mappers.operation;

import antidimon.web.restservice.mappers.stock.StockMapper;
import antidimon.web.restservice.models.dto.output.operation.OperationOutputDTO;
import antidimon.web.restservice.models.entities.briefcase.Briefcase;
import antidimon.web.restservice.models.entities.user.MyUser;
import antidimon.web.restservice.models.entities.operation.Operation;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OperationMapper {

    private final ModelMapper modelMapper = new ModelMapper();
    private final OperationStatusMapper operationStatusMapper;
    private final OperationNameMapper operationNameMapper;
    private final StockMapper stockMapper;

    public OperationOutputDTO toOutputDTO(Operation operation) {
        OperationOutputDTO operationOutputDTO = modelMapper.map(operation, OperationOutputDTO.class);
        operationOutputDTO.setStatus(operationStatusMapper.toOutputDTO(operation.getOperationStatus()));
        operationOutputDTO.setName(operationNameMapper.toOutputDTO(operation.getOperationName()));
        operationOutputDTO.setStock(stockMapper.toOutputDTO(operation.getOperationStock()));
        return operationOutputDTO;
    }
    public Operation toEntity(OperationOutputDTO operationOutputDTO, MyUser user, Briefcase briefcase) {
        Operation entity = modelMapper.map(operationOutputDTO, Operation.class);
        entity.setUser(user);
        entity.setOperationBriefcase(briefcase);
        return entity;
    }
}
