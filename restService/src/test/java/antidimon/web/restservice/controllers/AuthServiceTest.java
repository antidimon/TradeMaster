package antidimon.web.restservice.controllers;


import antidimon.web.restservice.models.dto.input.user.MyUserInputDTO;
import antidimon.web.restservice.services.MyUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class)
public class AuthServiceTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    MyUserService myUserService;

    @Test
    void checkRegister() throws Exception {
        MyUserInputDTO testUser = new MyUserInputDTO("Test", 10, "Test", "Test", "Test@test.test");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = objectMapper.writeValueAsString(testUser);
        mvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());


        verify(myUserService, times(1)).save(any(MyUserInputDTO.class));

    }

}
