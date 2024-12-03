package antidimon.web.mvcservice.models.outputDTO.briefcase;


import antidimon.web.mvcservice.models.outputDTO.operation.OperationOutputDTO;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BriefcaseInfoDTO {

    private String briefcaseName;
    private List<OperationOutputDTO> operations;
}
