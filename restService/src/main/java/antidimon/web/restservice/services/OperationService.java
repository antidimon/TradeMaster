package antidimon.web.restservice.services;

import antidimon.web.restservice.models.dto.input.briefcase.BriefcaseInputDTO;
import antidimon.web.restservice.models.dto.input.operation.OperationInputDTO;
import antidimon.web.restservice.models.dto.output.operation.OperationDetails;
import antidimon.web.restservice.models.dto.output.operation.OperationDetailsProjection;
import antidimon.web.restservice.models.entities.briefcase.Briefcase;
import antidimon.web.restservice.models.entities.briefcase.BriefcaseStock;
import antidimon.web.restservice.models.entities.operation.*;
import antidimon.web.restservice.models.entities.stock.Stock;
import antidimon.web.restservice.models.entities.user.MyUser;
import antidimon.web.restservice.repositories.operation.OperationNameEntityRepository;
import antidimon.web.restservice.repositories.operation.OperationRepository;
import antidimon.web.restservice.repositories.operation.OperationStatusEntityRepository;
import antidimon.web.restservice.repositories.stock.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;


@Service
@RequiredArgsConstructor
@Slf4j
public class OperationService {

    private final MyUserService myUserService;
    private final BriefcaseService briefcaseService;
    private final OperationRepository operationRepository;
    private final StockRepository stockRepository;
    private final OperationStatusEntityRepository operationStatusEntityRepository;
    private final OperationNameEntityRepository operationNameEntityRepository;

    @Transactional
    public boolean operate(OperationInputDTO operationInputDTO) throws Exception {
        long operationId = this.operateStocks(operationInputDTO);
        sleep(TimeUnit.SECONDS.toMillis(5));
        OperationStatusEntity status = this.findOperationStatus(operationId);
        boolean flag = status.getOperationStatus().equals(OperationStatuses.CREATED);
        log.info(String.valueOf(flag));
        if (flag){
            this.doOperation(operationId);
            return true;
        }
        return false;
    }


    @Transactional
    public long operateStocks(OperationInputDTO operationInputDTO) throws Exception {
        MyUser user = myUserService.getUserEntity(operationInputDTO.getPhoneNumber());
        Briefcase briefcase = briefcaseService.getBriefcase(user.getId(), operationInputDTO.getBriefcaseName());
        switch (operationInputDTO.getName()){
            case SALE -> {
                BriefcaseStock briefcaseStock = briefcase.getStocks().stream()
                        .filter(stock -> stock.getStock().getName().equals(operationInputDTO.getStockName()))
                        .findFirst().get();

                double price = stockRepository.findLastByName(operationInputDTO.getStockName()).getPrice();

                Operation operation = new Operation(user, briefcase,
                        operationStatusEntityRepository.findByName("CREATED"),
                        operationNameEntityRepository.findByName("SALE"),
                        briefcaseStock.getStock(), operationInputDTO.getStocksAmount(), price, operationInputDTO.getStocksPerLot());

                operationRepository.save(operation);
                log.info("Saved operation {}", operation.getId());
                return operation.getId();
            }
            case PURCHASE -> {
                Stock stock = stockRepository.findLastByName(operationInputDTO.getStockName());
                log.info("Getted stock {}", stock.getId());

                Operation operation = new Operation(user, briefcase,
                        operationStatusEntityRepository.findByName("CREATED"),
                        operationNameEntityRepository.findByName("PURCHASE"),
                        stock, operationInputDTO.getStocksAmount(), stock.getPrice(), operationInputDTO.getStocksPerLot());
                log.info("operation {}", operation.getOperationName().getOperationName());
                operationRepository.save(operation);
                log.info("Saved operation {}", operation.getId());
                return operation.getId();
            }
            default -> {
                throw new Exception("wrong input");
            }
        }
    }

    @Transactional
    public void doOperation(long operationId) {
        operationRepository.doOperation(operationId);
    }

    public OperationStatusEntity findOperationStatus(long operationId) {
        return operationRepository.findOperationStatus(operationId);
    }

    public List<OperationDetails> getOperationDetails(BriefcaseInputDTO briefcaseInputDTO) {
        return operationRepository.getOperationsFromView(briefcaseInputDTO.getPhoneNumber(), briefcaseInputDTO.getBriefcaseName()).stream().map(this::convertToDTO).toList();
    }

    private OperationDetails convertToDTO(OperationDetailsProjection projection) {
        OperationDetails dto = new OperationDetails();
        dto.setOperationId(projection.getOperationId());
        dto.setFio(projection.getFio());
        dto.setPassportDetails(projection.getPassportDetails());
        dto.setPhoneNumber(projection.getPhoneNumber());
        dto.setBriefcaseName(projection.getBriefcaseName());
        dto.setOperationStatus(projection.getOperationStatus());
        dto.setOperationName(projection.getOperationName());
        dto.setStockName(projection.getStockName());
        dto.setStocksAmount(projection.getStocksAmount());
        dto.setStockPrice(projection.getStockPrice());
        dto.setStocksPerLot(projection.getStocksPerLot());
        dto.setOperationTime(projection.getOperationTime());
        dto.setTotalAmount(projection.getTotalAmount());
        return dto;
    }
}
