package antidimon.web.restservice.services;


import antidimon.web.restservice.mappers.stock.StockMapper;
import antidimon.web.restservice.models.dto.input.stock.StockFilterInputDTO;
import antidimon.web.restservice.models.dto.input.stock.StockInputDTO;
import antidimon.web.restservice.models.dto.output.stock.PriceOutputDTO;
import antidimon.web.restservice.models.dto.output.stock.StockOutputDTO;
import antidimon.web.restservice.models.entities.stock.Stock;
import antidimon.web.restservice.repositories.stock.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockMapper stockMapper;
    private final StockRepository stockRepository;

    public List<StockOutputDTO> getStocks(StockFilterInputDTO stockFilterInputDTO, String filter) {
        List<Stock> stocks;
        if (stockFilterInputDTO.getName() == null || stockFilterInputDTO.getName().isEmpty()) {
            stocks = stockRepository.findAllUnique(stockFilterInputDTO.getFrom(), stockFilterInputDTO.getTo());
        }
        else {
            stocks = stockRepository.findAllUniqueByName(stockFilterInputDTO.getName(), stockFilterInputDTO.getFrom(), stockFilterInputDTO.getTo());
        }
        List<StockOutputDTO> stocksDTO = stocks.stream().map(stockMapper::toOutputDTO).toList();
        return doFilter(stocksDTO, filter);
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




    private List<StockOutputDTO> doFilter(List<StockOutputDTO> stocksDTO, String filter) {
        switch (filter){
            case "name_asc", "name_desc":
                Comparator<StockOutputDTO> nameComparator = Comparator.comparing(StockOutputDTO::getName);
                if (filter.equals("name_asc")) {
                    return stocksDTO.stream().sorted(nameComparator).toList();
                }
                return stocksDTO.stream().sorted(nameComparator.reversed()).toList();
            case "price_desc", "price_asc":
                Comparator<StockOutputDTO> comparator = Comparator.comparingDouble(stock -> stock.getPrice() * stock.getStocksPerLot());
                if (filter.equals("price_asc")) {
                    return stocksDTO.stream().sorted(comparator).toList();
                }
                return stocksDTO.stream().sorted(comparator.reversed()).toList();
            default:
                return stocksDTO;
        }
    }
}
