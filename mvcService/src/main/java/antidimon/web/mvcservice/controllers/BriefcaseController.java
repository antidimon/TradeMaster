package antidimon.web.mvcservice.controllers;


import antidimon.web.mvcservice.models.outputDTO.briefcase.BriefcaseInfoDTO;
import antidimon.web.mvcservice.models.outputDTO.briefcase.BriefcaseOutputDTO;
import antidimon.web.mvcservice.models.outputDTO.operation.OperationOutputDTO;
import antidimon.web.mvcservice.services.JwtService;
import antidimon.web.mvcservice.services.RestTemplateService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/briefcases")
@AllArgsConstructor
public class BriefcaseController {

    private final JwtService jwtService;
    private RestTemplateService restTemplateService;

    @GetMapping
    public String briefcase(HttpServletRequest request, @RequestParam("name") String name, Model model) {
        String phoneNumber = jwtService.getPhoneNumber(request.getCookies());
        BriefcaseOutputDTO briefcase = restTemplateService.sendToGetBriefcase(phoneNumber, name);
        BriefcaseInfoDTO briefcaseInfo = restTemplateService.sendToGetBriefcaseInfo(phoneNumber, name);
        List<OperationOutputDTO> operations = briefcaseInfo.getOperations().stream().sorted(Comparator.comparing(OperationOutputDTO::getCreatedAt).reversed()).toList();
        model.addAttribute("briefcase", briefcase);
        model.addAttribute("operations", operations);

        return "briefcase";
    }

    @PostMapping("/save")
    public String saveBriefcase(HttpServletRequest request, @RequestParam("name") String name, @RequestParam("newName") String newName, Model model) {
        String phoneNumber = jwtService.getPhoneNumber(request.getCookies());
        if (!restTemplateService.sendToSaveAfterEdit(phoneNumber, name, newName)) {
            model.addAttribute("nameError", true);
            return briefcase(request, name, model);
        }
        return briefcase(request, newName, model);
    }



    @PostMapping("/withdraw")
    public String doWithdraw(HttpServletRequest request, @RequestParam("name") String name, @RequestParam("summ") double summ, Model model) {
        String phoneNumber = jwtService.getPhoneNumber(request.getCookies());
        if (!restTemplateService.sendToUpdateBalance(phoneNumber, name, -1*summ)){
            return briefcase(request, name, model);
        }
        model.addAttribute("valueError", true);
        return briefcase(request, name, model);
    }



    @PostMapping("/deposit")
    public String doDeposit(HttpServletRequest request, @RequestParam("name") String name, @RequestParam("summ") double summ, Model model) {
        String phoneNumber = jwtService.getPhoneNumber(request.getCookies());
        if (restTemplateService.sendToUpdateBalance(phoneNumber, name, summ)){
            return briefcase(request, name, model);
        }
        model.addAttribute("valueError", true);
        return briefcase(request, name, model);
    }
}
