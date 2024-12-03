package antidimon.web.restservice.controllers;


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

    @GetMapping
    public ResponseEntity<List<StockOutputDTO>> getStocks(){
        List<StockOutputDTO> stocks = stockService.getStocks();
        return ResponseEntity.ok(stocks);
    }

    @PostMapping("/find")
    public ResponseEntity<StockOutputDTO> getStock(@RequestBody String name){
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
    public ResponseEntity<List<StockOutputDTO>> findStocksByName(@RequestBody String name){
        List<StockOutputDTO> stocks = stockService.getStocks(name);
        return ResponseEntity.ok(stocks);
    }

    @PostMapping("/add")
    public ResponseEntity<List<StockOutputDTO>> addStocks(@RequestBody List<StockInputDTO> stocks){
        stocks.forEach(stockService::save);
        return ResponseEntity.ok().build();
    }
}
