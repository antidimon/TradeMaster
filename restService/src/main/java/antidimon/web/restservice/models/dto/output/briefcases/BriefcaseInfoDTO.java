package antidimon.web.restservice.models.dto.output.briefcases;


import antidimon.web.restservice.models.dto.output.operation.OperationOutputDTO;
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
