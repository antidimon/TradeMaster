package antidimon.web.mvcservice.controllers;


import antidimon.web.mvcservice.models.outputDTO.briefcase.BriefcaseInfoDTO;
import antidimon.web.mvcservice.models.outputDTO.briefcase.BriefcaseOutputDTO;
import antidimon.web.mvcservice.models.outputDTO.operation.OperationDetails;
import antidimon.web.mvcservice.models.outputDTO.operation.OperationDetailsProjection;
import antidimon.web.mvcservice.models.outputDTO.operation.OperationOutputDTO;
import antidimon.web.mvcservice.services.ExcelService;
import antidimon.web.mvcservice.services.JwtService;
import antidimon.web.mvcservice.services.RestTemplateService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/briefcases")
@AllArgsConstructor
public class BriefcaseController {

    private final JwtService jwtService;
    private final RestTemplateService restTemplateService;
    private final ExcelService excelService;

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

    @PatchMapping("/save")
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
            model.addAttribute("valueError", true);
            return briefcase(request, name, model);
        }
        return briefcase(request, name, model);
    }



    @PostMapping("/deposit")
    public String doDeposit(HttpServletRequest request, @RequestParam("name") String name, @RequestParam("summ") double summ, Model model) {
        String phoneNumber = jwtService.getPhoneNumber(request.getCookies());
        if (!restTemplateService.sendToUpdateBalance(phoneNumber, name, summ)){
            model.addAttribute("valueError", true);
            return briefcase(request, name, model);
        }
        return briefcase(request, name, model);
    }



    @PostMapping("/new")
    public String createBriefcase(HttpServletRequest request, @RequestParam("name") String name, Model model){
        String phoneNumber = jwtService.getPhoneNumber(request.getCookies());
        if (!restTemplateService.sendToCreateNewBriefcase(phoneNumber, name)){
            model.addAttribute("nameError", true);
            return briefcase(request, name, model);
        }
        return briefcase(request, name, model);
    }



    @GetMapping("/operations")
    public ResponseEntity<ByteArrayResource> getOperationsView(HttpServletRequest request, @RequestParam("name") String name) throws IOException {
        String phoneNumber = jwtService.getPhoneNumber(request.getCookies());
        List<OperationDetails> operationsDetails = restTemplateService.sendToGetOperationsDetails(phoneNumber, name);
        System.out.println("controller " + operationsDetails.size());
        ByteArrayOutputStream outputStream = excelService.generateExcelOperationsDetails(operationsDetails);

        ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=operation_details.xlsx");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(outputStream.size())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
