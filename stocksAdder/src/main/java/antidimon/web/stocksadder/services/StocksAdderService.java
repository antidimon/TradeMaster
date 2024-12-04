package antidimon.web.stocksadder.services;


import antidimon.web.stocksadder.models.StockInputDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StocksAdderService {

    private final RestTemplate restTemplate;
    private final StockService stockService;

    @Value(value = "${REST_URL}")
    private String REST_URL;

    @Scheduled(cron = "*/3 * * * * *")
    public void addStocks() {
        List<StockInputDTO> stocks = stockService.getStocks();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<StockInputDTO>> request = new HttpEntity<>(stocks, headers);
        restTemplate.exchange(REST_URL + "/stocks/add", HttpMethod.POST, request, new ParameterizedTypeReference<List<StockInputDTO>>() {});
    }
}
