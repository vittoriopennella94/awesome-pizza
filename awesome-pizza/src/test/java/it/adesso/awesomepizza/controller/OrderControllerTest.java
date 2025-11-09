package it.adesso.awesomepizza.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void getAllOrdersTest() throws Exception {
        mockMvc.perform(get("/api/orders")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data[0].orderId").exists())
                .andExpect(jsonPath("$.data[0].customerName").exists())
                .andExpect(jsonPath("$.data[0].customerSurname").exists())
                .andExpect(jsonPath("$.data[0].customerAddress").exists())
                .andExpect(jsonPath("$.data[0].customerStreetNumber").exists())
                .andExpect(jsonPath("$.data[0].customerAddInfo").exists())
                .andExpect(jsonPath("$.data[0].orderState").exists());
    }

    @Test
    public void getOrderByIdTest() throws Exception {
        mockMvc.perform(get("/api/orders/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.products").exists())
                .andExpect(jsonPath("$.data.products").isArray())
                .andExpect(jsonPath("$.data.products").isNotEmpty())
                .andExpect(jsonPath("$.data.products[0].product").exists())
                .andExpect(jsonPath("$.data.products[0].product.productId").exists())
                .andExpect(jsonPath("$.data.products[0].product.productName").exists())
                .andExpect(jsonPath("$.data.products[0].product.productDescription").exists())
                .andExpect(jsonPath("$.data.products[0].product.productPrice").exists())
                .andExpect(jsonPath("$.data.products[0].product.productEnable").exists())
                .andExpect(jsonPath("$.data.products[0].quantity").exists());
    }
}
