package antidimon.web.mvcservice.services;


import antidimon.web.mvcservice.mappers.MyUserMapper;
import antidimon.web.mvcservice.models.inputDTO.user.MyUserRegisterDTO;
import antidimon.web.mvcservice.repositories.MyUserRepository;
import antidimon.web.mvcservice.security.MyUser;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AuthService {

    private final MyUserMapper myUserMapper;
    private MyUserRepository myUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void save(MyUserRegisterDTO user){
        MyUser userEntity = myUserMapper.toEntity(user);
        encodePersonPassword(userEntity);
        myUserRepository.save(userEntity);
    }

    public boolean isPhoneNumberExists(String phoneNumber) {
        return myUserRepository.findByPhoneNumber(phoneNumber).isPresent();
    }

    public boolean isEmailExists(String email) {
        return myUserRepository.findByEmail(email).isPresent();
    }

    public boolean isPassportDetailsExists(String passportDetails) {
        return myUserRepository.findByPassportDetails(passportDetails).isPresent();
    }

    private void encodePersonPassword(MyUser user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    public void delete(MyUserRegisterDTO user) {
        MyUser userEntity = myUserRepository.findByPhoneNumber(user.getPhoneNumber()).get();
        myUserRepository.delete(userEntity);
    }

    public MyUser findByPhoneNumber(String phoneNumber) {
        return myUserRepository.findByPhoneNumber(phoneNumber).orElse(null);
    }
}
