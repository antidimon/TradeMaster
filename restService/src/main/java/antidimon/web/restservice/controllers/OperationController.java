package antidimon.web.restservice.controllers;


import antidimon.web.restservice.models.dto.input.briefcase.BriefcaseInputDTO;
import antidimon.web.restservice.models.dto.input.operation.OperationInputDTO;
import antidimon.web.restservice.models.dto.output.operation.OperationDetails;
import antidimon.web.restservice.services.OperationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operations")
@AllArgsConstructor
@Slf4j
public class OperationController {

    private final OperationService operationService;

    @PostMapping("/buy")
    public ResponseEntity<OperationInputDTO> doBuy(@RequestBody OperationInputDTO operationInputDTO) {
        try{
            boolean flag = operationService.operate(operationInputDTO);
            if (flag){
                return ResponseEntity.ok(operationInputDTO);
            }
            return ResponseEntity.badRequest().body(operationInputDTO);
        }catch (Exception e){
            log.info(e.getMessage());
            return ResponseEntity.badRequest().body(operationInputDTO);
        }
    }


    @PostMapping("/sell")
    public ResponseEntity<OperationInputDTO> doSell(@RequestBody OperationInputDTO operationInputDTO) {
        try{
            boolean flag = operationService.operate(operationInputDTO);
            if (flag){
                return ResponseEntity.ok(operationInputDTO);
            }
            return ResponseEntity.badRequest().body(operationInputDTO);
        }catch (Exception e){
            log.info(e.getMessage());
            return ResponseEntity.badRequest().body(operationInputDTO);
        }
    }


    @PostMapping
    public List<OperationDetails> getOperationDetails(@RequestBody BriefcaseInputDTO briefcaseInputDTO){
        return operationService.getOperationDetails(briefcaseInputDTO);
    }
}
