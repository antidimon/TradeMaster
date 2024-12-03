package antidimon.web.restservice.controllers;


import antidimon.web.restservice.models.dto.input.briefcase.BriefcaseEditDTO;
import antidimon.web.restservice.models.dto.input.briefcase.BriefcaseInputDTO;
import antidimon.web.restservice.models.dto.input.user.PhoneDTO;
import antidimon.web.restservice.models.dto.output.briefcases.BriefcaseInfoDTO;
import antidimon.web.restservice.models.dto.output.briefcases.BriefcaseOutputDTO;
import antidimon.web.restservice.services.BriefcaseService;
import antidimon.web.restservice.services.MyUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/briefcases")
@AllArgsConstructor
@Slf4j
public class BriefcaseController {

    private final BriefcaseService briefcaseService;
    private MyUserService myUserService;

    @PostMapping
    public ResponseEntity<BriefcaseOutputDTO> getBriefcase(@RequestBody BriefcaseInputDTO briefcase){
        try{
            BriefcaseOutputDTO briefcaseOutputDTO= briefcaseService.getBriefcase(briefcase);
            return ResponseEntity.ok(briefcaseOutputDTO);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }

    }

    @PostMapping("/info")
    public ResponseEntity<BriefcaseInfoDTO> getBriefcaseInfo(@RequestBody BriefcaseInputDTO briefcaseInputDTO) {

        try{
            BriefcaseInfoDTO briefcaseInfoDTO = briefcaseService.getBriefcaseInfo(briefcaseInputDTO);
            return ResponseEntity.ok(briefcaseInfoDTO);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/index")
    public ResponseEntity<List<BriefcaseOutputDTO>> getUserBriefcases(@RequestBody PhoneDTO phoneDTO) {

        try{
            List<BriefcaseOutputDTO> briefcases = myUserService.getUserBriefcases(phoneDTO.getPhoneNumber());
            return ResponseEntity.ok(briefcases);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }

    }

    @PostMapping("/name")
    public ResponseEntity<BriefcaseEditDTO> editBriefcaseName(@RequestBody BriefcaseEditDTO briefcaseEditDTO) {
        try {
            boolean flag = briefcaseService.editBriefcaseName(briefcaseEditDTO);
            if (flag) {
                return ResponseEntity.ok(briefcaseEditDTO);
            }else {
                return ResponseEntity.badRequest().body(briefcaseEditDTO);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(briefcaseEditDTO);
        }
    }

    @PostMapping("/balance")
    public ResponseEntity<BriefcaseEditDTO> editBriefcaseBalance(@RequestBody BriefcaseEditDTO briefcaseEditDTO) {
        try {
            boolean flag = briefcaseService.editBriefcaseBalance(briefcaseEditDTO);
            if (flag) {
                return ResponseEntity.ok(briefcaseEditDTO);
            }else {
                return ResponseEntity.badRequest().body(briefcaseEditDTO);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(briefcaseEditDTO);
        }
    }
}
