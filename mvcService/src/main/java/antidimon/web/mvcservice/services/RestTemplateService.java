package antidimon.web.mvcservice.services;

import antidimon.web.mvcservice.mappers.MyUserMapper;
import antidimon.web.mvcservice.models.entities.OperationNames;
import antidimon.web.mvcservice.models.inputDTO.briefcase.BriefcaseInputDTO;
import antidimon.web.mvcservice.models.inputDTO.briefcase.BriefcaseEditDTO;
import antidimon.web.mvcservice.models.inputDTO.briefcase.BriefcaseStockFindDTO;
import antidimon.web.mvcservice.models.inputDTO.operation.OperationInputDTO;
import antidimon.web.mvcservice.models.inputDTO.stock.StockFilterInputDTO;
import antidimon.web.mvcservice.models.inputDTO.user.MyUserInputDTO;
import antidimon.web.mvcservice.models.inputDTO.user.MyUserRegisterDTO;
import antidimon.web.mvcservice.models.inputDTO.user.PhoneDTO;
import antidimon.web.mvcservice.models.outputDTO.briefcase.BriefcaseInfoDTO;
import antidimon.web.mvcservice.models.outputDTO.briefcase.BriefcaseOutputDTO;
import antidimon.web.mvcservice.models.outputDTO.briefcase.BriefcaseStockOutputDTO;
import antidimon.web.mvcservice.models.outputDTO.stock.StockOutputDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class RestTemplateService {

    private final RestTemplate restTemplate;
    private final MyUserMapper myUserMapper;

    private final HttpHeaders headers = new HttpHeaders();

    @Value("${REST_URL}")
    private String REST_URL;

    public RestTemplateService(RestTemplate restTemplate, MyUserMapper myUserMapper) {
        this.restTemplate = restTemplate;
        this.myUserMapper = myUserMapper;
        this.headers.setContentType(MediaType.APPLICATION_JSON);
    }

    public boolean sendNewUserToRest(MyUserRegisterDTO myUserRegisterDTO) {
        MyUserInputDTO myUserInputDTO = myUserMapper.registerToInput(myUserRegisterDTO);
        ResponseEntity<MyUserInputDTO> response = restTemplate.postForEntity(REST_URL + "/register", myUserInputDTO, MyUserInputDTO.class);
        return response.getStatusCode().is2xxSuccessful();
    }

//    public MyUserOutputDTO sendToGetUser(PhoneDTO phoneDTO) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<PhoneDTO> request = new HttpEntity<>(phoneDTO, headers);
//        return restTemplate.postForEntity(REST_URL + "/users", request, MyUserOutputDTO.class).getBody();
//    }

    public List<BriefcaseOutputDTO> sendToGetUserBriefcases(PhoneDTO phoneDTO) {
        HttpEntity<PhoneDTO> request = new HttpEntity<>(phoneDTO, headers);
        return restTemplate.exchange(REST_URL+"/briefcases/index", HttpMethod.POST, request, new ParameterizedTypeReference<List<BriefcaseOutputDTO>>(){}).getBody();
    }

    public BriefcaseInfoDTO sendToGetBriefcaseInfo(String phoneNumber, String name) {
        HttpEntity<BriefcaseInputDTO> request = new HttpEntity<>(new BriefcaseInputDTO(phoneNumber, name), headers);
        return restTemplate.postForEntity(REST_URL+"/briefcases/info", request, BriefcaseInfoDTO.class).getBody();
    }

    public BriefcaseOutputDTO sendToGetBriefcase(String phoneNumber, String name) {
        HttpEntity<BriefcaseInputDTO> request = new HttpEntity<>(new BriefcaseInputDTO(phoneNumber, name), headers);
        return restTemplate.postForEntity(REST_URL+"/briefcases", request, BriefcaseOutputDTO.class).getBody();
    }

    public boolean sendToSaveAfterEdit(String phoneNumber, String name, String newName) {
        HttpEntity<BriefcaseEditDTO> request = new HttpEntity<>(new BriefcaseEditDTO(phoneNumber, name, newName, -1), headers);
        try {
            ResponseEntity<BriefcaseEditDTO> response = restTemplate.postForEntity(REST_URL + "/briefcases/name", request, BriefcaseEditDTO.class);
            return response.getStatusCode().is2xxSuccessful();
        }
        catch (Exception e) {
            return false;
        }
    }

    public List<StockOutputDTO> sendToGetStocks(String name, String filter, double from, double to) {
        StockFilterInputDTO stockFilterInputDTO = new StockFilterInputDTO(name, from, to);
        HttpEntity<StockFilterInputDTO> request = new HttpEntity<>(stockFilterInputDTO, headers);
        return restTemplate.exchange(REST_URL + "/stocks/search?filter="+filter, HttpMethod.POST, request, new ParameterizedTypeReference<List<StockOutputDTO>>(){}).getBody();
    }

    public StockOutputDTO sendToGetStock(String name){
        HttpEntity<String> request = new HttpEntity<>(name, headers);
        return restTemplate.postForEntity(REST_URL+"/stocks/find", request, StockOutputDTO.class).getBody();
    }

    public List<BriefcaseStockOutputDTO> sendToGetUserStocks(String phoneNumber, String name) {
        HttpEntity<BriefcaseStockFindDTO> request = new HttpEntity<>(new BriefcaseStockFindDTO(phoneNumber, name), headers);
        return restTemplate.exchange(REST_URL + "/users/stocks", HttpMethod.POST, request, new ParameterizedTypeReference<List<BriefcaseStockOutputDTO>>(){}).getBody();
    }

    public boolean sendToUpdateBalance(String phoneNumber, String briefcaseName, double amount) {
        HttpEntity<BriefcaseEditDTO> request = new HttpEntity<>(new BriefcaseEditDTO(phoneNumber, briefcaseName, briefcaseName, amount), headers);
        try {
            ResponseEntity<BriefcaseEditDTO> response = restTemplate.postForEntity(REST_URL+"/briefcases/balance", request, BriefcaseEditDTO.class);
            return response.getStatusCode().is2xxSuccessful();
        }catch (Exception e) {
            return false;
        }
    }

    public boolean sendToSellStocks(String phoneNumber, String briefcaseName, String stockName, int amount) {
        HttpEntity<OperationInputDTO> request = new HttpEntity<>(new OperationInputDTO(phoneNumber, briefcaseName,
                OperationNames.SALE, stockName, amount), headers);
        try {
            ResponseEntity<OperationInputDTO> response = restTemplate.postForEntity(REST_URL+"/operations/sell", request, OperationInputDTO.class);
            return response.getStatusCode().is2xxSuccessful();
        }catch (Exception e) {
            return false;
        }

    }

    public boolean sendToBuyStocks(String phoneNumber, String briefcaseName, String stockName, int amount) {
        HttpEntity<OperationInputDTO> request = new HttpEntity<>(new OperationInputDTO(phoneNumber, briefcaseName,
                OperationNames.PURCHASE, stockName, amount), headers);
        try {
            ResponseEntity<OperationInputDTO> response = restTemplate.postForEntity(REST_URL+"/operations/buy", request, OperationInputDTO.class);
            return response.getStatusCode().is2xxSuccessful();
        }catch (Exception e) {
            return false;
        }
    }
}
