package antidimon.web.stocksadder.services;


import antidimon.web.stocksadder.models.StockInputDTO;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.*;

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
        StockInputDTO stockAC = new StockInputDTO("AC", 1000, 1);
        stocks.add(stockAC);
        StockInputDTO stockAR = new StockInputDTO("AR", 10000, 1);
        stocks.add(stockAR);
    }

    public List<StockInputDTO> getStocks() {
        stocks.forEach(this::setRandomPrice);
        return stocks;
    }

    private void setRandomPrice(StockInputDTO stock) {
        int randomInt = rand.nextInt(100);
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat format = new DecimalFormat("#.##", symbols);
        double newPrice = (randomInt < 55) ? stock.getPrice() + (int) stock.getPrice() * 0.005 : stock.getPrice() - (int) stock.getPrice() * 0.005;
        try{
            double formatedPrice = format.parse(format.format(newPrice)).doubleValue();
            System.out.println(formatedPrice);
            stock.setPrice(formatedPrice);
        }catch (ParseException e){
            e.printStackTrace();
        }
    }
}
