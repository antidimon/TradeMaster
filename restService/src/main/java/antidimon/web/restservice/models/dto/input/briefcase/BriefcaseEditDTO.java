package antidimon.web.restservice.models.dto.input.briefcase;

import antidimon.web.restservice.models.dto.output.user.MyUserOutputDTO;
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
