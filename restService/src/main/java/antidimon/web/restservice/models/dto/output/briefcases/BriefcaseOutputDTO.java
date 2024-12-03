package antidimon.web.restservice.models.dto.output.briefcases;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BriefcaseOutputDTO {

    private String briefcaseName;
    private double depositedBalance;
    private double balanceNow;
    private double freeBalance;
    private double totalRevenue;
    private List<BriefcaseStockOutputDTO> stocks;
}
