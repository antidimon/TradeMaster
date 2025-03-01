package antidimon.web.mvcservice.models.outputDTO.user;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MyUserOutputDTO {

    private String fio;
    private int age;
    private String passportDetails;
    private String phoneNumber;
    private String email;
    private boolean isQualified;
}
