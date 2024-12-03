package antidimon.web.mvcservice.models.inputDTO.briefcase;

import antidimon.web.mvcservice.models.outputDTO.user.MyUserOutputDTO;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BriefcaseEditDTO {
    private String phoneNumber;
    private String briefcaseName;
    private String newName;
    private double addedBalance;
}
