package antidimon.web.restservice.controllers;


import antidimon.web.restservice.models.dto.output.briefcases.BriefcaseStockOutputDTO;
import antidimon.web.restservice.models.dto.output.stock.StockOutputDTO;
import antidimon.web.restservice.services.MyUserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MyUserService myUserService;

    @Test
    public void findStocksByUser() throws Exception {
        Mockito.when(this.myUserService.findStocks("Test")).thenReturn(this.getStocks());
        mockMvc.perform(get("/users/stocks?phone=Test")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void wrongNumberTest() throws Exception {
        Mockito.when(this.myUserService.findStocks("123")).thenThrow(new IllegalArgumentException());
        mockMvc.perform(get("/users/stocks?phone=123")).andExpect(status().isBadRequest());
    }


    private List<BriefcaseStockOutputDTO> getStocks() throws IllegalArgumentException{
        StockOutputDTO stockOutputDTO = new StockOutputDTO("A", 100, LocalDateTime.now(), 110, 1, "A.png", Collections.emptyList());
        BriefcaseStockOutputDTO stock = new BriefcaseStockOutputDTO(stockOutputDTO, 2, 100, 0, false, 90, 2, 130, 2, "Test");
        StockOutputDTO stockOutputDTO2 = new StockOutputDTO("B", 10, LocalDateTime.now(), 20, 1, "B.png", Collections.emptyList());
        BriefcaseStockOutputDTO stock2 = new BriefcaseStockOutputDTO(stockOutputDTO2, 5, 10, 0, false, 0, 0, 0, 0, "Test");
        return List.of(stock, stock2);
    }
}
