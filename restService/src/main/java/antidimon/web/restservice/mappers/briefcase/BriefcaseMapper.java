package antidimon.web.restservice.mappers.briefcase;

import antidimon.web.restservice.mappers.operation.OperationMapper;
import antidimon.web.restservice.models.dto.input.briefcase.BriefcaseInputDTO;
import antidimon.web.restservice.models.dto.output.briefcases.BriefcaseInfoDTO;
import antidimon.web.restservice.models.dto.output.briefcases.BriefcaseOutputDTO;
import antidimon.web.restservice.models.entities.briefcase.Briefcase;
import antidimon.web.restservice.models.entities.user.MyUser;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BriefcaseMapper {

    private BriefcaseStockMapper briefcaseStockMapper;
    private OperationMapper operationMapper;

    private final ModelMapper modelMapper = new ModelMapper();

    public BriefcaseOutputDTO toOutputDTO(Briefcase briefcase) {
        BriefcaseOutputDTO output = modelMapper.map(briefcase, BriefcaseOutputDTO.class);
        output.setStocks(
                briefcase.getStocks().stream().map(stock -> briefcaseStockMapper.toOutputDTO(stock)).toList()
        );
        return output;
    }
    public Briefcase toEntity(BriefcaseInputDTO inputDTO, MyUser user) {
        Briefcase entity = modelMapper.map(inputDTO, Briefcase.class);
        entity.setOwner(user);
        return entity;
    }

    public BriefcaseInfoDTO toInfoDTO(Briefcase briefcase) {
        Hibernate.initialize(briefcase.getOperations());
        BriefcaseInfoDTO briefcaseInfoDTO = modelMapper.map(briefcase, BriefcaseInfoDTO.class);
        briefcaseInfoDTO.setOperations(briefcase.getOperations().stream().map(operationMapper::toOutputDTO).toList());
        return briefcaseInfoDTO;
    }
}
