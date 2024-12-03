package antidimon.web.restservice.controllers;


import antidimon.web.restservice.models.dto.input.user.MyUserInputDTO;
import antidimon.web.restservice.services.MyUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final MyUserService myUserService;

    public AuthController(MyUserService myUserService) {
        this.myUserService = myUserService;
    }

    @PostMapping("/register")
    public ResponseEntity<MyUserInputDTO> register(@RequestBody MyUserInputDTO user) {
        try {
            myUserService.save(user);
            return ResponseEntity.ok(user);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
