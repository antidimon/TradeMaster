package antidimon.web.restservice.mappers.user;

import antidimon.web.restservice.models.dto.input.user.MyUserInputDTO;
import antidimon.web.restservice.models.dto.output.user.MyUserOutputDTO;
import antidimon.web.restservice.models.entities.user.MyUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MyUserMapper {

    private final ModelMapper modelMapper = new ModelMapper();


    public MyUserOutputDTO toOutputDTO(MyUser user){
        return modelMapper.map(user, MyUserOutputDTO.class);
    }
    public MyUser toEntity(MyUserInputDTO dto){
        return modelMapper.map(dto, MyUser.class);
    }
}
