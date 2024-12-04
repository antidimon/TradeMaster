package antidimon.web.stocksadder.services;


import antidimon.web.stocksadder.models.StockInputDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class StockService {

    private final Random rand = new Random();

    private final List<StockInputDTO> stocks = new ArrayList<>();

    public StockService() {
        StockInputDTO stockA = new StockInputDTO("A", 101, 1);
        stocks.add(stockA);
        StockInputDTO stockB = new StockInputDTO("B", 1763, 1);
        stocks.add(stockB);
        StockInputDTO stockC = new StockInputDTO("C", 0.00132, 1000000);
        stocks.add(stockC);
        StockInputDTO stockD = new StockInputDTO("D", 503.24, 1);
        stocks.add(stockD);
    }

    public List<StockInputDTO> getStocks() {
        stocks.forEach(this::setRandomPrice);
        return stocks;
    }

    private void setRandomPrice(StockInputDTO stock) {
        int randomInt = rand.nextInt(100);
        if (randomInt < 55) {
            stock.setPrice(stock.getPrice() + (int)stock.getPrice()*0.005);
        }
        else {
            stock.setPrice(stock.getPrice() - (int)stock.getPrice()*0.005);
        }
    }
}
