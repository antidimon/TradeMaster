package antidimon.web.mvcservice.controllers;

import antidimon.web.mvcservice.models.inputDTO.user.MyUserRegisterDTO;
import antidimon.web.mvcservice.services.AuthService;
import antidimon.web.mvcservice.services.RestTemplateService;
import antidimon.web.mvcservice.utils.validators.MyUserValidator;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@AllArgsConstructor
public class AuthController {

    private final RestTemplateService restTemplateService;
    private final MyUserValidator myUserValidator;
    private final AuthService authService;

    @GetMapping("/login")
    public String login(@RequestParam(required = false, name = "error") String exception, Model model, Authentication auth) {

        if (auth != null){
            return "redirect:/";
        }

        if (exception != null) {
            model.addAttribute("error", exception);
        }
        return "auth/login";
    }

    @GetMapping("/register")
    public String register(Model model, Authentication auth) {
        if (auth != null){return "redirect:/";}
        model.addAttribute("user", new MyUserRegisterDTO());
        return "auth/register";
    }

    @PostMapping("/register")
    public String doRegister(@ModelAttribute("user") @Valid MyUserRegisterDTO user, BindingResult bindingResult) {

        myUserValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        authService.save(user);
        boolean flag = restTemplateService.sendNewUserToRest(user);
        if (!flag) {
            authService.delete(user);
        }
        return (flag) ? "redirect:/login" : "redirect:/register";

    }

}
