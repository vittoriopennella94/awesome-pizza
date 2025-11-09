package it.adesso.awesomepizza.controller;

import it.adesso.awesomepizza.service.OrderStateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;

import static it.adesso.awesomepizza.utility.Constants.EXCEPTION_ERROR_MSG;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class OrderStateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoSpyBean
    private OrderStateService orderStateService;

    @Test
    public void searchAllStatesOk() throws Exception {
        mockMvc.perform(get("/api/states")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data[0].stateId").exists())
                .andExpect(jsonPath("$.data[0].stateName").exists());

    }

    @Test
    public void searchAllStates_thenThrowsException() throws Exception {
        when(this.orderStateService.getStates()).thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/states")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.status").isNumber())
                .andExpect(jsonPath("$.data.status").value(500))
                .andExpect(jsonPath("$.data.message").isString())
                .andExpect(jsonPath("$.data.message").value(EXCEPTION_ERROR_MSG));
    }


}
