package antidimon.web.restservice.controllers;


import antidimon.web.restservice.models.dto.input.operation.OperationInputDTO;
import antidimon.web.restservice.models.entities.operation.Operation;
import antidimon.web.restservice.models.entities.operation.OperationStatusEntity;
import antidimon.web.restservice.models.entities.operation.OperationStatuses;
import antidimon.web.restservice.services.OperationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

@RestController
@RequestMapping("/operations")
@AllArgsConstructor
@Slf4j
public class OperationController {

    private final OperationService operationService;

    @PostMapping("/buy")
    public ResponseEntity<OperationInputDTO> doBuy(@RequestBody OperationInputDTO operationInputDTO) {
        try{
            boolean flag = operate(operationInputDTO);
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
            boolean flag = operate(operationInputDTO);
            if (flag){
                return ResponseEntity.ok(operationInputDTO);
            }
            return ResponseEntity.badRequest().body(operationInputDTO);
        }catch (Exception e){
            log.info(e.getMessage());
            return ResponseEntity.badRequest().body(operationInputDTO);
        }
    }


//    private boolean operate(OperationInputDTO operationInputDTO) throws Exception {
//        long operationId = operationService.operateStocks(operationInputDTO);
//        sleep(TimeUnit.SECONDS.toMillis(3));
//        OperationStatusEntity status  = operationService.findOperationStatus(operationId);
//        boolean flag = status.getOperationStatus().equals(OperationStatuses.CREATED);
//        log.info(String.valueOf(flag));
//        if (flag){
//            operationService.doOperation(operationId);
//            return true;
//        }
//        return false;
//    }


    private boolean operate(OperationInputDTO operationInputDTO) throws Exception {
        long operationId = operationService.operateStocks(operationInputDTO);
        sleep(TimeUnit.SECONDS.toMillis(5));
        OperationStatusEntity status = operationService.findOperationStatus(operationId);
        boolean flag = status.getOperationStatus().equals(OperationStatuses.CREATED);
        log.info(String.valueOf(flag));
        if (flag){
            operationService.doOperation(operationId);
            return true;
        }
        return false;
    }
}
