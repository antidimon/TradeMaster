package antidimon.web.restservice.controllers;



import antidimon.web.restservice.models.dto.output.briefcases.BriefcaseStockOutputDTO;
import antidimon.web.restservice.services.MyUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final MyUserService myUserService;

    @GetMapping("/stocks")
    public ResponseEntity<List<BriefcaseStockOutputDTO>> getUserStocks(@RequestParam("phone") String phoneNumber, @RequestParam(required = false, value = "name") String name){
        try {
            List<BriefcaseStockOutputDTO> list;
            if (name != null && !name.isEmpty()){
                list = myUserService.findStocks(phoneNumber, name);
            }else {
                list = myUserService.findStocks(phoneNumber);
            }
            return ResponseEntity.ok(list);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
