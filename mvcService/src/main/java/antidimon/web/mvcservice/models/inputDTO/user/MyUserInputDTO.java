package antidimon.web.mvcservice.models.inputDTO.user;


import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MyUserInputDTO {

    private String fio;
    private int age;
    private String passportDetails;
    private String phoneNumber;
    private String email;
}
