package it.adesso.awesomepizza.controller;

import it.adesso.awesomepizza.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;

import static it.adesso.awesomepizza.utility.Constants.EXCEPTION_ERROR_MSG;
import static it.adesso.awesomepizza.utility.Constants.NOT_FOUND_EXCEPTION_ERROR_MSG;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoSpyBean
    private OrderService orderService;

    @Test
    public void getAllOrdersTestOk() throws Exception {
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
    public void getAllOrdersTest_thenThrowsException() throws Exception {
        when(this.orderService.getOrders()).thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/orders")
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

    @Test
    public void getOrderByIdTestOk() throws Exception {
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

    @Test
    public void getOrderByIdTest_thenThrowsException() throws Exception {
        when(this.orderService.getOrderDetailsById(1L)).thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/orders/1")
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

    @Test
    public void getOrderById_notFound() throws Exception {
        mockMvc.perform(get("/api/orders/10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.status").isNumber())
                .andExpect(jsonPath("$.data.status").value(200))
                .andExpect(jsonPath("$.data.message").isString())
                .andExpect(jsonPath("$.data.message").value(NOT_FOUND_EXCEPTION_ERROR_MSG));
    }
}
