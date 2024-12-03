package antidimon.web.restservice.services;

import antidimon.web.restservice.models.dto.input.operation.OperationInputDTO;
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
    public long operateStocks(OperationInputDTO operationInputDTO) throws Exception {
        MyUser user = myUserService.getUserEntity(operationInputDTO.getPhoneNumber());
        Briefcase briefcase = briefcaseService.getBriefcase(user.getId(), operationInputDTO.getBriefcaseName());
        log.info("Getted user, briefcase");
        switch (operationInputDTO.getName()){
            case SALE -> {
                BriefcaseStock briefcaseStock = briefcase.getStocks().stream()
                        .filter(stock -> stock.getStock().getName().equals(operationInputDTO.getStockName()))
                        .findFirst().get();

                double price = stockRepository.findLastByName(operationInputDTO.getStockName()).getPrice();

                Operation operation = new Operation(user, briefcase,
                        operationStatusEntityRepository.findByName("CREATED"),
                        operationNameEntityRepository.findByName("SALE"),
                        briefcaseStock.getStock(), operationInputDTO.getStocksAmount(), price);

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
                        stock, operationInputDTO.getStocksAmount(), stock.getPrice());
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
}
