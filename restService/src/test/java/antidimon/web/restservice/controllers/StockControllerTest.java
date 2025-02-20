package antidimon.web.restservice.controllers;

import antidimon.web.restservice.models.dto.input.stock.StockFilterInputDTO;
import antidimon.web.restservice.models.dto.input.stock.StockInputDTO;
import antidimon.web.restservice.models.dto.output.stock.StockOutputDTO;
import antidimon.web.restservice.services.StockService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StockController.class)
public class StockControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    StockService stockService;

    private static List<StockOutputDTO> stocks;

    @BeforeAll
    public static void setup() {
        StockOutputDTO stockOutputDTO = new StockOutputDTO("A", 100, LocalDateTime.now(), 110, 1, "A.png", Collections.emptyList());
        StockOutputDTO stockOutputDTO2 = new StockOutputDTO("B", 10, LocalDateTime.now(), 20, 1, "B.png", Collections.emptyList());
        StockOutputDTO stockOutputDTO3 = new StockOutputDTO("B2", 10, LocalDateTime.now(), 20, 1, "B2.png", Collections.emptyList());
        stocks = List.of(stockOutputDTO, stockOutputDTO2, stockOutputDTO3);
    }

    @Test
    void getStockByName() throws Exception {
        Mockito.when(this.stockService.getStock(any())).thenReturn(this.getOneStock());

        mockMvc.perform(get("/stocks/find?name=Test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(7));
    }

    @Test
    void getStocksByName() throws Exception {

        Mockito.when(this.stockService.getStocks(any(), any())).thenReturn(this.getTwoStocks());

        StockFilterInputDTO filter = new StockFilterInputDTO("B", 0, 100);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = objectMapper.writeValueAsString(filter);
        mockMvc.perform(post("/stocks/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void addStocks() throws Exception {

        StockInputDTO stock1 = new StockInputDTO("Test1", 1, 1);
        StockInputDTO stock2 = new StockInputDTO("Test2", 2, 2);
        var list = List.of(stock1, stock2);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = objectMapper.writeValueAsString(list);

        mockMvc.perform(post("/stocks/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isOk());

        verify(this.stockService, times(2)).save(any(StockInputDTO.class));
    }
    
    @Test
    void getAllStocks() throws Exception {

        Mockito.when(this.stockService.getStocks(any())).thenReturn(stocks);

        mockMvc.perform(get("/stocks?name=Test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }



    private StockOutputDTO getOneStock(){
        return stocks.get(0);
    }

    private List<StockOutputDTO> getTwoStocks(){
        return stocks.stream().filter(stock -> stock.getName().startsWith("B")).toList();
    }
}
