package antidimon.web.restservice.mappers.stock;


import antidimon.web.restservice.models.dto.input.stock.StockInputDTO;
import antidimon.web.restservice.models.dto.output.stock.PriceOutputDTO;
import antidimon.web.restservice.models.dto.output.stock.StockOutputDTO;
import antidimon.web.restservice.models.entities.stock.Stock;
import antidimon.web.restservice.repositories.stock.StockRepository;
import antidimon.web.restservice.services.StockService;
import jakarta.persistence.Tuple;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class StockMapper {

    private final ModelMapper modelMapper = new ModelMapper();
    private final StockRepository stockRepository;

    public StockMapper(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public StockOutputDTO toOutputDTO(Stock stock) {
        StockOutputDTO stockOutputDTO = modelMapper.map(stock, StockOutputDTO.class);
        stockOutputDTO.setUrl("/images/"+stock.getName()+".jpg");
        List<Tuple> prices = stockRepository.getPrices(stock.getName());
        List<PriceOutputDTO> priceOutputDTOs = new ArrayList<>();
        for (Tuple tuple : prices) {
            Object[] objects = tuple.toArray();
            double price = ((BigDecimal) objects[0]).doubleValue();
            String time = objects[1].toString();
            String[] splittedTime = time.split("\\.");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime gettedAt = LocalDateTime.parse(splittedTime[0], formatter);
            priceOutputDTOs.add(new PriceOutputDTO(price, gettedAt));
        }
        stockOutputDTO.setPrices(priceOutputDTOs);
        return stockOutputDTO;
    }
    public Stock toEntity(StockOutputDTO stockOutputDTO) {return modelMapper.map(stockOutputDTO, Stock.class);}

    public Stock toEntity(StockInputDTO stockInputDTO){
        return modelMapper.map(stockInputDTO, Stock.class);
    }
}
