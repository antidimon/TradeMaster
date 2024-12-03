package antidimon.web.mvcservice.controllers;


import antidimon.web.mvcservice.models.inputDTO.user.PhoneDTO;
import antidimon.web.mvcservice.models.outputDTO.briefcase.BriefcaseOutputDTO;
import antidimon.web.mvcservice.security.MyUser;
import antidimon.web.mvcservice.services.AuthService;
import antidimon.web.mvcservice.services.JwtService;
import antidimon.web.mvcservice.services.RestTemplateService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class MainController {

    private final JwtService jwtService;
    private final AuthService authService;
    private final RestTemplateService restTemplateService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model, @RequestParam(required = false, name = "unhide") boolean flag) {
        String phoneNumber = jwtService.getPhoneNumber(request.getCookies());
        MyUser user = authService.findByPhoneNumber(phoneNumber);

        List<BriefcaseOutputDTO> briefcases = restTemplateService.sendToGetUserBriefcases(new PhoneDTO(phoneNumber));
        model.addAttribute("user", user);
        if (flag) model.addAttribute("unhide", true);
        model.addAttribute("briefcases", briefcases);
        return "main";
    }

    @GetMapping("/news")
    public String getNews(HttpServletRequest request, Model model) {
        return "news";
    }
}
