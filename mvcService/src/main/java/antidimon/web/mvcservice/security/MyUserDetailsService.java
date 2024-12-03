package antidimon.web.mvcservice.security;


import antidimon.web.mvcservice.repositories.MyUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private MyUserRepository myUserRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        Optional<MyUser> foundedUser = myUserRepository.findByPhoneNumber(phoneNumber);
        if (foundedUser.isEmpty()) {
            throw new UsernameNotFoundException(phoneNumber);
        }
        return new MyUserDetails(foundedUser.get());
    }
}
