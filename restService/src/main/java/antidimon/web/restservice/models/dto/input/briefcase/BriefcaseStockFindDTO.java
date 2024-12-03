package antidimon.web.restservice.models.dto.input.briefcase;

import antidimon.web.restservice.models.dto.output.user.MyUserOutputDTO;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BriefcaseStockFindDTO {

    private String phoneNumber;
    private String name;
}