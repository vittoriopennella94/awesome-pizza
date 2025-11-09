package it.adesso.awesomepizza.controller;

import it.adesso.awesomepizza.service.OrderService;
import it.adesso.awesomepizza.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoSpyBean
    private ProductService productService;

    @Test
    public void getAllOrdersTestOk() throws Exception {
        mockMvc.perform(get("/api/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data[0].productId").exists())
                .andExpect(jsonPath("$.data[0].productName").exists())
                .andExpect(jsonPath("$.data[0].productDescription").exists())
                .andExpect(jsonPath("$.data[0].productPrice").isNumber())
                .andExpect(jsonPath("$.data[0].productPrice").exists())
                .andExpect(jsonPath("$.data[0].productEnable").exists());
    }

    @Test
    public void getAllOrdersTest_thenThrowsException() throws Exception {
        when(this.productService.getProducts()).thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/products")
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
        mockMvc.perform(get("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.productId").exists())
                .andExpect(jsonPath("$.data.productName").exists())
                .andExpect(jsonPath("$.data.productDescription").exists())
                .andExpect(jsonPath("$.data.productPrice").isNumber())
                .andExpect(jsonPath("$.data.productPrice").exists())
                .andExpect(jsonPath("$.data.productEnable").exists());
    }

    @Test
    public void getOrderByIdTest_thenThrowsException() throws Exception {
        when(this.productService.getProductDetailsById(1L)).thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/products/1")
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
        mockMvc.perform(get("/api/products/50")
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
