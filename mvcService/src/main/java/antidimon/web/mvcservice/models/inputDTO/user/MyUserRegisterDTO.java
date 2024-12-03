package antidimon.web.mvcservice.models.inputDTO.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MyUserRegisterDTO {

    @NotBlank(message = "fio can't be empty")
    private String fio;

    @Min(value = 14, message = "min age is 14")
    @Max(value = 150, message = "max age is 150")
    private int age;

    @NotBlank(message = "passport details can't be empty")
    @Length(min = 14, max = 14, message = "passport details is like: ********* pass") //9 + пробел + pass
    private String passportDetails;

    @NotBlank(message = "phone can't be empty")
    @Length(min = 11, max = 11, message = "Phone number must be like: 8********** (11 numbers)")
    private String phoneNumber;

    @NotBlank(message = "email can't be empty")
    @Email(message = "must be email format")
    private String email;

    @NotBlank(message = "password can't be empty")
    private String password;
}
