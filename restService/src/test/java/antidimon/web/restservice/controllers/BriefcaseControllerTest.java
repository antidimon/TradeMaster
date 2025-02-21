package antidimon.web.restservice.controllers;


import antidimon.web.restservice.models.dto.input.briefcase.BriefcaseEditDTO;
import antidimon.web.restservice.models.dto.input.briefcase.BriefcaseInputDTO;
import antidimon.web.restservice.models.dto.output.briefcases.BriefcaseInfoDTO;
import antidimon.web.restservice.models.dto.output.briefcases.BriefcaseOutputDTO;
import antidimon.web.restservice.models.entities.briefcase.Briefcase;
import antidimon.web.restservice.services.BriefcaseService;
import antidimon.web.restservice.services.MyUserService;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BriefcaseController.class)
public class BriefcaseControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BriefcaseService briefcaseService;

    @MockBean
    MyUserService myUserService;

    private static final ObjectMapper mapper = new ObjectMapper();


    @Test
    void getBriefcase() throws Exception {
        Mockito.when(this.briefcaseService.getBriefcase(any(BriefcaseInputDTO.class))).thenReturn(new BriefcaseOutputDTO());

        BriefcaseInputDTO briefcaseInputDTO = new BriefcaseInputDTO("Test", "Test");

        String json = mapper.writeValueAsString(briefcaseInputDTO);

        mockMvc.perform(post("/briefcases")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void createBriefcase() throws Exception {

        BriefcaseInputDTO briefcaseInputDTO = new BriefcaseInputDTO("Test", "Test");

        String json = mapper.writeValueAsString(briefcaseInputDTO);

        mockMvc.perform(post("/briefcases/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());

        verify(this.briefcaseService, times(1)).createBriefcase(any(BriefcaseInputDTO.class));

    }

    @Test
    void getInfo() throws Exception {

        Mockito.when(this.briefcaseService.getBriefcaseInfo(any(BriefcaseInputDTO.class))).thenReturn(new BriefcaseInfoDTO());

        BriefcaseInputDTO briefcaseInputDTO = new BriefcaseInputDTO("Test", "Test");

        String json = mapper.writeValueAsString(briefcaseInputDTO);

        mockMvc.perform(post("/briefcases/info")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());

        verify(this.briefcaseService, times(1)).getBriefcaseInfo(any(BriefcaseInputDTO.class));
    }

    @Test
    void getUserBriefcases() throws Exception {

        Mockito.when(this.myUserService.getUserBriefcases(anyString())).thenReturn(List.of(new BriefcaseOutputDTO(), new BriefcaseOutputDTO()));

        mockMvc.perform(get("/briefcases/index?phone=Test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(this.myUserService, times(1)).getUserBriefcases(anyString());

    }

    @Test
    void updateBriefcase() throws Exception {

        BriefcaseEditDTO briefcaseEditDTO = new BriefcaseEditDTO("Test", "Test", "Test2", 1);

        String json = mapper.writeValueAsString(briefcaseEditDTO);

        mockMvc.perform(post("/briefcases/name")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is4xxClientError());

        verify(this.briefcaseService, times(1)).editBriefcaseName(any(BriefcaseEditDTO.class));


    }

    @Test
    void updateBalance() throws Exception {

        BriefcaseEditDTO briefcaseEditDTO = new BriefcaseEditDTO("Test", "Test", "Test2", 1);

        String json = mapper.writeValueAsString(briefcaseEditDTO);

        mockMvc.perform(post("/briefcases/balance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is4xxClientError());

        verify(this.briefcaseService, times(1)).editBriefcaseBalance(any(BriefcaseEditDTO.class));
    }

    @Test
    void getLots() throws Exception {

        Mockito.when(this.briefcaseService.getBriefcaseLotsAmount(anyString(), anyString())).thenReturn(100L);

        mockMvc.perform(get("/briefcases/lots?phone=Test&name=Test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(100));

        verify(this.briefcaseService, times(1)).getBriefcaseLotsAmount(anyString(), anyString());
    }

}
