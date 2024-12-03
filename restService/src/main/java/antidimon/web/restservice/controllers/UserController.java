package antidimon.web.restservice.controllers;


import antidimon.web.restservice.models.dto.input.briefcase.BriefcaseStockFindDTO;
import antidimon.web.restservice.models.dto.input.user.PhoneDTO;
import antidimon.web.restservice.models.dto.output.briefcases.BriefcaseStockOutputDTO;
import antidimon.web.restservice.models.dto.output.user.MyUserOutputDTO;
import antidimon.web.restservice.services.MyUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final MyUserService myUserService;

    @PostMapping
    public ResponseEntity<MyUserOutputDTO> getUser(@RequestBody PhoneDTO phoneDTO){

        try{
            MyUserOutputDTO myUserOutputDTO = myUserService.getUserDTO(phoneDTO.getPhoneNumber());
            return ResponseEntity.ok(myUserOutputDTO);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/stocks")
    public ResponseEntity<List<BriefcaseStockOutputDTO>> getUserStocks(@RequestBody BriefcaseStockFindDTO briefcaseStockFindDTO){
        try {
            List<BriefcaseStockOutputDTO> list = myUserService.findStocks(briefcaseStockFindDTO.getPhoneNumber(), briefcaseStockFindDTO.getName());
            return ResponseEntity.ok(list);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
