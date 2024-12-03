package antidimon.web.mvcservice.mappers;


import antidimon.web.mvcservice.models.inputDTO.user.MyUserInputDTO;
import antidimon.web.mvcservice.models.inputDTO.user.MyUserRegisterDTO;
import antidimon.web.mvcservice.security.MyUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MyUserMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public MyUser toEntity(MyUserRegisterDTO myUserRegisterDTO) {
        return modelMapper.map(myUserRegisterDTO, MyUser.class);
    }

    public MyUserInputDTO toInputDTO(MyUser myUser) {
        return modelMapper.map(myUser, MyUserInputDTO.class);
    }

    public MyUserInputDTO registerToInput(MyUserRegisterDTO myUserRegisterDTO) {
        return modelMapper.map(myUserRegisterDTO, MyUserInputDTO.class);
    }
}
