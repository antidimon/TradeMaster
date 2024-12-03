package antidimon.web.mvcservice.utils.validators;


import antidimon.web.mvcservice.models.inputDTO.user.MyUserRegisterDTO;
import antidimon.web.mvcservice.services.AuthService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MyUserValidator implements Validator {

    private final AuthService authService;

    public MyUserValidator(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return MyUserRegisterDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        MyUserRegisterDTO myUserRegisterDTO = (MyUserRegisterDTO) target;

        if (authService.isPhoneNumberExists(myUserRegisterDTO.getPhoneNumber())){
            errors.rejectValue("phoneNumber", "phoneNumberExists", "Phone number already registered");
        }
        if (authService.isEmailExists(myUserRegisterDTO.getEmail())){
            errors.rejectValue("email", "emailExists", "Email already registered");
        }
        if (authService.isPassportDetailsExists(myUserRegisterDTO.getPassportDetails())){
            errors.rejectValue("passportDetails", "passportDetailsExists", "Passport details already registered");
        }
    }
}
