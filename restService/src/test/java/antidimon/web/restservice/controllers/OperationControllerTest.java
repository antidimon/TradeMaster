package antidimon.web.restservice.controllers;


import antidimon.web.restservice.models.dto.input.briefcase.BriefcaseInputDTO;
import antidimon.web.restservice.models.dto.input.operation.OperationInputDTO;
import antidimon.web.restservice.models.dto.output.operation.OperationDetails;
import antidimon.web.restservice.models.entities.operation.OperationNames;
import antidimon.web.restservice.services.OperationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OperationController.class)
public class OperationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    OperationService operationService;

    @Test
    void performBuy() throws Exception {
        Mockito.when(this.operationService.operate(any(OperationInputDTO.class))).thenReturn(true);
        OperationInputDTO operation = new OperationInputDTO("Test", "Test", OperationNames.PURCHASE, "Test", 1, 1);

        ObjectMapper mapper = new ObjectMapper();
        String jsonObject = mapper.writeValueAsString(operation);

        mockMvc.perform(post("/operations/buy")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject))
                .andExpect(status().isOk());

        verify(this.operationService, times(1)).operate(any(OperationInputDTO.class));
    }

    @Test
    void performSell() throws Exception {
        Mockito.when(this.operationService.operate(any(OperationInputDTO.class))).thenReturn(true);
        OperationInputDTO operation = new OperationInputDTO("Test", "Test", OperationNames.SALE, "Test", 1, 1);

        ObjectMapper mapper = new ObjectMapper();
        String jsonObject = mapper.writeValueAsString(operation);

        mockMvc.perform(post("/operations/sell")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject))
                .andExpect(status().isOk());

        verify(this.operationService, times(1)).operate(any(OperationInputDTO.class));
    }

    @Test
    void getDetails() throws Exception {
        Mockito.when(this.operationService.getOperationDetails(any(BriefcaseInputDTO.class))).thenReturn(List.of(new OperationDetails(), new OperationDetails()));
        BriefcaseInputDTO briefcase = new BriefcaseInputDTO("Test", "Test");

        ObjectMapper mapper = new ObjectMapper();
        String jsonObject = mapper.writeValueAsString(briefcase);

        mockMvc.perform(post("/operations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));


    }

}
