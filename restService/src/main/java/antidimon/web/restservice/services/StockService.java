package antidimon.web.restservice.services;


import antidimon.web.restservice.mappers.stock.StockMapper;
import antidimon.web.restservice.models.dto.input.stock.StockInputDTO;
import antidimon.web.restservice.models.dto.output.stock.PriceOutputDTO;
import antidimon.web.restservice.models.dto.output.stock.StockOutputDTO;
import antidimon.web.restservice.models.entities.stock.Stock;
import antidimon.web.restservice.repositories.stock.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockMapper stockMapper;
    private final StockRepository stockRepository;

    public List<StockOutputDTO> getStocks() {
        List<Stock> stocks = stockRepository.findAllUnique();
        return stocks.stream().map(stockMapper::toOutputDTO).toList();
    }

    public List<StockOutputDTO> getStocks(String name) {
        List<Stock> stocks = stockRepository.findAllUniqueByName(name);
        return stocks.stream().map(stockMapper::toOutputDTO).toList();
    }

    public StockOutputDTO getStock(String name) {
        Stock stock = stockRepository.findLastByName(name);
        return stockMapper.toOutputDTO(stock);
    }

    @Transactional
    public void save(StockInputDTO stock) {
        Stock stockEntity = stockMapper.toEntity(stock);
        stockRepository.save(stockEntity);
    }
}
