package antidimon.web.restservice.controllers;


import antidimon.web.restservice.models.dto.input.stock.StockFilterInputDTO;
import antidimon.web.restservice.models.dto.input.stock.StockInputDTO;
import antidimon.web.restservice.models.dto.output.stock.StockOutputDTO;
import antidimon.web.restservice.services.StockService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stocks")
@AllArgsConstructor
public class StockController {


    private final StockService stockService;

    @GetMapping("/find")
    public ResponseEntity<StockOutputDTO> getStock(@RequestParam("name") String name){
        try {

            StockOutputDTO stockOutputDTO = stockService.getStock(name);
            return ResponseEntity.ok(stockOutputDTO);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/search")
    public ResponseEntity<List<StockOutputDTO>> findStocksByName(@RequestBody StockFilterInputDTO stockFilterInputDTO,
                                                                 @RequestParam(required = false, value = "filter") String filter){
        List<StockOutputDTO> stocks = stockService.getStocks(stockFilterInputDTO, filter);
        return ResponseEntity.ok(stocks);
    }

    @PostMapping("/add")
    public ResponseEntity<List<StockOutputDTO>> addStocks(@RequestBody List<StockInputDTO> stocks){
        stocks.forEach(stockService::save);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<StockOutputDTO>> getStocks(@RequestParam("name") String stockName){
        List<StockOutputDTO> stocks = stockService.getStocks(stockName);
        return ResponseEntity.ok(stocks);
    }
}
